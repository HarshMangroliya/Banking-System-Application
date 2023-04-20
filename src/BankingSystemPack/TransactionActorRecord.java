package BankingSystemPack;

import java.io.Serializable;

public class TransactionActorRecord implements Serializable {
    protected Transaction rec;
    protected double remainingBalance;
    protected userType type;

    TransactionActorRecord(Transaction record, userType actor){
        this.rec = record;
        this.type = actor;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public void printRecord(){
        char sign = type == userType.SENDER ? '-':'+';
        System.out.println(rec.transactionID+"  "+rec.debitFrom+"  "+rec.creditTo+"  "+sign+rec.amount+"  "+remainingBalance+"  "+rec.status);
    }

}
