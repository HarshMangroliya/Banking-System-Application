package BankingSystemPack;

import java.io.Serializable;

public class Admin extends User implements Serializable {
    protected int authorised;

    // Admin class constructor calls super() -> constructor of User class
    public Admin(){
        super();
    }

    // Admin constructor for creating an Admin account
    public Admin(String username, String password, String name, userType utype, int authorised){
        super(username, password, name, utype);
        this.authorised = authorised;
    }
}
