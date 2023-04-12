package BankingSystemPack;

import java.util.HashMap;
import java.util.Scanner;

public class UserOperations {

    public static Scanner scanner = new Scanner(System.in);

    public static HashMap<String, User> users = new HashMap<>();
    public static Customer Cactive;
    public static Admin Aactive;

    private static String AdminKey = "Admin@123";
    public static int AccountNo = 10000;
    public static int LoanNo = 100;

    public static bankingOperations bank = new bankingOperations();

    public static void register() {

        boolean f = true;
        while(f){

            int choice;
            System.out.println("Register as : \n1)Customer \n2)Admin \n3)Exit");
            System.out.print("Enter your choice : ");
            choice = scanner.nextInt();

            switch (choice){
                case 1: {
                    System.out.print("\nEnter your full name: ");
                    String fname = null, lname = null;
                    fname = scanner.next();
                    lname = scanner.next();
                    String fullname = fname + " " + lname;

                    System.out.print("Enter your username: ");
                    String username = scanner.next();

                    User user = UserOperations.users.get(username);
                    if (user != null && user.getUsername().equals(username)) {
                        boolean flag = true;
                        while (flag) {
                            System.out.println("\nUser already exists.");
                            System.out.print("Enter your username: ");
                            username = scanner.next();

                            if (!user.getUsername().equals(username))
                                flag = false;
                        }
                    }

                    String password1 = null, password2 = null;
                    boolean f1 = true;
                    while (f1) {
                        //uncomment the following lines for running in intelliJ IDEA
                        System.out.print("Enter your password: ");
                        password1 = scanner.next();
                        System.out.print("Re-enter your password: ");
                        password2 = scanner.next();

                        //uncomment the following lines for running in VS Code
                        //Console cnsl = System.console();
                        //if(cnsl == null) {
                        //    System.out.println("No console available.");
                        //}
                        //char[] passwd1 = cnsl.readPassword("Enter your password: ");
                        //password1 = new String(passwd1);
                        //char[] passwd2 = cnsl.readPassword("Re-enter your password: ");
                        //password2 = new String(passwd2);

                        if (password1.equals(password2))
                            f1 = false;
                        else
                            System.out.println("\nPlease enter same password\n");
                    }

                    boolean flag = true;
                    while(flag){
                        int tmp;
                        System.out.print("Type of account ? \n(Saving: 0 / Current: 1) : ");
                        tmp = scanner.nextInt();

                        if (tmp == 0) {
                            user = new Customer(username, password1, fullname, userType.CUSTOMER, accType.SAVING);
                            flag = false;
                        }else if (tmp == 1) {
                            user = new Customer(username, password1, fullname,userType.CUSTOMER,accType.CURRENT);
                            flag = false;
                        }
                        else{
                            System.out.println("Invalid choice for type of account");
                        }
                    }


                    UserOperations.users.put(username, user);
                    System.out.println("\nUser registered successfully.");
                    break;
                }

                case 2:{

                    System.out.print("\nEnter your full name: ");
                    String fname = null, lname = null;
                    fname = scanner.next();
                    lname = scanner.next();
                    String fullname = fname + " " + lname;

                    System.out.print("Enter your username: ");
                    String username = scanner.next();

                    User admin = UserOperations.users.get(username);
                    if (admin != null && admin.getUsername().equals(username)) {
                        boolean flag = true;
                        while (flag) {
                            System.out.println("\nAdmin already exists.");
                            System.out.print("Enter your username: ");
                            username = scanner.next();

                            if (!admin.getUsername().equals(username))
                                flag = false;
                        }
                    }

                    String password1 = null, password2 = null;
                    boolean f1 = true;
                    while (f1) {
                        //uncomment the following lines for running in intelliJ IDEA
                        System.out.print("Enter your password: ");
                        password1 = scanner.next();
                        System.out.print("Re-enter your password: ");
                        password2 = scanner.next();

                        //uncomment the following lines for running in VS Code
                        //Console cnsl = System.console();
                        //if(cnsl == null) {
                        //    System.out.println("No console available.");
                        //}
                        //char[] passwd1 = cnsl.readPassword("Enter your password: ");
                        //password1 = new String(passwd1);
                        //char[] passwd2 = cnsl.readPassword("Re-enter your password: ");
                        //password2 = new String(passwd2);

                        if (password1.equals(password2))
                            f1 = false;
                        else
                            System.out.println("\nPlease enter same password\n");
                    }


                    int cnt = 3;
                    while(true) {
                        if(cnt-- == 0){
                            System.out.println("Maximum attempts exhausted!");
                            break;
                        }

                        System.out.print("Enter the admin key (Remaining tries "+(cnt+1) +") : " );
                        String key;
                        key = scanner.next();

                        if(key.equals(AdminKey)){
                            UserOperations.users.put(username, new Admin(username, password1, fullname,userType.ADMIN));
                            System.out.println("\nAdmin registered successfully.");
                            break;
                        }
                        else
                            System.out.println("Invalid Admin key.");
                    }
                    break;
                }

                case 3:{
                    f = false;
                    break;
                }

                default:
                    System.out.println("Please Enter valid choice !");
            }

        }

    }

    public static boolean resetPassword() {
        System.out.print("\nEnter your fullname: ");
        String fname = null , lname = null;
        fname = scanner.next();
        lname =  scanner.next();
        String fullname = fname+" "+lname;
        System.out.print("Enter your username: ");
        String username = scanner.next();

        User user = UserOperations.users.get(username);

        if (user != null && user.getUsername().equals(username) && user.getFullname().equals(fullname)) {
            boolean flag = true;
            while(flag) {
                //uncomment the following lines for running in intelliJ IDEA
                System.out.print("Enter your new password: ");
                String password1 = scanner.next();
                System.out.print("Re-enter your new password: ");
                String password2 = scanner.next();


                //uncomment the following lines for running in VS Code
                /*//Console cnsl = System.console();
                //if(cnsl == null) {
                //    System.out.println("No console available.");
                //}
                //char[] passwd1 = cnsl.readPassword("Enter your new password: ");
                //String password1 = new String(passwd1);
                //char[] passwd2 = cnsl.readPassword("Re-enter your new password: ");
                //String password2 = new String(passwd2);
                */

                if(password1.equals(password2)){
                    flag = false;
                    System.out.println("\nPassword reset successfully.");
                    user.setPassword(password1);
                }
                else
                    System.out.println("\nPlease enter same password.");
            }
            return true;
        }

        System.out.println("\nNo such user exists.");

        return false;
    }

    public static boolean login() {


        System.out.print("\nEnter your username: ");
        String username = scanner.next();

        //uncomment the following lines for running in intelliJ IDEA
        System.out.print("Enter your password: ");
        String password = scanner.next();


        //uncomment the following lines for running in VS Code
                        /*  Console cnsl = System.console();
                        if(cnsl == null)
                            System.out.println("No console available.");

                        char[] passwd = cnsl.readPassword("Enter password: ");
                        String password = new String(passwd);*/

        User user = UserOperations.users.get(username);
        if (user != null && user.getPassword().equals(password) ){
            System.out.println("\nLogin successfully.");
            if(user.getUserType() == userType.ADMIN)
                UserOperations.Aactive = (Admin) user;
            else
                UserOperations.Cactive = (Customer)user;
            return true;
        }
        else {
            System.out.println("\nLogin failed. Invalid username or password.");
        }

        return false;
    }

    public static boolean logout() {

            UserOperations.Aactive = null;
            UserOperations.Cactive = null;
        return true;
    }

}
