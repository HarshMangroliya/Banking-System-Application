package BankingSystemPack;

public class User {
    private String username;
    private String password;
    private String name;
    private userType userType;

    User(){
        super();
    }

    User(String username, String password, String name, userType utype) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.userType = utype;
    }

    public String getUsername() {return this.username;}

    public String getPassword() {return this.password;}

    public userType getUserType(){return this.userType;}

    public String getFullname() {return this.name;}

    public void setPassword(String newPass) {
        this.password = newPass;
    }

}
