package Bank.Service;

import Bank.Model.Transaction;
import Bank.Repository.TransactionDAO;

import java.util.List;

public class TransactionService {
    TransactionDAO transactionDAO;

    public TransactionService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    //
    public void depositMoney(Transaction transaction) {
        if(transaction.getAmount() < 0){
            throw new IllegalArgumentException("Cannot deposit negative amount");
        } else {
            transactionDAO.depositMoney(transaction);
        }
    }

    //
    public void withdrawMoney(Transaction transaction) {
        if(transaction.getAmount() < 0){
            throw new IllegalArgumentException("Cannot withdraw negative amount");
        } else {
            transactionDAO.withdrawMoney(transaction);
        }
    }

    //work on
    public void transferMoney(Transaction transaction){
        transactionDAO.transferMoney(transaction);
    }

    //last method
    public List<Transaction> listTransactionsByAccountTypeAndId(Transaction transaction) {
        List<Transaction> accountTransactions = transactionDAO.listTransactionsByAccountTypeAndId(transaction);
        return accountTransactions;
    }

}
