package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class GuestDashboardDao {
    private Connection conn;

    // Constructor to initialize database connection
    public GuestDashboardDao(Connection conn) {
        this.conn = conn;
    }

    // Add new user (e.g., guest registration)
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (name, email, roomType, checkIn, checkOut) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getRoomType());
            ps.setDate(4, user.getCheckIn());
            ps.setDate(5, user.getCheckOut());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    // Retrieve all users (for dashboard display)
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("roomType"),
                    rs.getDate("checkIn"),
                    rs.getDate("checkOut")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }
        return users;
    }

    // Update user details (e.g., room change or checkout)
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET roomType=?, checkOut=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getRoomType());
            ps.setDate(2, user.getCheckOut());
            ps.setInt(3, user.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    // Delete user (after checkout)
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }
}
