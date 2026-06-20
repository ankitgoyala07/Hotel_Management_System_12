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
        String sql = "CREATE TABLE IF NOT EXISTS guest_dashboard_data ("
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
            System.out.println("Table 'guest_dashboard_data' verified/created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    // Insert new guest
    public boolean insertGuest(GuestDashboardModel guest) {
        String sql = "INSERT INTO guest_dashboard_data (name, email, roomType, checkIn, checkOut, guestsCount, expenses) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
        String sql = "SELECT * FROM guest_dashboard_data";
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
        String sql = "SELECT * FROM guest_dashboard_data WHERE id = ?";
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
        String sql = "UPDATE guest_dashboard_data SET name=?, email=?, roomType=?, checkIn=?, checkOut=?, guestsCount=?, expenses=? WHERE id=?";
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
        String sql = "DELETE FROM guest_dashboard_data WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting guest: " + e.getMessage());
            return false;
        }
    }

    // Retrieve user email/phone from users table by username
    public String[] getUserDetails(String username) {
        String sql = "SELECT email, phone FROM users WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new String[]{rs.getString("email"), rs.getString("phone")};
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting user details: " + e.getMessage());
        }
        return null;
    }

    // Retrieve active booking from guest_details table by email or phone
    public GuestDashboardModel getActiveBooking(String email, String phone) {
        String sql = "SELECT * FROM guest_details WHERE (email_address = ? OR phone_number = ?) ORDER BY guest_id DESC LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, phone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("guest_id");
                    String name = rs.getString("full_name");
                    String userEmail = rs.getString("email_address");
                    String roomType = rs.getString("room_type");
                    int roomNo = rs.getInt("room_no");
                    Date checkIn = rs.getDate("check_in_date");
                    Date checkOut = rs.getDate("check_out_date");
                    int guestNo = rs.getInt("guest_no");
                    
                    double expenses = getCalculatedExpenses(roomNo);
                    
                    return new GuestDashboardModel(
                        id,
                        name,
                        userEmail,
                        roomType + " (Room " + roomNo + ")",
                        checkIn,
                        checkOut,
                        guestNo,
                        expenses
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting active booking: " + e.getMessage());
        }
        return null;
    }

    // Calculate total expenses dynamically based on food_orders and room_service
    public double getCalculatedExpenses(int roomNo) {
        double total = 0.0;
        
        // 1. Sum food orders
        String foodSql = "SELECT SUM(price * quantity) FROM food_orders WHERE room_no = ?";
        try (PreparedStatement ps = conn.prepareStatement(foodSql)) {
            ps.setInt(1, roomNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total += rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Note: Calculated food expenses check skipped or table not created: " + e.getMessage());
        }
        
        // 2. Sum room services
        String serviceSql = "SELECT service_type FROM room_service WHERE room_no = ?";
        try (PreparedStatement ps = conn.prepareStatement(serviceSql)) {
            ps.setInt(1, roomNo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String serviceType = rs.getString("service_type");
                    if (serviceType != null) {
                        serviceType = serviceType.trim();
                        if (serviceType.equalsIgnoreCase("Room Cleaning")) {
                            total += 5.00;
                        } else if (serviceType.equalsIgnoreCase("Extra Blanket")) {
                            total += 2.00;
                        } else if (serviceType.equalsIgnoreCase("Laundry")) {
                            total += 5.00;
                        } else if (serviceType.equalsIgnoreCase("Gym AND Jumba")) {
                            total += 10.00;
                        } else if (serviceType.equalsIgnoreCase("Infinity Pool")) {
                            total += 8.00;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Note: Calculated room service check skipped or table not created: " + e.getMessage());
        }
        
        return total;
    }
}
// git push