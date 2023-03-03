package iss.nus.Assessment_PAF.services;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.nus.Assessment_PAF.repositories.LogAuditRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Service
public class LogAuditService {

    @Autowired
    LogAuditRepository logRepo;
    
    public void saveAuditLog(String transactionId, String fromId, String toId, BigDecimal amt) {
        
        JsonObject json = Json.createObjectBuilder()
                            .add("transactionId", transactionId)
                            .add("date", LocalDate.now().toString())
                            .add("from_account", fromId)
                            .add("to_account", toId)
                            .add("amount", amt)
                            .build();

        logRepo.insertRecord(transactionId, json.toString());
    }

}
