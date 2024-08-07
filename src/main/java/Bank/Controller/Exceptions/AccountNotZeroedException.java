package Bank.Controller.Exceptions;

public class AccountNotZeroedException extends Exception{
    public AccountNotZeroedException(String message){
        super(message);
    }
}
