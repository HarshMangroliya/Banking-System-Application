package BankingSystemPack;

import java.io.Serializable;

public class Transaction implements Serializable {
    protected int transactionID;
    protected int debitFrom;
    protected int creditTo;
    protected double amount;
    protected transactionMessage status;

    Transaction(){

        this.transactionID = UserOperations.bank.TransactionID++;

    }


    Transaction(int transactionID,int a){
        this.transactionID = transactionID;
    }


}
