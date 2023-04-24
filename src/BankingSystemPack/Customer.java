package BankingSystemPack;

import java.io.Serializable;

// Customer class is a child class of User class and it implements interfaces Calculate_interest and Serializable
public class Customer extends User implements Calculate_interest, Serializable {
    private accType acc_type;
    private int accNo;
    private double balance;

    // customer constructor for creating a customer account
    public Customer(String username, String password, String name, userType utype, accType atype){
        super(username,password,name,utype);
        this.acc_type = atype;
        this.accNo = ++UserOperations.AccountNo;
    }

    // customer constructor for adding balance to the account
    public Customer(String username, String password, String name, userType utype, accType atype, double balance){
        super(username,password,name,utype);
        this.acc_type = atype;
        this.accNo = ++UserOperations.AccountNo;
        this.balance = balance;
    }

    // customer constructor for adding account number to the account
    public Customer(String username, String password, String name, userType utype, accType atype, double balance, int accNo){
        super(username,password,name,utype);
        this.acc_type = atype;
        this.balance = balance;
        this.accNo = accNo;
        ++UserOperations.AccountNo;
    }

    // returning customer balance amount
    public double getBalance() { return this.balance; }

    // returning customer account number
    public int getAccNo() { return this.accNo; }

    // returning customer account type
    public accType getAccType(){ return this.acc_type; }

    // updating customer balance amount
    public double updateBalance(double amt, char ops){
        if(ops == '+')
            this.balance = this.balance + amt;
        else
            this.balance = this.balance - amt;
        return this.balance;
    }

    // calculating interest rate for customer
    @Override
    public void CalSavingAccInterest(double balance) {    }

    // displaying customer account details
    public void printAccRecord() {
        System.out.println(this.getAccNo() + " " + this.getUsername() + " " + this.balance + " " + this.acc_type);
    }
}
