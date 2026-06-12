package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.GuestDashboardModel;

public class GuestDashboardDao {
    private Connection conn;

    // Constructor to initialize database connection
    public GuestDashboardDao(Connection conn) {
        this.conn = conn;
    }

    // DDL: Create table if not present in the database
    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                   + "id INT AUTO_INCREMENT PRIMARY KEY, "
                   + "name VARCHAR(255), "
                   + "email VARCHAR(255), "
                   + "roomType VARCHAR(255), "
                   + "checkIn DATE, "
                   + "checkOut DATE, "
                   + "guestsCount INT DEFAULT 1, "
                   + "expenses DOUBLE DEFAULT 0.0"
                   + ")";
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
            System.out.println("Table 'users' verified/created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    // Insert new guest
    public boolean insertGuest(GuestDashboardModel guest) {
        String sql = "INSERT INTO users (name, email, roomType, checkIn, checkOut, guestsCount, expenses) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, guest.getName());
            ps.setString(2, guest.getEmail());
            ps.setString(3, guest.getRoomType());
            ps.setDate(4, guest.getCheckIn());
            ps.setDate(5, guest.getCheckOut());
            ps.setInt(6, guest.getGuestsCount());
            ps.setDouble(7, guest.getExpenses());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        guest.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error inserting guest: " + e.getMessage());
            return false;
        }
    }

    // Retrieve all guests
    public List<GuestDashboardModel> getAllGuests() {
        List<GuestDashboardModel> guests = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                GuestDashboardModel guest = new GuestDashboardModel(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("roomType"),
                    rs.getDate("checkIn"),
                    rs.getDate("checkOut"),
                    rs.getInt("guestsCount"),
                    rs.getDouble("expenses")
                );
                guests.add(guest);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching guests: " + e.getMessage());
        }
        return guests;
    }

    // Retrieve a single guest by ID
    public GuestDashboardModel getGuestById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new GuestDashboardModel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("roomType"),
                        rs.getDate("checkIn"),
                        rs.getDate("checkOut"),
                        rs.getInt("guestsCount"),
                        rs.getDouble("expenses")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching guest by id: " + e.getMessage());
        }
        return null;
    }

    // Update guest details
    public boolean updateGuest(GuestDashboardModel guest) {
        String sql = "UPDATE users SET name=?, email=?, roomType=?, checkIn=?, checkOut=?, guestsCount=?, expenses=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, guest.getName());
            ps.setString(2, guest.getEmail());
            ps.setString(3, guest.getRoomType());
            ps.setDate(4, guest.getCheckIn());
            ps.setDate(5, guest.getCheckOut());
            ps.setInt(6, guest.getGuestsCount());
            ps.setDouble(7, guest.getExpenses());
            ps.setInt(8, guest.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating guest: " + e.getMessage());
            return false;
        }
    }

    // Delete guest
    public boolean deleteGuest(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting guest: " + e.getMessage());
            return false;
        }
    }
}
