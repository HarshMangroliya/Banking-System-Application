package BankingSystemPack;

public class Customer extends User implements Calculate_interest{

    private accType acc_type;
    private int accNo;
    private double balance;

    public Customer(String username, String password, String name, userType utype,accType atype){
        super(username,password,name,utype);
        this.acc_type = atype;
        this.accNo = ++UserOperations.AccountNo;
    }

    public Customer(String username, String password, String name, userType utype,accType atype,double balance){
        super(username,password,name,utype);
        this.acc_type = atype;
        this.accNo = ++UserOperations.AccountNo;
        this.balance = balance;
    }

    public double getBalance() {return this.balance;}

    public int getAccNo() {return this.accNo;}

    public accType getAccType(){return this.acc_type;}

    public double updateBalance(double amt,char ops){
        if(ops == '+')
            this.balance = this.balance + amt;
        else
            this.balance = this.balance - amt;
        return this.balance;
    }

    @Override
    public void CalSavingAccInterest(double balance) {

    }


    public void printAccRecord() {
        System.out.println(this.getAccNo()+" "+this.getUsername()+" "+this.balance+" "+this.acc_type);
    }
}
