package iss.nus.Assessment_PAF.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import iss.nus.Assessment_PAF.exceptions.TransferFailedException;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public record AppControllerAdvice() {
    
    @ExceptionHandler({TransferFailedException.class})
    public ModelAndView failedTransfer(TransferFailedException ex, HttpSession session) {
        ModelAndView mav = new ModelAndView("index.html");
        
        List<String> errors = new LinkedList<>();
        errors.add(ex.getMessage());

        session.setAttribute("errors", errors);
        session.setAttribute("status", "submitted");
        return mav;
    }

}
