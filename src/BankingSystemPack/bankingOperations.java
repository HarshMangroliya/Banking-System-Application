package BankingSystemPack;
import java.util.ArrayList;
import java.util.Map;

public class bankingOperations implements Approve_Loan {

    public static ArrayList<TransactionActorRecord> transactions = new ArrayList<>();
    public static ArrayList<LoanRecord> loanRecords = new ArrayList<>();
    int TransactionID = 1000;


    public void TransferMoney(Customer sender){
        System.out.println("Your account balance is : "+sender.getBalance());

        Transaction record = new Transaction(TransactionID++);
        record.debitFrom = sender.getAccNo();

        System.out.print("Enter Account number : ");
        record.creditTo = UserOperations.scanner.nextInt();

        System.out.print("Enter Amount : ");
        record.amount = UserOperations.scanner.nextDouble();

        //System.out.println("Enter Amount : "+record.amount+" "+record.amount+" "+sender.getBalance());

        boolean accFound = false;

        if(record.amount <= sender.getBalance()){

            //System.out.print("Enter Amount : "+record.amount);


            for (Map.Entry<String, User> entry : UserOperations.users.entrySet())
            {
                if(entry.getValue().getUserType() == userType.CUSTOMER) {

                    Customer receiver = (Customer) entry.getValue();
                    if(receiver.getAccNo() == record.creditTo){
                        accFound = true;

                        TransactionActorRecord debitFrom = new TransactionActorRecord(record,userType.SENDER);
                        TransactionActorRecord creditTo = new TransactionActorRecord(record, userType.RECEIVER);

                        debitFrom.setRemainingBalance(sender.updateBalance(record.amount,'-'));
                        creditTo.setRemainingBalance(receiver.updateBalance(record.amount, '+'));
                        record.status = "Successful";

                        transactions.add(debitFrom);
                        transactions.add(creditTo);

                        System.out.println("Money transfer successfully");
                        break;
                    }

                }

            }
            if(!accFound) {
                System.out.println("Receiver account not found");
                record.status = "Failed - Receiver account not found";
                TransactionActorRecord debitFrom = new TransactionActorRecord(record,userType.SENDER);
                debitFrom.setRemainingBalance(sender.getBalance());
                transactions.add(debitFrom);
            }

        }
        else {
            record.status = "Failed - Insufficient fund";
            System.out.println("Failed - Insufficient fund");
            TransactionActorRecord debitFrom = new TransactionActorRecord(record,userType.SENDER);
            debitFrom.setRemainingBalance(sender.getBalance());
            transactions.add(debitFrom);
        }




    }

    @Override
    public void ApproveSavingAccLoan(Customer cactive) {
        int requiredBalance = 150000;
        LoanRecord record;
        if(requiredBalance > cactive.getBalance()){
            System.out.println("Loan not approved - insufficient fund");
            record = new LoanRecord(cactive,UserOperations.LoanNo++,0,"Loan not approved - insufficient fund");
        }
        else{
            System.out.println("Loan approved");
            double approvedLoan = cactive.getBalance() * 1.5;

            Transaction tRecord = new Transaction(TransactionID++);

            tRecord.amount = approvedLoan;
            tRecord.debitFrom = 8084; //branch bank account number
            tRecord.creditTo = cactive.getAccNo();
            tRecord.status = "Successful";


            TransactionActorRecord creditTo = new TransactionActorRecord(tRecord, userType.RECEIVER);
            creditTo.setRemainingBalance(cactive.updateBalance(tRecord.amount, '+'));

            transactions.add(creditTo);

            record = new LoanRecord(cactive,UserOperations.LoanNo++,approvedLoan,"Loan approved");
        }
        loanRecords.add(record);
    }

    @Override
    public void ApproveCurrentAccLoan(Customer cactive) {
        int requiredBalance = 500000;
        LoanRecord record;
        if(requiredBalance > cactive.getBalance()){
            System.out.println("Loan not approved - insufficient fund");
            record = new LoanRecord(cactive,UserOperations.LoanNo++,0,"Loan not approved - insufficient fund");
        }
        else{
            System.out.println("Loan approved");
            double approvedLoan = cactive.getBalance() * 3;

            Transaction tRecord = new Transaction(TransactionID++);

            tRecord.amount = approvedLoan;
            tRecord.debitFrom = 8084; //branch bank account number
            tRecord.creditTo = cactive.getAccNo();
            tRecord.status = "Successful";


            TransactionActorRecord creditTo = new TransactionActorRecord(tRecord, userType.RECEIVER);
            creditTo.setRemainingBalance(cactive.updateBalance(tRecord.amount, '+'));

            transactions.add(creditTo);

            record = new LoanRecord(cactive,UserOperations.LoanNo++,approvedLoan,"Loan approved");
        }

        loanRecords.add(record);

    }


    public void TransactionHistory(Customer cactive){

        int accNo = cactive.getAccNo();

        System.out.println("Current account balance is : "+cactive.getBalance());
        System.out.println("Account transactions : ");
        System.out.println("TransID   Debitfrom   CreditTo  Amount   RemainingBalance   Status ");


        for(TransactionActorRecord i : transactions){
            if(i.rec.debitFrom == accNo && i.type == userType.SENDER)
                i.printRecord();


            if(i.rec.creditTo == accNo && i.type == userType.RECEIVER)
                i.printRecord();

        }




    }


    public void ShowLoanApprovals(Customer cactive){
        int bankAcc = cactive.getAccNo();
        System.out.println("loanNumber"+"  "+"approvedAmt"+"  "+"status");
        for(LoanRecord i: loanRecords){
            if(bankAcc == i.cust.getAccNo())
                i.printRecord();
        }

    }



}
