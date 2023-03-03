package iss.nus.Assessment_PAF.models;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class FormTransfer {
    
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String comments;

    private String fromId;
    private String fromName;
    private String toId;
    private String toName;


    @Override
    public String toString() {
        return "FormTransfer [fromAccount=" + fromAccount + ", toAccount=" + toAccount + ", amount=" + amount
                + ", comments=" + comments + ", fromId=" + fromId + ", fromName=" + fromName + ", toID=" + toId
                + ", toName=" + toName + "]";
    }

    public String getFromId() {
        return fromId;
    }
    public void setFromId(String fromId) {
        this.fromId = fromId;
    }
    public String getFromName() {
        return fromName;
    }
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }
    public String getToId() {
        return toId;
    }
    public void setToID(String toId) {
        this.toId = toId;
    }
    public String getToName() {
        return toName;
    }
    public void setToName(String toName) {
        this.toName = toName;
    }
    public String getFromAccount() {
        return fromAccount;
    }
    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
        this.fromName = fromAccount.split(" ")[0];
        this.fromId = fromAccount.split(" ")[1].replace("(", "").replace(")", "");
    }
    public String getToAccount() {
        return toAccount;
    }
    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
        this.toName = toAccount.split(" ")[0];
        this.toId = toAccount.split(" ")[1].replace("(", "").replace(")", "");
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    

}
