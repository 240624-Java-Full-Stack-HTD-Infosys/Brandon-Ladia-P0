package Bank.Service;

import Bank.Controller.Exceptions.AccountNotZeroedException;
import Bank.Controller.Exceptions.UserNotFoundException;
import Bank.Model.Account;
import Bank.Repository.AccountDAO;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account createAccount(Account account) throws UserNotFoundException {
        if(account.getUserId() == 0){
            throw new UserNotFoundException("User not found");
        } else {
            return accountDAO.createAccount(account);
        }
    }

    //work on
    public void deleteAccountByID(Account account) throws AccountNotZeroedException {
        if(account.getChecking() > 0 || account.getSavings() > 0){
            throw new AccountNotZeroedException("Withdraw money to delete");
        } else {
            accountDAO.deleteAccountByID(account.getAccountId());
        }
        //wanted to make code more robust and make sure it account was zeroed out to delete first
        //kept logic as to not break code in case
    }

    //work on
    public Account viewAccountBalance(Account account){
        return accountDAO.viewAccountBalance(account.getAccountId());
    }

    //end of class
}
