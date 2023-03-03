package iss.nus.Assessment_PAF.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import iss.nus.Assessment_PAF.exceptions.TransferFailedException;
import iss.nus.Assessment_PAF.repositories.AccountsRepository;

@Service
public class FundsTransferService {
    
    @Autowired
    AccountsRepository accountsRepo;
    
    public List<String> getFormAccounts() {
        return accountsRepo.getNameConcatID();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void fundTransfer(String fromId, String toId, BigDecimal trfAmt) {

        // TODO: Check if need select statement to lock entries
        
        Integer fromUpdated = accountsRepo.updateBalanceById(fromId, trfAmt.negate());
        Integer toUpdated = accountsRepo.updateBalanceById(toId, trfAmt);

        if (fromUpdated != 1 || toUpdated != 1) {
            throw new TransferFailedException("failed to transfer %d from %s to %s"
                                            .formatted(trfAmt, fromId, toId));    
        }


    }
}
