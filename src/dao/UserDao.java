package dao;

import database.MySqlConnection;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) for User database operations.
 */
public class UserDao {
    private final MySqlConnection dbConnection;

    public UserDao() {
        this.dbConnection = new MySqlConnection();
        ensureTableExists();
    }

    /**
     * Checks if the 'users' table exists and creates it if it does not.
     */
    private void ensureTableExists() {
        Connection conn = dbConnection.Openconnection();
        if (conn == null) {
            System.err.println("UserDao: Could not establish database connection to ensure users table exists.");
            return;
        }

        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "fullname VARCHAR(100) NOT NULL, " +
                     "email VARCHAR(100) NOT NULL UNIQUE, " +
                     "phone VARCHAR(20) NOT NULL, " +
                     "password VARCHAR(100) NOT NULL, " +
                     "role VARCHAR(50) NOT NULL" +
                     ")";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            System.out.println("UserDao: Users table verified/created successfully.");
        } catch (SQLException e) {
            System.err.println("UserDao: Error verifying or creating users table: " + e.getMessage());
        } finally {
            dbConnection.closeConnection(conn);
        }
    }

    /**
     * Saves a user record to the database.
     *
     * @param user the User to save
     * @return true if save was successful, false otherwise
     */
    public boolean saveUser(User user) {
        Connection conn = dbConnection.Openconnection();
        if (conn == null) {
            System.err.println("UserDao: Failed to open connection to save user.");
            return false;
        }

        String sql = "INSERT INTO users (fullname, email, phone, password, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("UserDao: Error inserting user into database: " + e.getMessage());
            return false;
        } finally {
            dbConnection.closeConnection(conn);
        }
    }

    /**
     * Checks if a user already exists with the given email.
     *
     * @param email the email to check
     * @return true if the email exists, false otherwise
     */
    public boolean isEmailExists(String email) {
        Connection conn = dbConnection.Openconnection();
        if (conn == null) {
            System.err.println("UserDao: Failed to open connection to check email.");
            return false;
        }

        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("UserDao: Error checking email existence: " + e.getMessage());
        } finally {
            dbConnection.closeConnection(conn);
        }
        return false;
    }
}
