package BankingSystemPack;

public class Admin extends User {
    protected boolean authorised;
    public Admin(String username, String password, String name, userType utype,boolean authorised){
        super(username,password,name,utype);
        this.authorised = authorised;
    }
}
