import java.util.ArrayList;
import java.util.Random;

public class Bank {
    //name of the bank
    private String name;

    //list of bank users
    private ArrayList<User> users;

    //list of total accounts
    private ArrayList<Account> accounts;

    /**
     * Initializes a new bank object
     * @param name  name of the bank
     */
    Bank(String name){
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Generate new univerally unique id for user
     * @return  returns a new uuid
     */
    public String getNewUserUUID(){
        //inits
        String uuid;
        Random rng = new Random();
        boolean nonUnique;
        int length = 6;


        do{
            //Generate uuid
            uuid = "";
            for(int i = 0; i < length; i++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            //Check if unique
            nonUnique = false;
            for(User u : users){
                if(uuid.compareTo(u.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }

        }while(nonUnique == true);

        return uuid;
    }

    /**
     * Generate new uuid foe each account
     * @return  new uuid as a string
     */
    public String getNewAccountUUID(){
        //inits
        String uuid;
        Random rng = new Random();
        boolean nonUnique;
        int length = 10;


        do{
            //Generate uuid
            uuid = "";
            for(int i = 0; i < length; i++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            //Check if unique
            nonUnique = false;
            for(Account a : accounts){
                if(uuid.compareTo(a.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }

        }while(nonUnique == true);

        return uuid;
    }

    /**
     * Adds an account to the bank database
     * @param a account to be added
     */
    public void addAccount(Account a){
        this.accounts.add(a);
    }

    /**
     * Adds a user to the bank records with an account to go with it
     * @param firstName first name of the user
     * @param lastName  last name of the user
     * @param pin       user pin
     * @return          new user object
     */
    public User addUser(String firstName, String lastName, String pin){

        //Create user, add to list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        //Create savings account for the user
        Account newAccount = new Account("Savings", newUser, this);

        //Adds account for the user
        newUser.addAccount(newAccount);

        //Adds account for the database
        this.accounts.add(newAccount);

        return newUser;
    }

    /**
     * Get user object associated with ID and pin
     * @param userID given userID to validate
     * @param pin   given pin to validate
     * @return  either user object if found, or null if not
     */
    public User userLogin(String userID, String pin){
        //iterate through every user in list
        for(User u : this.users){
            //checks for valid user id and valid pin
            if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;
            }
        }
        //returns null if nothing correct was found
        return null;
    }

    /**
     * Returns bank name
     * @return Banks name as a string
     */
    public String getName(){
        return this.name;
    }
}
