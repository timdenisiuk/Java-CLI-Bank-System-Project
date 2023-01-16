import java.util.ArrayList;
import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank("Bank of America");

        //add user, also creates savings
        User user1 = bank.addUser("John", "Doe", "1234");

        //add checking account
        Account newAccount = new Account("Checking", bank);
        user1.addAccount(newAccount);
        bank.addAccount(newAccount);

        User curUser;

        while (true) {
            //Stay in login until successful login
            curUser = ATM.mainMenuPrompt(bank, sc);

            //Stay in menu until user exits
            ATM.printUserMenu(curUser, sc);
        }
    }

    /**
     * Main menu interface
     *
     * @param bank the bank we are working with
     * @param sc   scanner object for input
     * @return returns an authenticated user
     */
    public static User mainMenuPrompt(Bank bank, Scanner sc) {
        //inits
        String userID, pin;
        User authUser;

        //Prompt user for ID/Pin combo
        do {
            System.out.printf("\n\nWelcome to %s\n\n", bank.getName());
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

            //try to obtain User from given credentials
            authUser = bank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID / pin. Please try again");
            }

        } while (authUser == null);

        return authUser;
    }


    public static void printUserMenu(User user, Scanner sc) {
        //print user account summary
        user.printAccountsSummary();

        int choice;

        do {
            System.out.printf("Welcome %s, what would you like to do?\n", user.getFirstName());
            System.out.println("    1) Show account transaction history");
            System.out.println("    2) Make withdrawal");
            System.out.println("    3) Deposit");
            System.out.println("    4) Transfer");
            System.out.println("    5) Quit");
            System.out.println();
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice, Please choose 1-5");
            }
        } while (choice < 1 || choice > 5);

        switch (choice) {
            case 1:
                ATM.showTransHistory(user, sc);
                break;
            case 2:
                ATM.withdraw(user, sc);
                break;
            case 3:
                ATM.deposit(user, sc);
                break;
            case 4:
                ATM.transfer(user, sc);
                break;
            case 5:
                sc.nextLine();
        }
        //redisplay menu unless user wants to quit
        if (choice != 5) {
            ATM.printUserMenu(user, sc);
        }

    }

    /**
     * prints the transaction history for a specific account
     *
     * @param user the user object that stores the data
     * @param sc   the scanner object defined earlier
     */
    public static void showTransHistory(User user, Scanner sc) {

        int account;

        do {
            System.out.printf("Enter the number (1-%d) of the account whose " +
                    "transactions you want to see: ", user.getNumAccounts());
            account = sc.nextInt() - 1;
            if (account < 0 || account >= user.getNumAccounts())
                System.out.printf("Invalid Choice. Please select a number between (1-%d)", user.getNumAccounts());
        } while (account < 0 || account >= user.getNumAccounts());

        user.printAcctTransHistory(account);
    }

    /**
     * Transfers money from one account to another
     * @param user User object in question
     * @param sc   previously defined scanner object
     */
    public static void transfer(User user, Scanner sc){

        int fromAccount, toAccount;
        double amount, accountBalance;
        //Checking for valid first account
        do{
            System.out.printf("Enter the number (1-%d) of the account \n to transfer from: ", user.getNumAccounts());
            fromAccount = sc.nextInt() - 1;
            if(fromAccount < 0 || fromAccount >= user.getNumAccounts()){
                System.out.printf("Invalid Choice. Please select a number between (1-%d)", user.getNumAccounts());
            }
        }while(fromAccount < 0 || fromAccount >= user.getNumAccounts());

        //Balance of first account
        accountBalance = user.getAccountBal(fromAccount);

        //Checking for valid second account
        do{
            System.out.printf("Enter the number (1-%d) of the account \n to transfer to: ", user.getNumAccounts());
            toAccount = sc.nextInt() - 1;
            if(toAccount < 0 || toAccount >= user.getNumAccounts()){
                System.out.printf("Invalid Choice. Please select a number between (1-%d)", user.getNumAccounts());
            }
        }while(toAccount < 0 || toAccount >= user.getNumAccounts());

        //Check for amount of money to be transferred
        do{
            System.out.printf("Enter the amount to transfer (max %.02f): ", accountBalance);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than 0");
            }
            else if(amount > accountBalance){
                System.out.printf("Amount must be less than your current balance \n" +
                        "of $%.02f", accountBalance);
            }
        }while(amount < 0 || amount > accountBalance);

        //Perform the transfer
        user.addAccountTrans(fromAccount, -1*amount, String.format(
                "Transfer to account %s", user.getAccountUUID(toAccount)));
        user.addAccountTrans(toAccount, amount, String.format(
                "Transfer from account %s", user.getAccountUUID(fromAccount)));
    }

    /**
     * withdraw from your bank account
     * @param user user object to withdraw from
     * @param sc   scanner object for input
     */
    public static void withdraw(User user, Scanner sc){
        double accountBalance, amount;
        int account;
        String memo;

        //Find valid account to withdraw from
        do{
            System.out.printf("Enter the number (1-%d) of the account \n to withdraw from: ", user.getNumAccounts());
            account = sc.nextInt() - 1;
            if(account < 0 || account >= user.getNumAccounts()){
                System.out.printf("Invalid Choice. Please select a number between (1-%d)", user.getNumAccounts());
            }
        }while(account < 0 || account >= user.getNumAccounts());

        //Grab the found account's balance
        accountBalance = user.getAccountBal(account);

        //Grab a valid amount of money to withdraw
        do{
            System.out.printf("Enter the amount to withdraw (max %.02f): ", accountBalance);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than 0");
            }
            else if(amount > accountBalance){
                System.out.printf("Amount must be less than your current balance \n" +
                        "of $%.02f", accountBalance);
            }
        }while(amount < 0 || amount > accountBalance);

        //Perform one more input line
        sc.nextLine();

        //Get a memo from the user
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        //perform the withdrawal
        user.addAccountTrans(account, -1*amount, memo);
    }

    /**
     * Deposit money to an account
     * @param user User object to deposit money into
     * @param sc Scanner object created earlier
     */
    public static void deposit(User user, Scanner sc){
        double accountBalance, amount;
        int account;
        String memo;

        //Find valid account to withdraw from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n to deposit to: ", user.getNumAccounts());
            account = sc.nextInt() - 1;
            if(account < 0 || account >= user.getNumAccounts()){
                System.out.printf("Invalid Choice. Please select a number between (1-%d)", user.getNumAccounts());
            }
        }while(account < 0 || account >= user.getNumAccounts());


        //Grab a valid amount of money to withdraw
        do{
            System.out.println("Enter the amount to deposit");
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than 0");
            }

        }while(amount < 0);

        //Perform one more input line
        sc.nextLine();

        //Get a memo from the user
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        //perform the withdrawal
        user.addAccountTrans(account, amount, memo);
    }
}
