import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.util.ArrayList;
import java.security.MessageDigest;

public class User {
    //User first name
    private String firstName;

    //User lastName
    private String lastName;
    //Universal user ID
    private String uuid;

    //MD5 hash algorithm
    private byte pinHash[];

    //List of accounts
    private ArrayList<Account> accounts;

    /**
     * Create a new user
     * @param firstName users first name
     * @param lastName  users last name
     * @param pin       users pin protected by an MD5 hash
     * @param theBank   bank object user is a customer of
     */
    public User(String firstName, String lastName, String pin, Bank theBank){
        this.firstName = firstName;
        this.lastName = lastName;

        // store pin's MD5 hash for security
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        //get a new unique uuid for user
        this.uuid = theBank.getNewUserUUID();

        //create empty account list
        this.accounts = new ArrayList<Account>();

        System.out.printf("Mew user %s, %s, with ID %s created \n", lastName, firstName, this.uuid);

    }

    /**
     * Add account for the user
     * @param a  Account object to add
     */
    public void addAccount(Account a){
        this.accounts.add(a);
    }

    /**
     * Returns user UUID
     * @return  the uuid as a string
     */
    public String getUUID(){
        return this.uuid;
    }

    /**
     * Checks for a valid pin using MD5
     * @param pin   pin to be checked
     * @return      true if pin is correct, false if it isn't. False at the end to appease Java
     */
    public boolean validatePin(String pin){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        return false;
    }

    /**
     * Returns the user's first name
     * @return  user's first name
     */
    public String getFirstName(){
        return this.firstName;
    }

    /**
     * Prints summaries for accounts
     */
    public void printAccountsSummary(){

        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for(int i = 0; i < this.accounts.size(); i++){
            System.out.printf("  %d) %s \n", i+1, this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Gives the number of user accounts
     * @return an int representing number of accounts
     */
    public int getNumAccounts(){
        return this.accounts.size();
    }

    /**
     *
     * @param index
     */
    public void printAcctTransHistory(int index){
        this.accounts.get(index).printTransHistory();
    }

    /**
     * Gets the account balance from a specific account
     * @param index account index to be looked at
     * @return  double representing specific account balance
     */
    public double getAccountBal(int index){
        return this.accounts.get(index).getBalance();
    }

    /**
     * Gets the UUID from a specific account
     * @param index account index to be looked at
     * @return  string representing specific account uuid
     */
    public String getAccountUUID(int index){
        return this.accounts.get(index).getUUID();
    }

    public void addAccountTrans(int index, double amount, String memo){
        this.accounts.get(index).addTransaction(amount, memo);
    }
}
