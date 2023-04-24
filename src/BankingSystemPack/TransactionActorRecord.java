package BankingSystemPack;

import java.io.Serializable;

// TransactionActorRecord class implements the Serializable interface
public class TransactionActorRecord implements Serializable {
    protected Transaction rec;
    protected double remainingBalance;
    protected userType type;

    // TransactionActorRecord sets the record and actor for current transaction
    TransactionActorRecord(Transaction record, userType actor){
        this.rec = record;
        this.type = actor;
    }

    // method for updating the remaining balance
    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    // method for displaying the record
    public void printRecord(){
        char sign = type == userType.SENDER ? '-':'+';
        System.out.println(rec.transactionID + "  " + rec.debitFrom + "  " + rec.creditTo + "  " + sign+rec.amount + "  " + remainingBalance + "  " + rec.status);
    }
}
