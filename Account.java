import java.util.ArrayList;

public class Account {
    //account name
    private String name;

    //account id number
    private String uuid;

    //account user holder
    private User holder;

    //account transactions
    private ArrayList<Transaction> transactions;

    /**
     * Constructor for account class
     * @param name      name of the account holder
     * @param holder    User object for the account holder
     * @param theBank   Bank object for the account holder's bank
     */
    public Account(String name, User holder, Bank theBank){
        this.name = name;
        this.holder = holder;

        this.uuid = theBank.getNewAccountUUID();
        this.transactions = new ArrayList<Transaction>();

    }

    /**
     * Returns the accounts uuid
     * @return account uuid as a string
     */
    public String getUUID(){
        return this.uuid;
    }

    /**
     * Shows a summary of an account, including id, balance, and name of the account
     * @return returns a formatted string
     */
    public String getSummaryLine(){
        double balance = this.getBalance();
        if(balance >= 0)
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        else
            return String.format("%s : ($%.02f) : %s", this.uuid, balance, this.name);
    }

    /**
     * Returns the total balance of the account
     * @return a double representing the balance
     */
    public double getBalance(){
        double balance = 0;
        for(Transaction t : transactions){
            balance += t.getAmount();
        }
        return balance;
    }

    /**
     * Prints the transaction history for the account
     */
    public void printTransHistory(){
        System.out.printf("\nTransaction history for account %s: \n", this.uuid);
        for(int i = this.transactions.size() - 1; i >= 0; i--){
            System.out.printf(this.transactions.get(i).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Adds a transaction to the list
     * @param amount   money in the transaction
     * @param memo     memo for the transaction
     */
    public void addTransaction(double amount, String memo){
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }


}
