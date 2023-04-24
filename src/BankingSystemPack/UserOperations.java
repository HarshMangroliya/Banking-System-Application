package BankingSystemPack;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserOperations {
    public static Scanner scanner = new Scanner(System.in);

    // hashmap for storing the users
    public static HashMap<String, User> users = new HashMap<>();

    // Customer object for current active customer
    public static Customer Cactive;

    // Admin object for current active admin
    public static Admin Aactive;

    // starting value for account numbers
    public static int AccountNo = 1000;

    // starting values for loan numbers
    public static int LoanNo = 100;

    // object for bankingOperations class
    public static bankingOperations bank = new bankingOperations();

    // method for writing user and user transactions to a .csv file
    public static void writeUser() {
        System.out.println("Writing data to CSV files");

        // writing users to users.csv
        for (Map.Entry<String, User> entry : UserOperations.users.entrySet()) {
            User user = entry.getValue();
            writeUserToCSV(user);
        }
        System.out.println("Users.csv file written successfully!");

        // writing transactions to transactions.csv
        FileWriter writer = null;
        try {
            String csvFile = "transactions.csv";
            writer = new FileWriter(csvFile, true);
            for(int i=0;i< bank.transactions.size();i++){
                TransactionActorRecord record = bank.transactions.get(i);
                transactionMessage msg = record.rec.status;
                if(msg == transactionMessage.TransferSuccessful || msg == transactionMessage.InsufficientFund){
                    i++;
                    writeTransactionToCSV(record,bank.transactions.get(i), writer);
                }
                else
                    writeTransactionToCSV(record, writer);
            }
        }
        catch (IOException e) {
            System.out.println("Error writing Transaction CSV file: " + e.getMessage());
        }
        finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                System.out.println("Error closing file Transaction writer: " + e.getMessage());
            }
        }
        System.out.println("Transactions.csv file written successfully!");
    }

    // writing user details to a .csv file
    public static void writeUserToCSV(User user) {
        String csvFile = "users.csv";
        FileWriter writer = null;

        // writing user details
        try {
            writer = new FileWriter(csvFile,true);
            if (user instanceof Admin) {
                writer.append(user.getUsername());
                writer.append(",");
                writer.append(user.getPassword());
                writer.append(",");
                writer.append(user.getFullname());
                writer.append(",");
                writer.append(user.getUserType().toString());
                writer.append(",");
                writer.append(String.valueOf(((Admin) user).authorised));
                writer.append("\n");
            }
            else{
                writer.append(user.getUsername());
                writer.append(",");
                writer.append(user.getPassword());
                writer.append(",");
                writer.append(user.getFullname());
                writer.append(",");
                writer.append(user.getUserType().toString());
                writer.append(",");

                // object for user-type: customer
                Customer cust = (Customer)user;
                writer.append(cust.getAccType().toString());
                writer.append(",");
                writer.append(Integer.toString(cust.getAccNo()));
                writer.append(",");
                writer.append(Double.toString(cust.getBalance()));
                writer.append("\n");
            }
        }
        catch (IOException e) {
            System.out.println("Error writing USER CSV file: " + e.getMessage());
        }
        finally {
                try {
                    writer.flush();
                    writer.close();
                }
                catch (IOException e) {
                    System.out.println("Error closing file USER writer: " + e.getMessage());
                }
        }
    }

    // method for writing transaction details to a .csv file
    public static void writeTransactionToCSV(TransactionActorRecord TARecord, FileWriter writer) {
        try {
            writer.append(String.valueOf(TARecord.rec.status));
            writer.append(",");
            writer.append(String.valueOf(TARecord.rec.transactionID));
            writer.append(",");
            writer.append(String.valueOf(TARecord.rec.debitFrom));
            writer.append(",");
            writer.append(String.valueOf(TARecord.rec.creditTo));
            writer.append(",");
            writer.append(String.valueOf(TARecord.rec.amount));
            writer.append(",");
            writer.append(TARecord.type.toString());
            writer.append(",");
            writer.append(String.valueOf(TARecord.remainingBalance));
            writer.append("\n");
        }
        catch (IOException e) {
            System.out.println("Error writing Transaction CSV file: " + e.getMessage());
        }
    }

    // method for writing transaction details to a .csv file
    public static void writeTransactionToCSV(TransactionActorRecord TARecord1, TransactionActorRecord TARecord2, FileWriter writer) {
        try {
            writer.append(String.valueOf(TARecord1.rec.status));
            writer.append(",");
            writer.append(String.valueOf(TARecord1.rec.transactionID));
            writer.append(",");
            writer.append(String.valueOf(TARecord1.rec.debitFrom));
            writer.append(",");
            writer.append(String.valueOf(TARecord1.rec.creditTo));
            writer.append(",");
            writer.append(String.valueOf(TARecord1.rec.amount));
            writer.append(",");
            writer.append(TARecord1.type.toString());
            writer.append(",");
            writer.append(String.valueOf(TARecord1.remainingBalance));
            writer.append(",");
            writer.append(TARecord2.type.toString());
            writer.append(",");
            writer.append(String.valueOf(TARecord2.remainingBalance));
            writer.append("\n");
        }
        catch (IOException e) {
            System.out.println("Error writing Transaction CSV file: " + e.getMessage());
        }
    }

    // method for reading the data from .csv file
    public static void CSVReader(){
        try {
            // creating a file object for users.csv file
            File file = new File("users.csv");
            // creating a new file is it does not exist
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    System.out.println("File created successfully.");
                } catch (IOException e) {
                    System.out.println("An error occurred while creating the file: " + e.getMessage());
                }
            }

            // creating a buffer reader object to read data from the users.csv file
            BufferedReader br = new BufferedReader(new FileReader("users.csv"));
            String line;

            // while loop for reading each row in the file
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String username = fields[0];
                String password = fields[1];
                String name = fields[2];
                userType userType = BankingSystemPack.userType.valueOf(fields[3]);

                // assigning authorization value to the admin
                if (userType == userType.ADMIN) {
                    int authorised = Integer.parseInt(fields[4]);
                    users.put(username, new Admin(username, password, name, userType, authorised));
                } else {
                    accType accType = BankingSystemPack.accType.valueOf(fields[4]);
                    int accNo = Integer.parseInt(fields[5]);
                    double balance = Double.parseDouble(fields[6]);

                    // adding the suer details to the users hashmap at the runtime
                    users.put(username, new Customer(username, password, name, userType, accType, balance, accNo));
                }
            }
            System.out.println("Users CSV file read successfully!");

            // closing the file buffer reader
            {
                try {
                    if (br != null) {
                        br.close();
                    }
                }
                catch (IOException e) {
                    System.out.println("Error closing file reader: " + e.getMessage());
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Users CSV file not found: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
        finally {
            File file = new File("users.csv");
            if (file.delete()) {
                System.out.println("Users.csv File deleted successfully");
            } else {
                System.out.println("Users.csv Failed to delete the file");
            }
        }

        // reading data from the transactions.csv file
        try {
            // creating a file object for transactions.csv file
            File file = new File("transactions.csv");
            if (!file.exists()) {
                try {
                    // create a new file if it does not exist
                    file.createNewFile();
                    System.out.println("File created successfully.");
                } catch (IOException e) {
                    System.out.println("An error occurred while creating the file: " + e.getMessage());
                }
            }

            // creating a buffer reader for reading data from transactions.csv file
            BufferedReader br = new BufferedReader(new FileReader("transactions.csv"));
            String line;

            // reading all the data from the transactions.csv file
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                transactionMessage msg = transactionMessage.valueOf(data[0]);
                int transactionID = Integer.parseInt(data[1]);
                int debitFrom = Integer.parseInt(data[2]);
                int creditTo = Integer.parseInt(data[3]);
                double amount = Double.parseDouble(data[4]);

                // creating a new transaction class object for storing the transaction details read from the file
                Transaction rec = new Transaction(transactionID, debitFrom, creditTo, amount, msg);

                if(msg == transactionMessage.TransferSuccessful || msg == transactionMessage.InsufficientFund){
                    userType sender = userType.valueOf(data[5]);
                    double senderBalance = Double.parseDouble(data[6]);
                    TransactionActorRecord obj = new TransactionActorRecord(rec, sender);
                    obj.setRemainingBalance(senderBalance);
                    bank.transactions.add(obj);

                    userType receiver = userType.valueOf(data[7]);
                    double receiverBalance = Double.parseDouble(data[8]);
                    TransactionActorRecord obj2 = new TransactionActorRecord(rec, receiver);
                    obj.setRemainingBalance(receiverBalance);
                    bank.transactions.add(obj2);
                }
                else {
                    userType actor = userType.valueOf(data[5]);
                    double remainingBalance = Double.parseDouble(data[6]);
                    TransactionActorRecord obj = new TransactionActorRecord(rec,actor);
                    obj.setRemainingBalance(remainingBalance);
                    bank.transactions.add(obj);
                }
            }
            System.out.println("CSV file read successfully!");

            // closing the file buffer reader
            {
                try {
                    if (br != null) {
                        br.close();
                    }
                }
                catch (IOException e) {
                    System.out.println("Error closing file reader: " + e.getMessage());
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Transactions CSV file not found: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("Error reading transactions CSV file: " + e.getMessage());
        }
        finally {
            File file = new File("transactions.csv");
            if (file.delete()) {
                System.out.println("Transactions.csv File deleted successfully");
            } else {
                System.out.println("Transactions.csv Failed to delete the file");
            }
        }
    }

    // method for registering a user
    public static void register() {
        boolean f = true;
        while(f){
            int choice;
            System.out.println("\n");
            System.out.println( "Register as : \n" +
                                "1)Customer \n" +
                                "2)Admin \n" +
                                "3)Exit");

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

                        // uncomment the following lines for running in intelliJ IDEA
                        System.out.print("Enter your password: ");
                        password1 = scanner.next();
                        System.out.print("Re-enter your password: ");
                        password2 = scanner.next();

                        // uncomment the following lines for running in VS Code
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
                        System.out.print("Type of account ? \n(Saving: 0, Current: 1) : ");
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
                        // uncomment the following lines for running in intelliJ IDEA
                        System.out.print("Enter your password: ");
                        password1 = scanner.next();
                        System.out.print("Re-enter your password: ");
                        password2 = scanner.next();

                        // uncomment the following lines for running in VS Code
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

                        String adminKey = "Admin@123";
                        if(key.equals(adminKey)){
                            UserOperations.users.put(username, new Admin(username, password1, fullname, userType.ADMIN,1));
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

    // method for resetting the user password
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
                // uncomment the following lines for running in intelliJ IDEA
                System.out.print("Enter your new password: ");
                String password1 = scanner.next();
                System.out.print("Re-enter your new password: ");
                String password2 = scanner.next();


                // uncomment the following lines for running in VS Code
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

    // method for user login
    public static boolean login() {
        System.out.print("\nEnter your username: ");
        String username = scanner.next();

        // uncomment the following lines for running in intelliJ IDEA
        System.out.print("Enter your password: ");
        String password = scanner.next();

        // uncomment the following lines for running in VS Code
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

    // method for user logout
    public static boolean logout() {
            UserOperations.Aactive = null;
            UserOperations.Cactive = null;
        return true;
    }
}
