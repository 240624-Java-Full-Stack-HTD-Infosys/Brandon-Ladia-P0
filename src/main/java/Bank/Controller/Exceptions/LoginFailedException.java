package Bank.Controller.Exceptions;

public class LoginFailedException extends Exception {
    public LoginFailedException(String message){
        super(message);
    }
}
