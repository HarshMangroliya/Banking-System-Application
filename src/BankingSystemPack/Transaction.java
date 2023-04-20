package BankingSystemPack;

import java.io.Serializable;

public class Transaction implements Serializable {
    protected int transactionID;
    protected int debitFrom;
    protected int creditTo;
    protected double amount;
    protected String status;

    Transaction(int transactionID){
        this.transactionID = transactionID;
    }


}
