package iss.nus.Assessment_PAF.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import iss.nus.Assessment_PAF.models.FormTransfer;
import iss.nus.Assessment_PAF.models.Validation;
import iss.nus.Assessment_PAF.repositories.AccountsRepository;
import iss.nus.Assessment_PAF.services.FundsTransferService;
import iss.nus.Assessment_PAF.services.LogAuditService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class FundsTransferController {

    @Autowired
    AccountsRepository accountRepo;

    @Autowired
    FundsTransferService transferService;

    @Autowired
    LogAuditService logService;

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        List<String> names = transferService.getFormAccounts();

        String status = (String) session.getAttribute("status");

            if (status.equalsIgnoreCase("submitted")) {
                session.invalidate();
            }
        
        model.addAttribute("names", names);
        return "index";
    }

    // MultiValueMap<String, String> form)
    @PostMapping("/transfer")
    public String transfer(@ModelAttribute FormTransfer form, HttpSession session)  {
            System.out.println("\n\n\n\n\n" + form);

            session.setAttribute("form", form);

            Validation validation = new Validation();

            List<String> errors = validation.formTransfer(form, accountRepo);
            System.out.println(errors);
            if (!errors.isEmpty()) {
                // add errors to session
                session.setAttribute("errors", errors);

                // return redirect index
                return "redirect:/";
            }


            // TODO: Is this 8 digits?
            // TODO: This might gen duplicate ID and crash Redis
            String transactionId = UUID.randomUUID().toString().substring(0, 8);

            // SQL transaction transfer
            // Note:
            // - When transfer fail, will rollback with Exception
            // - Controller advice will redirect to index page
            // - Therefore not handled here
            transferService.fundTransfer(form.getFromId(), form.getToId(), form.getAmount());
            
            // Some issues with redis connection
            logService.saveAuditLog(transactionId, form.getFromId(), form.getToId(), form.getAmount());


            // should probably invalidate session here, sigh...
            session.setAttribute("id", transactionId);

        return "redirect:/confirm";
    }

    @GetMapping("/confirm")
    public String confirm(HttpSession session) {
        session.setAttribute("status", "submitted");
        return "confirm";
    }


}
