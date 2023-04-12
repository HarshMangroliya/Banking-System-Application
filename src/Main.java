import BankingSystemPack.*;
import BankingSystemPack.Customer;
public class Main {
    public static void main(String[] args) {

        UserOperations.users.put("a", new Admin("a", "a", "admmin bro",userType.ADMIN));
        UserOperations.users.put("cs", new Customer("cs", "cs", "saving cutomer",userType.CUSTOMER,accType.SAVING,500000));
        UserOperations.users.put("cc", new Customer("cc", "cs", "currect customer",userType.CUSTOMER,accType.CURRENT,10000002));


        boolean f1 = true;
        while(f1) {

            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forgot password");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = UserOperations.scanner.nextInt();


            switch (choice) {
                case 1:
                    // The register method from the UserOperations class is called
                    UserOperations.register();
                    break;
                case 2:

                    if (UserOperations.login() ) {
                        System.out.println("---------------------------------------------------------------------");
                        if(UserOperations.Aactive != null){
                            //Admin
                            //UserOperations.PAdmin = (Admin) UserOperations.active;
                            boolean f2 = true;
                            while(f2){

                                // The menu is displayed after the user has successfully logged in,
                                // This menu is wrapped inside an infinite while loop
                                System.out.println("\nWelcome Admin, "+UserOperations.Aactive.getFullname());
                                //RetailStore.adminDisplayProducts();
                                System.out.println("\n1. Add Product");
                                System.out.println("2. Calculate fine");
                                System.out.println("3. Calculate Total profit");
                                System.out.println("4. Set discount Rate");
                                System.out.println("5. Set Fine rate Rate");
                                System.out.println("6. Logout");
                                System.out.print("Enter your choice: ");
                                choice = UserOperations.scanner.nextInt();

                                switch (choice) {

                                    /*case 1: {
                                        RetailStore.addProduct();
                                        break;
                                    }

                                    case 2: {
                                        RetailStore.calculateFine();
                                        break;
                                    }

                                    case 3: {
                                        RetailStore.calTotalProfit();
                                        break;
                                    }

                                    case 4:{
                                        RetailStore.setDiscountRate();
                                        break;
                                    }
                                    case 5:{
                                        RetailStore.setFineRate();
                                        break;
                                    }
                                    */
                                    case 6: {
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

                        else if (UserOperations.Cactive != null){
                            //Customer

                            boolean f2 = true;
                            while(f2) {
                                System.out.println("\nWelcome Customer, " + UserOperations.Cactive.getFullname());
                                System.out.println("Your account balance is :  " + UserOperations.Cactive.getBalance());

                                System.out.println("\n1. Transfer Money");
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
                                    /*
                                    case 2: {
                                        RetailStore.cancelPurchase();
                                        break;
                                    }*/

                                    case 3: {

                                        if (UserOperations.Cactive.getAccType() == accType.SAVING)
                                            UserOperations.bank.ApproveSavingAccLoan(UserOperations.Cactive);
                                        else
                                            UserOperations.bank.ApproveCurrentAccLoan(UserOperations.Cactive);

                                        break;
                                    }
                                        /*
                                    case 4: {
                                        System.out.println("Your wallet balance is : " + UserOperations.PUser.getBalance());
                                        RetailStore.purchaseDetail();
                                        break;
                                    }*/

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
                    //UserOperations.printUser();
                    UserOperations.resetPassword();
                    //UserOperations.printUser();
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
}