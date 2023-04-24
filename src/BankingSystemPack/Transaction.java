package BankingSystemPack;

import java.io.Serializable;

// Transaction class implements the Serializable Interface
public class Transaction implements Serializable {
    protected int transactionID;
    protected int debitFrom;
    protected int creditTo;
    protected double amount;
    protected transactionMessage status;

    // Transaction constructor for incrementing the transactin ID on each transaction
    Transaction(){
        this.transactionID = UserOperations.bank.TransactionID++;
    }

    // Transaction constructor for setting the details of current transaction
    Transaction(int transactionID, int debitFrom, int creditTo, double amount, transactionMessage status){
        UserOperations.bank.TransactionID++;
        this.transactionID = transactionID;
        this.debitFrom = debitFrom;
        this.creditTo = creditTo;
        this.amount = amount;
        this.status = status;
    }
}
