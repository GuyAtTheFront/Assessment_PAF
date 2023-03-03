package iss.nus.Assessment_PAF.models;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iss.nus.Assessment_PAF.repositories.AccountsRepository;
import iss.nus.Assessment_PAF.services.FundsTransferService;

@Component
public class Validation {

    @Autowired
    FundsTransferService transferService;

    @Autowired
    AccountsRepository accountRepo;
    
    // Task 5
    public List<String> formTransfer(FormTransfer form, AccountsRepository repo) {
        List<String> errors = new LinkedList<>();

            List<String> names = repo.getNameConcatID();

            // C0
            if(!names.contains(form.getFromAccount())) {
                errors.add("Fron Account does not exist");
            }

            // C0
            if(!names.contains(form.getToAccount())) {
                errors.add("To Account does not exist");
            }

            // C1
            if(form.getFromId().length() != 10) {
                errors.add("From Account ID must be exactly 10 characters");
            }

            // C1
            if(form.getToId().length() != 10) {
                errors.add("To Account ID must be exactly 10 characters");
            }

            // C2
            if(form.getFromAccount().equalsIgnoreCase(form.getToAccount())) {
                errors.add("Cannot transfer to the same account");
            }

            // C3
            if(form.getAmount() == null || form.getAmount().signum() != 1) {
                errors.add("Transfer Amount cannot be zero or negative");
            }

            // C4
            if(form.getAmount().intValue() < 10) {
                errors.add("Transfer Amount must be at least $10");
            }

            Optional<BigDecimal> fromBalanceOpt = repo.getBalanceById(form.getFromId());

            if (fromBalanceOpt.isEmpty()) {
                errors.add("Form Account Balance Check failed");
            }

            // C5
            System.out.println(fromBalanceOpt.get());
            System.out.println(form.getAmount());
            System.out.println(form.getAmount().compareTo(fromBalanceOpt.get()));
            if(form.getAmount().compareTo(fromBalanceOpt.get()) != -1) {
                errors.add("From Account insuffience balance for transfer");
            }


        return errors;
    }
}
