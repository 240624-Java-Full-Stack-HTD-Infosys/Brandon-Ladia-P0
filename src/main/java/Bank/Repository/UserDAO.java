package Bank.Repository;

import Bank.Model.User;

import java.sql.*;

public class UserDAO {
    Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    //priorities
    //
    public User registerUser(User user) {
        String sql = "INSERT INTO users (firstName, lastName, username, password, email, phone, ssn) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try { //y
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPhone());
            ps.setString(7, user.getSsn());
            ps.executeUpdate();
            //get generated keys
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generatedUserId = pkeyResultSet.getInt(1);
                //returns the user object
                return new User(generatedUserId, user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone(), user.getSsn());
            }
            //catch below
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //catch above
        //if no key found, return null
        return null;
    }

    public User processLogin(String username, String password) {
        String sql = "SELECT user_id, username, password FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    } //changed from void to user

    public void changeUserInfo(User user) {
        String sql = "UPDATE users SET firstName = ?, lastName = ?, username = ?, password = ?, email = ?, phone = ?, ssn = ? WHERE user_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPhone());
            ps.setString(7, user.getSsn());
            ps.setInt(8, user.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //almost done
    }

    //end of class
}
