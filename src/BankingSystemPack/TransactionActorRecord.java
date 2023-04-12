package BankingSystemPack;

public class TransactionActorRecord {
    Transaction rec;
    double remainingBalance;
    userType type;

    TransactionActorRecord(Transaction record, userType actor){
        this.rec = record;
        this.type = actor;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

}
