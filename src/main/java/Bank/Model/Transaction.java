package Bank.Model;

public class Transaction {
    private int transactionId;
    private int accountId;
    private long amount;
    private String accountType;
    private String transactionType;
    private Account account;

    public Transaction() {
    }

    public Transaction(int transactionId, int accountId, long amount, String accountType, String transactionType, Account account) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.accountType = accountType;
        this.transactionType = transactionType;
        this.account = account;
    }

    public Transaction(int transactionId, int accountId, long amount, String accountType, String transactionType) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.accountType = accountType;
        this.transactionType = transactionType;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    //end class
}
