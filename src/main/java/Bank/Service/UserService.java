package Bank.Service;

import Bank.Model.User;
import Bank.Repository.UserDAO;

public class UserService {
    UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    //work on
    public User registerUser(User user) {
        if(user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword().length() < 4){
            return null;
        } else {
            return userDAO.registerUser(user);
        }
    }

    //work on
    public User processLogin(String username, String password) {
        User login = userDAO.processLogin(username, password);
        if(login != null){
            return login;
        } else {
            System.out.println("Login failed");
            return null;
        }
    }

    //work on
    public void changeUserInfo(User user){
        if(user == null){
            System.out.println("User not found");
        } else {
            userDAO.changeUserInfo(user);
        }
    }

    //end class
}
