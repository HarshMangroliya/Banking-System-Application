package BankingSystemPack;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class bankingOperations implements Approve_Loan, Serializable {

    public static ArrayList<TransactionActorRecord> transactions = new ArrayList<>();
    public static ArrayList<LoanRecord> loanRecords = new ArrayList<>();
    int TransactionID ;

    bankingOperations(){
        this.TransactionID = 1000;
    }

    public void TransferMoney(Customer sender) {
        System.out.println("Your account balance is : " + sender.getBalance());

        Transaction record = new Transaction();
        record.debitFrom = sender.getAccNo();

        System.out.print("Enter Account number : ");
        record.creditTo = UserOperations.scanner.nextInt();

        System.out.print("Enter Amount : ");
        record.amount = UserOperations.scanner.nextDouble();

        //System.out.println("Enter Amount : "+record.amount+" "+record.amount+" "+sender.getBalance());

        boolean accFound = false;

        if (record.amount <= sender.getBalance()) {

            //System.out.print("Enter Amount : "+record.amount);


            for (Map.Entry<String, User> entry : UserOperations.users.entrySet()) {
                if (entry.getValue().getUserType() == userType.CUSTOMER) {

                    Customer receiver = (Customer) entry.getValue();
                    if (receiver.getAccNo() == record.creditTo) {
                        accFound = true;

                        TransactionActorRecord debitFrom = new TransactionActorRecord(record, userType.SENDER);
                        TransactionActorRecord creditTo = new TransactionActorRecord(record, userType.RECEIVER);

                        debitFrom.setRemainingBalance(sender.updateBalance(record.amount, '-'));
                        creditTo.setRemainingBalance(receiver.updateBalance(record.amount, '+'));
                        record.status = transactionMessage.TransferSuccessful;

                        transactions.add(debitFrom);
                        transactions.add(creditTo);

                        System.out.println("Money transfer successfully");
                        break;
                    }

                }

            }
            if (!accFound) {
                System.out.println("Receiver account not found");
                record.status = transactionMessage.ReceiverAccountNotFound;
                TransactionActorRecord debitFrom = new TransactionActorRecord(record, userType.SENDER);
                debitFrom.setRemainingBalance(sender.getBalance());
                transactions.add(debitFrom);
            }

        } else {
            record.status = transactionMessage.InsufficientFundSenderRecord;
            System.out.println("Failed - Insufficient fund");
            TransactionActorRecord debitFrom = new TransactionActorRecord(record, userType.SENDER);
            debitFrom.setRemainingBalance(sender.getBalance());
            transactions.add(debitFrom);
        }


    }

    @Override
    public void ApproveSavingAccLoan(Customer cactive) {
        int requiredBalance = 150000;
        LoanRecord record;
        if (requiredBalance > cactive.getBalance()) {
            System.out.println("Loan not approved - insufficient fund");
            record = new LoanRecord(cactive, UserOperations.LoanNo++, 0, "Loan not approved - insufficient fund");
        } else {
            System.out.println("Loan approved");
            double approvedLoan = cactive.getBalance() * 1.5;

            Transaction tRecord = new Transaction();

            tRecord.amount = approvedLoan;
            tRecord.debitFrom = 8888; //branch bank account number
            tRecord.creditTo = cactive.getAccNo();
            tRecord.status = transactionMessage.LoanApproved;


            TransactionActorRecord creditTo = new TransactionActorRecord(tRecord, userType.RECEIVER);
            creditTo.setRemainingBalance(cactive.updateBalance(tRecord.amount, '+'));

            transactions.add(creditTo);

            record = new LoanRecord(cactive, UserOperations.LoanNo++, approvedLoan, "Loan approved");
        }
        loanRecords.add(record);
    }

    @Override
    public void ApproveCurrentAccLoan(Customer cactive) {
        int requiredBalance = 500000;
        LoanRecord record;
        if (requiredBalance > cactive.getBalance()) {
            System.out.println("Loan not approved - insufficient fund");
            record = new LoanRecord(cactive, UserOperations.LoanNo++, 0, "Loan not approved - insufficient fund");
        } else {
            System.out.println("Loan approved");
            double approvedLoan = cactive.getBalance() * 3;

            Transaction tRecord = new Transaction();

            tRecord.amount = approvedLoan;
            tRecord.debitFrom = 8888; //branch bank account number
            tRecord.creditTo = cactive.getAccNo();
            tRecord.status = transactionMessage.LoanApproved;


            TransactionActorRecord creditTo = new TransactionActorRecord(tRecord, userType.RECEIVER);
            creditTo.setRemainingBalance(cactive.updateBalance(tRecord.amount, '+'));

            transactions.add(creditTo);

            record = new LoanRecord(cactive, UserOperations.LoanNo++, approvedLoan, "Loan approved");
        }

        loanRecords.add(record);

    }

    public void TransactionHistory(Customer cactive) {

        int accNo = cactive.getAccNo();

        System.out.println("Current account balance is : " + cactive.getBalance());
        System.out.println("Account transactions : ");
        System.out.println("TransID   Debitfrom   CreditTo  Amount   RemainingBalance   Status ");


        for (TransactionActorRecord i : transactions) {
            if (i.rec.debitFrom == accNo && i.type == userType.SENDER)
                i.printRecord();


            if (i.rec.creditTo == accNo && i.type == userType.RECEIVER)
                i.printRecord();

        }


    }

    public void ShowLoanApprovals(Customer cactive) {
        int bankAcc = cactive.getAccNo();
        System.out.println("loanNumber" + "  " + "approvedAmt" + "  " + "status");
        for (LoanRecord i : loanRecords) {
            if (bankAcc == i.cust.getAccNo())
                i.printRecord();
        }
    }

    public void showAccountDetail() {
        System.out.println("Account no "+"Username "+"balance "+"acc_type");

        for (Map.Entry<String, User> entry : UserOperations.users.entrySet()) {
            if (entry.getValue().getUserType() == userType.CUSTOMER) {

                Customer customer = (Customer) entry.getValue();

                customer.printAccRecord();

            }
        }
    }

    public void adminTransferMoney(){

        if (UserOperations.Aactive.authorised == 1) {
            Transaction record = new Transaction();

            System.out.print("Enter Debit From Account number : ");
            record.debitFrom = UserOperations.scanner.nextInt();

            System.out.print("Enter Credit to Account number : ");
            record.creditTo = UserOperations.scanner.nextInt();

            System.out.print("Enter Amount : ");
            record.amount = UserOperations.scanner.nextDouble();

            Customer debitFrom = null;
            Customer creditTo = null;

            for (Map.Entry<String, User> entry : UserOperations.users.entrySet()) {
                if (entry.getValue().getUserType() == userType.CUSTOMER) {

                    Customer customer = (Customer) entry.getValue();

                    if (customer.getAccNo() == record.debitFrom)
                        debitFrom = customer;

                    if (customer.getAccNo() == record.creditTo)
                        creditTo = customer;

                    if (debitFrom != null && creditTo != null)
                        break;


                }

            }

            if (debitFrom != null && creditTo != null) {

                TransactionActorRecord debitRec = new TransactionActorRecord(record, userType.SENDER);
                TransactionActorRecord creditRec = new TransactionActorRecord(record, userType.RECEIVER);

                if (debitFrom.getBalance() >= record.amount) {

                    debitRec.setRemainingBalance(debitFrom.updateBalance(record.amount, '-'));
                    creditRec.setRemainingBalance(creditTo.updateBalance(record.amount, '+'));

                    record.status = transactionMessage.TransferSuccessful;

                    System.out.println("Money transfer successfully");

                } else {
                    debitRec.setRemainingBalance(debitFrom.getBalance());
                    creditRec.setRemainingBalance(creditTo.getBalance());

                    record.status = transactionMessage.InsufficientFund;
                    System.out.println("Sender : insufficient fund");
                }

                transactions.add(debitRec);
                transactions.add(creditRec);

            } else if (debitFrom != null) {

                TransactionActorRecord debitRec = new TransactionActorRecord(record, userType.SENDER);
                debitRec.setRemainingBalance(debitFrom.getBalance());
                record.status = transactionMessage.ReceiverAccountNotFound;

                System.out.println("Receiver account not found");
                transactions.add(debitRec);

            } else if (creditTo != null) {

                TransactionActorRecord creditRec = new TransactionActorRecord(record, userType.RECEIVER);
                creditRec.setRemainingBalance(creditTo.getBalance());
                record.status = transactionMessage.SenderAccountNotFound;

                System.out.println("Sender account not found");
                transactions.add(creditRec);

            } else {
                TransactionActorRecord TArec = new TransactionActorRecord(record, userType.NONE);
                TArec.setRemainingBalance(0);
                record.status = transactionMessage.BothAccountNotFound;

                System.out.println("Receiver & Sender acc not found");
                transactions.add(TArec);

            }
        }
        else
            System.out.println("You dont have Access to this module");
    }

    public void addMoney() {
        if (UserOperations.Aactive.authorised == 1) {
            Transaction record = new Transaction();

            record.debitFrom = 8888;

            System.out.print("Enter Credit to Account number : ");
            record.creditTo = UserOperations.scanner.nextInt();

            System.out.print("Enter Amount : ");
            record.amount = UserOperations.scanner.nextDouble();


            Customer creditTo = null;

            for (Map.Entry<String, User> entry : UserOperations.users.entrySet()) {
                if (entry.getValue().getUserType() == userType.CUSTOMER) {

                    Customer customer = (Customer) entry.getValue();

                    if (customer.getAccNo() == record.creditTo) {
                        creditTo = customer;
                        break;
                    }
                }

            }

            if (creditTo != null) {

                TransactionActorRecord creditRec = new TransactionActorRecord(record, userType.RECEIVER);

                creditRec.setRemainingBalance(creditTo.updateBalance(record.amount, '+'));

                record.status = transactionMessage.MoneyDeposited;

                System.out.println("Money added successfully");

                transactions.add(creditRec);

            }
            else {

                TransactionActorRecord Rec = new TransactionActorRecord(record, userType.NONE);
                Rec.setRemainingBalance(0);
                record.status = transactionMessage.ReceiverAccountNotFound;

                System.out.println("Receiver account not found");
                transactions.add(Rec);

            }
        }
        else
            System.out.println("You dont have Access to this module");
    }

    public void WithdrawMoney() {
        if (UserOperations.Aactive.authorised == 1) {
            Transaction record = new Transaction();

            System.out.print("Enter Debit From Account number : ");
            record.debitFrom = UserOperations.scanner.nextInt();

            record.creditTo = 8888;

            System.out.print("Enter Amount : ");
            record.amount = UserOperations.scanner.nextDouble();

            Customer debitFrom = null;

            for (Map.Entry<String, User> entry : UserOperations.users.entrySet()) {
                if (entry.getValue().getUserType() == userType.CUSTOMER) {

                    Customer customer = (Customer) entry.getValue();

                    if (customer.getAccNo() == record.debitFrom) {
                        debitFrom = customer;
                        break;
                    }

                }

            }

            if (debitFrom != null) {

                TransactionActorRecord debitRec = new TransactionActorRecord(record, userType.SENDER);

                if (debitFrom.getBalance() >= record.amount) {

                    debitRec.setRemainingBalance(debitFrom.updateBalance(record.amount, '-'));

                    record.status = transactionMessage.MoneyWithdrawal;

                    System.out.println("Money withdraw successfully");

                }
                else {

                    debitRec.setRemainingBalance(debitFrom.getBalance());

                    record.status = transactionMessage.InsufficientFundSenderRecord;
                    System.out.println("Sender : insufficient fund");
                }

                transactions.add(debitRec);


            }
            else {
                TransactionActorRecord TArec = new TransactionActorRecord(record, userType.NONE);
                TArec.setRemainingBalance(0);
                record.status = transactionMessage.SenderAccountNotFound;

                System.out.println("Withdrawal acc not found");
                transactions.add(TArec);

            }
        } else
            System.out.println("You dont have Access to this module");
    }
}