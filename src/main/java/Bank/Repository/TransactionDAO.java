package Bank.Repository;

import Bank.Model.Transaction;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    Connection conn;

    public TransactionDAO(Connection conn) throws SQLException, IOException, ClassNotFoundException {
        this.conn = conn;
    }

    //insert into then update
    public void depositMoney(Transaction transaction) {
        //conn = ConnectionUtil.getConnection();
        String sql = "INSERT INTO bankingTransactions (account_id, amount, accountType, transactionType) VALUES (?, ?, ?, ?)";
        String sqlUpdate;

        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, transaction.getAccountId());
            ps.setLong(2, transaction.getAmount());
            ps.setString(3, transaction.getAccountType());
            ps.setString(4, "deposit");
            ps.executeUpdate();
            //get return keys?
            //update the value in the account table
            if(transaction.getAccountType().equalsIgnoreCase("checking")){
                //write code
                sqlUpdate = "UPDATE account SET checking = checking + ? WHERE account_id = ?";
            } else if(transaction.getAccountType().equalsIgnoreCase("savings")) {
                sqlUpdate = "UPDATE account SET savings = savings + ? WHERE account_id = ?";
            } else {
                throw new IllegalArgumentException("Invalid account");
            }
            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate, Statement.RETURN_GENERATED_KEYS);
            psUpdate.setLong(1, transaction.getAmount());
            psUpdate.setInt(2, transaction.getAccountId());
            psUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //insert into then update
    public void withdrawMoney(Transaction transaction) {
        String sql = "INSERT INTO bankingTransactions (account_id, amount, accountType, transactionType) VALUES (?, ?, ?, ?)";
        String sqlUpdate;

        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, transaction.getAccountId());
            ps.setLong(2, transaction.getAmount());
            ps.setString(3, transaction.getAccountType());
            ps.setString(4, "withdraw");
            ps.executeUpdate();
            //get return keys?
            //update value in account table
            if(transaction.getAccountType().equals("checking")){
                //write code
                sqlUpdate = "UPDATE account SET checking = checking - ? WHERE account_id = ?";
            } else if(transaction.getAccountType().equals("savings")) {
                sqlUpdate = "UPDATE account SET savings = savings - ? WHERE account_id = ?";
            } else {
                throw new IllegalArgumentException("Invalid account type");
            }
            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate, Statement.RETURN_GENERATED_KEYS);
            psUpdate.setLong(1, transaction.getAmount());
            psUpdate.setInt(2, transaction.getAccountId());
            psUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //method ends
    }

    //insert into then update
    public void transferMoney(Transaction transaction) {
        //four sql statements
        String transferOut = "INSERT INTO bankingTransactions (account_id, amount, accountType, transactionType) VALUES (?, ?, ?, ?)";
        try {
        if(transaction.getAccountType().equalsIgnoreCase("checking")){ //transferring out of
            String updateOut = "UPDATE account SET checking = checking - ?  WHERE account_id = ?";
            String transferIn = "INSERT INTO bankingTransactions (account_id, amount, accountType, transactionType) VALUES (?, ?, ?, ?)";
            String updateIn = "UPDATE account SET savings = savings + ? WHERE account_id = ?";
            //transfer out
            PreparedStatement psOut = conn.prepareStatement(transferOut, Statement.RETURN_GENERATED_KEYS);
            psOut.setInt(1, transaction.getAccountId());
            psOut.setLong(2, transaction.getAmount());
            psOut.setString(3, transaction.getAccountType()); //maybe look at replacing this
            psOut.setString(4, "transfer out");
            psOut.executeUpdate();
            //update out
            PreparedStatement upOut = conn.prepareStatement(updateOut, Statement.RETURN_GENERATED_KEYS);
            upOut.setLong(1, transaction.getAmount());
            upOut.setInt(2, transaction.getAccountId());
            upOut.executeUpdate();
            //transfer in
            PreparedStatement psIn = conn.prepareStatement(transferIn, Statement.RETURN_GENERATED_KEYS);
            psIn.setInt(1, transaction.getAccountId());
            psIn.setLong(2, transaction.getAmount());
            psIn.setString(3, "savings"); //maybe look at replacing this
            psIn.setString(4, "transfer in");
            psIn.executeUpdate();
            //update in
            PreparedStatement upIn = conn.prepareStatement(updateIn, Statement.RETURN_GENERATED_KEYS);
            upIn.setLong(1, transaction.getAmount());
            upIn.setInt(2, transaction.getAccountId());
            upIn.executeUpdate();
        } else { //transferring into
            String updateOut = "UPDATE account SET savings = savings - ?  WHERE account_id = ?";
            String transferIn = "INSERT INTO bankingTransactions (account_id, amount, accountType, transactionType) VALUES (?, ?, ?, ?)";
            String updateIn = "UPDATE account SET checking = checking + ? WHERE account_id = ?";
            //transfer out
            PreparedStatement psOut = conn.prepareStatement(transferOut, Statement.RETURN_GENERATED_KEYS);
            psOut.setInt(1, transaction.getAccountId());
            psOut.setLong(2, transaction.getAmount());
            psOut.setString(3, transaction.getAccountType()); //maybe look at replacing this
            psOut.setString(4, "transfer out");
            psOut.executeUpdate();
            //update out
            PreparedStatement upOut = conn.prepareStatement(updateOut, Statement.RETURN_GENERATED_KEYS);
            upOut.setLong(1, transaction.getAmount());
            upOut.setInt(2, transaction.getAccountId());
            upOut.executeUpdate();
            //transfer in
            PreparedStatement psIn = conn.prepareStatement(transferIn, Statement.RETURN_GENERATED_KEYS);
            psIn.setInt(1, transaction.getAccountId());
            psIn.setLong(2, transaction.getAmount());
            psIn.setString(3, "checking"); //maybe look at replacing this
            psIn.setString(4, "transfer in");
            psIn.executeUpdate();
            //update in
            PreparedStatement upIn = conn.prepareStatement(updateIn, Statement.RETURN_GENERATED_KEYS);
            upIn.setLong(1, transaction.getAmount());
            upIn.setInt(2, transaction.getAccountId());
            upIn.executeUpdate();
        }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //
        //to show what's transferring from what account
    }

    //
    public List<Transaction> listTransactionsByAccountTypeAndId(Transaction transaction) {
        List<Transaction> transactionList = new ArrayList<>();
        String sql = "SELECT * FROM bankingTransactions WHERE LOWER(accountType) = LOWER(?) AND account_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, transaction.getAccountType());
            ps.setInt(2, transaction.getAccountId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Transaction newTransaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("account_id"),
                        rs.getLong("amount"),
                        rs.getString("accountType"),
                        rs.getString("transactionType")
                );
                transactionList.add(newTransaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return transactionList;
    }

    //end class
}
