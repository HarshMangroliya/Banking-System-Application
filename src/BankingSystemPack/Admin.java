package BankingSystemPack;

import java.io.Serializable;

public class Admin extends User implements Serializable {
    protected int authorised;
    public Admin(){
        super();
    }
    public Admin(String username, String password, String name, userType utype,int authorised){
        super(username,password,name,utype);
        this.authorised = authorised;
    }
}
