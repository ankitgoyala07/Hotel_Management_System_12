package dao;

import database.MySqlConnection;
import model.userModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class userDao {
    private final MySqlConnection mysql = new MySqlConnection();

    // ── SIGNUP ──────────────────────────────────────────────────────────────
    public boolean createUser(userModel user) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            System.out.println("Connection failed.");
            return false;
        }

        String sql = "INSERT INTO users (username, email, phone, password, role) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, user.getName());
            pstm.setString(2, user.getEmail());
            pstm.setString(3, user.getPhone());
            pstm.setString(4, user.getPassword());
            pstm.setString(5, user.getRole());
            int rows = pstm.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }

    // ── LOGIN ────────────────────────────────────────────────────────────────
    public userModel authenticateUser(String username, String password) {
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
                    userModel user = new userModel();
                    user.setUserid(rs.getInt("user_id"));
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

    // ── CHECK DUPLICATES ─────────────────────────────────────────────────────
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