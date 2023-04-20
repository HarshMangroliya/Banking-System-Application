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


    Transaction(int transactionID,int debitFrom,int creditTo,double amount,transactionMessage status){
        UserOperations.bank.TransactionID++;
        this.transactionID = transactionID;
        this.debitFrom = debitFrom;
        this.creditTo = creditTo;
        this.amount = amount;
        this.status = status;



    }


}
