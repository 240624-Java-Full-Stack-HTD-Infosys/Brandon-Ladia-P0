package Bank.Controller;

import Bank.Controller.Exceptions.AccountNotZeroedException;
import Bank.Controller.Exceptions.UserNotFoundException;
import Bank.Model.Account;
import Bank.Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class AccountController {
    AccountService accountService;
    Javalin app;

    public AccountController(AccountService accountService, Javalin app) {
        this.accountService = accountService;
        this.app = app;

        app.post("/account", this::postAccount);
        app.delete("/account/{accountId}", this::deleteAccount);
        app.get("/account/{accountId}", this::viewAccountBalance);

    }

    //create account
    public void postAccount(Context ctx) throws UserNotFoundException {
        Account newAccount = accountService.createAccount(ctx.bodyAsClass(Account.class));
        ctx.json(newAccount);
    }

    //delete account by accountId
    //can only delete if transactions are zeroed out
    public void deleteAccount(Context ctx) throws AccountNotZeroedException {
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));
        Account deleteAccount = new Account();
        deleteAccount.setAccountId(accountId);
        accountService.deleteAccountByID(deleteAccount);
        ctx.json(200).result("Account successfully deleted");
    }

    //view account balance by accountId
    public void viewAccountBalance(Context ctx){
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));
        Account viewAccount = accountService.viewAccountBalance(new Account(accountId));
        if(viewAccount != null){
            ctx.json(viewAccount);
            ctx.status(202);
        } else {
            ctx.json(404).result("account not found");
        }
    }

}
