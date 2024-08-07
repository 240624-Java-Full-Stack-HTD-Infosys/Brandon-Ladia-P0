package Bank.Controller;

import Bank.Model.Transaction;
import Bank.Service.TransactionService;
import io.javalin.Javalin;
import io.javalin.http.Context;

//Might not use this one. might have it be in the Account controller
public class TransactionController {
    TransactionService transactionService;
    Javalin app;

    public TransactionController(TransactionService transactionService, Javalin app) {
        this.transactionService = transactionService;
        this.app = app;

        app.post("/deposit", this::depositCurrency);    //done
        app.post("/withdraw", this::withdrawCurrency);  //done
        app.put("/transfer", this::transferCurrency);
        app.get("/list", this::listTransactions);       //done

    }

    //depositMoney
    public void depositCurrency(Context ctx){
        Transaction transaction = ctx.bodyAsClass(Transaction.class);
        transactionService.depositMoney(transaction);
        ctx.status(200).result("Deposit successful");
    }

    //withdrawMoney
    public void withdrawCurrency(Context ctx){
        Transaction transaction = ctx.bodyAsClass(Transaction.class);
        transactionService.withdrawMoney(transaction);
        ctx.status(200).result("withdrawal successful");
    }

    //transferMoney
    public void transferCurrency(Context ctx){
        Transaction transaction = ctx.bodyAsClass(Transaction.class);
        transactionService.transferMoney(transaction);
        ctx.status(200).result("transfer successful");
    }

    //list all transactions
    public void listTransactions(Context ctx){
        Transaction transaction = ctx.bodyAsClass(Transaction.class);
        ctx.json(transactionService.listTransactionsByAccountTypeAndId(transaction));
    }

}
