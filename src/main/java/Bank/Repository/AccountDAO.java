package Bank.Repository;

import Bank.Model.Account;

import java.io.IOException;
import java.sql.*;

//THESE ARE BANK ACCOUNTS (CHECKING/SAVINGS)
public class AccountDAO {
    Connection conn;

    public AccountDAO(Connection conn) throws SQLException, IOException, ClassNotFoundException {
        this.conn = conn;
    }

    //work on
    public Account createAccount(Account account) {
        String sql = "INSERT INTO account (user_id, checking, savings) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, account.getUserId());
            ps.setLong(2, account.getChecking());
            ps.setLong(3, account.getSavings());
            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generatedAccountId = pkeyResultSet.getInt(1);
                return new Account(generatedAccountId, account.getUserId(), account.getChecking(), account.getSavings());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    //high priority
    //priorities
    //work on
    //in the service layer, find out if there is money present. if there is then withrdaw first
    public void deleteAccountByID(int id)  {
        String sql = "DELETE FROM bankingTransactions WHERE account_id = ?";
        String finalDelete = "DELETE FROM account WHERE account_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            //also have to drop the rows in the transaction table FIRST
            PreparedStatement buhBye = conn.prepareStatement(finalDelete);
            buhBye.setInt(1, id);
            buhBye.executeUpdate();
            //returns number of rows affected. return an int. can use that to check if a row exists?
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //view by accountId
    public Account viewAccountBalance(int id) {
        String sql = "SELECT account_id, user_id, checking, savings FROM account WHERE account_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Account accountBalance = new Account(
                        rs.getInt("account_id"),
                        rs.getInt("user_id"),
                        rs.getLong("checking"),
                        rs.getLong("savings")
                );
                return accountBalance;
                //returning the object with all those columns
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

//end of class
}
