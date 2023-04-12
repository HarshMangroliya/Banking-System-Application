package BankingSystemPack;

public class Transaction {
    private int transactionID;
    protected int debitFrom;
    protected int creditTo;
    protected double amount;
    protected String status;

    Transaction(int transactionID){
        this.transactionID = transactionID;
    }


}
