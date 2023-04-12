package BankingSystemPack;

public class LoanRecord {
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
}
