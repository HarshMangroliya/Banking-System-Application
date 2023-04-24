import BankingSystemPack.*;

public class Main {
    public static void main(String[] args) {
        /*
        UserOperations.users.put("aa", new Admin("aa", "aa", "Aadmmin bro",userType.ADMIN,1));
        UserOperations.users.put("ua", new Admin("ua", "ua", "Uadmmin bro",userType.ADMIN,0));
        UserOperations.users.put("cs", new Customer("cs", "cs", "saving customer",userType.CUSTOMER,accType.SAVING,250000));
        UserOperations.users.put("cc", new Customer("cc", "cc", "currect customer",userType.CUSTOMER,accType.CURRENT,600000));
        */

        // calling the CSVReader method for loading all the file data at the run time
        UserOperations.CSVReader();

        // enclosing the whole main menu in a try-finally block
        try {
            boolean f1 = true;
            while (f1) {
                System.out.println("\n");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Forgot password");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = UserOperations.scanner.nextInt();

                switch (choice) {
                    case 1:
                        // the register method from the UserOperations class is called
                        UserOperations.register();
                        break;
                    case 2:
                        // the login method from UserOperations class is called
                        if (UserOperations.login()) {
                            System.out.println("---------------------------------------------------------------------");
                            if (UserOperations.Aactive != null) {
                                // login for Admin
                                boolean f2 = true;
                                while (f2) {
                                    // The menu is displayed after the user has successfully logged in,
                                    // This menu is wrapped inside an infinite while loop
                                    System.out.println("\nWelcome Admin, " + UserOperations.Aactive.getFullname());
                                    //RetailStore.adminDisplayProducts();
                                    System.out.println("\n");
                                    System.out.println("1. Show account details");
                                    System.out.println("2. TransferMoney");
                                    System.out.println("3. Add money");
                                    System.out.println("4. Withdraw Money");
                                    System.out.println("5. Logout");
                                    System.out.print("Enter your choice: ");
                                    choice = UserOperations.scanner.nextInt();

                                    switch (choice) {
                                        case 1: {
                                            UserOperations.bank.showAccountDetail();
                                            break;
                                        }
                                        case 2: {
                                            UserOperations.bank.adminTransferMoney();
                                            break;
                                        }
                                        case 3: {
                                            UserOperations.bank.addMoney();
                                            break;
                                        }
                                        case 4: {
                                            UserOperations.bank.WithdrawMoney();
                                            break;
                                        }
                                        /*
                                        case 5:{
                                            RetailStore.setFineRate();
                                            break;
                                        }
                                        */
                                        case 5: {
                                            if (UserOperations.logout()) {
                                                System.out.println("\nUser logged out successfully.");
                                                f2 = false;
                                            }
                                            break;
                                        }
                                        default: {
                                            System.out.println("\nInvalid choice.");
                                            break;
                                        }
                                    }
                                }
                            } else if (UserOperations.Cactive != null) {
                                // login for customer
                                boolean f2 = true;
                                while (f2) {
                                    System.out.println("\nWelcome Customer, " + UserOperations.Cactive.getFullname());
                                    System.out.println("Your account balance is : " + UserOperations.Cactive.getBalance());
                                    System.out.println("Your account number is  : " + UserOperations.Cactive.getAccNo());
                                    System.out.println("\n");
                                    System.out.println("1. Transfer Money");
                                    System.out.println("2. Transaction History");
                                    System.out.println("3. Apply for loan");
                                    System.out.println("4. Loan application status");
                                    System.out.println("5. Logout");
                                    System.out.print("Enter your choice: ");
                                    choice = UserOperations.scanner.nextInt();
                                    switch (choice) {
                                        case 1: {
                                            UserOperations.bank.TransferMoney(UserOperations.Cactive);
                                            break;
                                        }
                                        case 2: {
                                            UserOperations.bank.TransactionHistory(UserOperations.Cactive);
                                            break;
                                        }
                                        case 3: {
                                            if (UserOperations.Cactive.getAccType() == accType.SAVING)
                                                UserOperations.bank.ApproveSavingAccLoan(UserOperations.Cactive);
                                            else
                                                UserOperations.bank.ApproveCurrentAccLoan(UserOperations.Cactive);
                                            break;
                                        }
                                        case 4: {
                                            UserOperations.bank.ShowLoanApprovals(UserOperations.Cactive);
                                            break;
                                        }
                                        case 5: {
                                            if (UserOperations.logout()) {
                                                System.out.println("\nUser logged out successfully.");
                                                f2 = false;
                                            }
                                            break;
                                        }
                                        default: {
                                            System.out.println("\nInvalid choice.");
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 3:
                        UserOperations.resetPassword();
                        break;
                    case 4:
                        f1 = false;
                        System.out.println("\nProgram exit successful.");
                        break;
                    default:
                        System.out.println("\nInvalid choice.");
                        break;
                }
            }
        }
        finally {
            UserOperations.writeUser();
        }
    }
}

