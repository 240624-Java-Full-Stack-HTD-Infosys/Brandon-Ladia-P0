package Bank.utils;

import Bank.Controller.AccountController;
import Bank.Controller.TransactionController;
import Bank.Controller.UserController;
import Bank.Repository.AccountDAO;
import Bank.Repository.TransactionDAO;
import Bank.Repository.UserDAO;
import Bank.Service.AccountService;
import Bank.Service.TransactionService;
import Bank.Service.UserService;
import io.javalin.Javalin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ServerUtil {
    private static ServerUtil serverUtil;

    private ServerUtil(){

    }

    public static ServerUtil getServerUtil(){
        if(serverUtil == null){
            serverUtil = new ServerUtil();
        }
        return serverUtil;
    }
    //work on
    //
    //
    public Javalin initialize() throws SQLException, IOException, ClassNotFoundException {
        Javalin app = Javalin.create().start();
        Connection conn = ConnectionUtil.getConnection();

        UserDAO userDAO = new UserDAO(conn);
        UserService userService = new UserService(userDAO);
        UserController userController = new UserController(userService, app);

        AccountDAO accountDAO = new AccountDAO(conn);
        AccountService accountService = new AccountService(accountDAO);
        AccountController accountController = new AccountController(accountService, app);

        TransactionDAO transactionDAO = new TransactionDAO(conn);
        TransactionService transactionService = new TransactionService(transactionDAO);
        TransactionController transactionController = new TransactionController(transactionService, app);

        return app;
    }

}
