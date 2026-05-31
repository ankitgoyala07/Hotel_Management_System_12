package dao;

import database.MySqlConnection;
import model.loginModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Data Access Object for handling login-related database operations.
 *
 * @author i3
 */
public class loginDao {
    private final MySqlConnection mysql = new MySqlConnection();

    /**
     * Authenticates a user with username and password.
     *
     * @param username the input username
     * @param password the input password
     * @return a loginModel if authentication is successful, null otherwise
     */
    public loginModel authenticateUser(String username, String password) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            System.out.println("Connection failed.");
            return null;
        }

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, username);
            pstm.setString(2, password);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    loginModel user = new loginModel();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }

        } catch (Exception e) {
            System.out.println("Error authenticating: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return null;
    }
}
