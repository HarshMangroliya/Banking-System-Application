package BankingSystemPack;

import java.io.Serializable;

// LoanRecord class implements the Serializable interface
public class LoanRecord implements Serializable {
    protected Customer cust;
    protected int loanNumber;
    protected double approvedAmt;
    protected String status;

    // constructor for registering loan amount
    public LoanRecord(Customer cactive, int loanNumber, double approvedAmt, String status) {
        this.cust = cactive;
        this.loanNumber = loanNumber;
        this.approvedAmt = approvedAmt;
        this.status = status;
    }

    // displaying the customer details
    public void printRecord(){
        System.out.println(loanNumber + "   " + approvedAmt + "   " + status);
    }
}
