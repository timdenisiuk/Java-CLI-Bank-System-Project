import java.util.Date;

public class Transaction {
    //transaction amount
    private double amount;

    //date of transaction
    private Date timestamp;

    //what transaction is for
    private String memo;

    //account transaction is linked to
    private Account inAccount;

    /**
     * 2 arguement constructor
     * @param amount    amount for the transaction
     * @param inAccount account transaction is done on
     */
    public Transaction(double amount, Account inAccount){
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    /**
     * 3 argument constructor
     * @param amount    transaction amount
     * @param memo      memo for transaction
     * @param inAccount account associated with it
     */
    public Transaction(double amount, String memo, Account inAccount){
        //call 2 argument constructor
        this(amount, inAccount);

        //update with memo
        this.memo = memo;
    }

    /**
     * Returns the amount of a transaction
     * @return Double indicating amount
     */
    public double getAmount(){
        return this.amount;
    }

    /**
     * string summarizes the transaction
     * @return  string representing summary
     */
    public String getSummaryLine(){
        if(this.amount >= 0){
            return String.format("%s : $%.02f : %s\n", this.timestamp.toString(), this.amount, this.memo);
        }
        else
            return String.format("%s : ($%.02f) : %s\n", this.timestamp.toString(), -this.amount, this.memo);
    }
}
