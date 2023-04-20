package BankingSystemPack;

import java.io.Serializable;

public class LoanRecord implements Serializable {
    protected Customer cust;
    protected int loanNumber;
    protected double approvedAmt;
    protected String status;

    public LoanRecord(Customer cactive, int loanNumber, double approvedAmt, String status) {
        this.cust = cactive;
        this.loanNumber = loanNumber;
        this.approvedAmt = approvedAmt;
        this.status = status;
    }

    public void printRecord(){
        System.out.println(loanNumber+"   "+approvedAmt+"   "+status);
    }

}
