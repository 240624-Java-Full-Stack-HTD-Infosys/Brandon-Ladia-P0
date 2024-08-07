package Bank.Model;

public class Account {
    private int accountId;
    private int userId;
    private long checking;
    private long savings;
    private User user;

    public Account() {
    }

    public Account(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }

    public Account(int accountId, int userId, long checking, long savings) {
        this.accountId = accountId;
        this.userId = userId;
        this.checking = checking;
        this.savings = savings;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getChecking() {
        return checking;
    }

    public void setChecking(long checking) {
        this.checking = checking;
    }

    public long getSavings() {
        return savings;
    }

    public void setSavings(long savings) {
        this.savings = savings;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //end class
}
