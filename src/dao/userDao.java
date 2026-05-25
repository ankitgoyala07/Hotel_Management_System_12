package dao;

import database.MySqlConnection;
import model.userModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author i3
 */
public class userDao {
    private final MySqlConnection mysql = new MySqlConnection();
    
    /**
     * Creates a new user in the database
     * @param user the user model containing username, email, and password
     * @return true if creation succeeded, false otherwise
     */
    public boolean createUser(userModel user){
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            System.out.println("Database connection failed during user creation.");
            return false;
        }
        
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, user.getName());
            pstm.setString(2, user.getEmail());
            pstm.setString(3, user.getPassword());
            
            int rowsAffected = pstm.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }

    /**
     * Authenticates a user with username and password
     * @param username the username
     * @param password the password
     * @return the authenticated userModel if successful, null otherwise
     */
    public userModel authenticateUser(String username, String password) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            System.out.println("Database connection failed during authentication.");
            return null;
        }

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, username);
            pstm.setString(2, password);
            
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    userModel user = new userModel();
                    user.setUserid(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            }
        } catch (Exception e) {
            System.out.println("Error authenticating user: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return null;
    }
}
