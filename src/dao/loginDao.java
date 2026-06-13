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
     * Validates a user's credentials against the database and sets their role.
     *
     * @param user the loginModel containing credentials
     * @return true if credentials are valid, false otherwise
     */
    public boolean validateUser(loginModel user) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            System.out.println("Connection failed.");
            return false;
        }

        String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getPassword());
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    user.setRole(rs.getString("role"));
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error validating user: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return false;
    }
}
