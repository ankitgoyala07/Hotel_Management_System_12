package dao;

import database.MySqlConnection;
import model.signupModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Data Access Object for handling signup-related database operations.
 *
 * @author i3
 */
public class signupDao {
    private final MySqlConnection mysql = new MySqlConnection();

    /**
     * Inserts a new user into the database using registration details.
     *
     * @param user the signupModel containing registration details
     * @return true if creation is successful, false otherwise
     */
    public boolean createUser(signupModel user) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            System.out.println("Connection failed.");
            return false;
        }

        String sql = "INSERT INTO users (username, email, phone, password, role, security_questions) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getEmail());
            pstm.setString(3, user.getPhone());
            pstm.setString(4, user.getPassword());
            pstm.setString(5, user.getRole());
            pstm.setString(6, user.getSecurityQuestion());
            int rows = pstm.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }

    /**
     * Checks if a username already exists.
     *
     * @param username the username to check
     * @return true if exists, false otherwise
     */
    public boolean usernameExists(String username) {
        Connection conn = mysql.Openconnection();
        if (conn == null) return false;

        String sql = "SELECT user_id FROM users WHERE username = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, username);
            try (ResultSet rs = pstm.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            System.out.println("Error checking username: " + e.getMessage());
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }

    /**
     * Checks if an email already exists.
     *
     * @param email the email to check
     * @return true if exists, false otherwise
     */
    public boolean emailExists(String email) {
        Connection conn = mysql.Openconnection();
        if (conn == null) return false;

        String sql = "SELECT user_id FROM users WHERE email = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, email);
            try (ResultSet rs = pstm.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            System.out.println("Error checking email: " + e.getMessage());
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }
}
