package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.GuestDetails;

/**
 * DAO class for Guest Details operations.
 * Handles database operations for guest bookings.
 */
public class GuestDetailsDao {
    private Connection conn;

    // Constructor with connection
    public GuestDetailsDao(Connection conn) {
        this.conn = conn;
    }

    // DDL: Create table if not present in the database
    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS guest_details ("
                   + "guest_id INT AUTO_INCREMENT PRIMARY KEY, "
                   + "full_name VARCHAR(255) NOT NULL, "
                   + "phone_number VARCHAR(50), "
                   + "email_address VARCHAR(255), "
                   + "home_address VARCHAR(255), "
                   + "room_no INT, "
                   + "guest_no INT, "
                   + "room_type VARCHAR(255), "
                   + "check_in_date DATE, "
                   + "check_out_date DATE, "
                   + "discount_deal VARCHAR(50), "
                   + "status VARCHAR(50) DEFAULT 'Checked In'"
                   + ")";
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
            System.out.println("Table 'guest_details' verified/created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating guest_details table: " + e.getMessage());
        }
    }

    // Find the first available room number matching a room type
    public int findAvailableRoomNo(String roomType) {
        // Normalize room type format from JComboBox or previous screens
        String queryType = roomType;
        if (roomType.equalsIgnoreCase("Single") || roomType.equalsIgnoreCase("Single bed") || roomType.equalsIgnoreCase("Single Bed Room")) {
            queryType = "Single";
        } else if (roomType.equalsIgnoreCase("Double") || roomType.equalsIgnoreCase("Double bed") || roomType.equalsIgnoreCase("Double Bed Room")) {
            queryType = "Double";
        } else if (roomType.equalsIgnoreCase("VIP")) {
            queryType = "VIP";
        }

        String sql = "SELECT room_number FROM rooms WHERE room_type = ? AND status = 'Available' LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, queryType);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String roomNumStr = rs.getString("room_number");
                    return Integer.parseInt(roomNumStr.replace("#", "").trim());
                }
            }
        } catch (Exception e) {
            System.out.println("Error finding available room: " + e.getMessage());
        }
        return -1;
    }

    // Update room status in the rooms table
    public boolean updateRoomStatus(int roomNo, String status) {
        String sql = "UPDATE rooms SET status = ? WHERE room_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, String.format("%03d", roomNo)); // Format e.g. 1 -> "001", 101 -> "101"
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            // Try updating with room number string directly (without formatting) if formatted fails
            try {
                String sql2 = "UPDATE rooms SET status = ? WHERE room_number = ?";
                try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
                    ps2.setString(1, status);
                    ps2.setString(2, String.valueOf(roomNo));
                    return ps2.executeUpdate() > 0;
                }
            } catch (Exception ex) {
                System.out.println("Error updating room status: " + ex.getMessage());
            }
        }
        return false;
    }

    // Insert guest booking details
    public boolean insertGuest(GuestDetails guest) {
        String sql = "INSERT INTO guest_details (full_name, phone_number, email_address, home_address, room_no, guest_no, room_type, check_in_date, check_out_date, discount_deal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, guest.getFULL_NAME());
            ps.setString(2, guest.getPHONE_NUMBER());
            ps.setString(3, guest.getEMAIL_ADDRESS());
            ps.setString(4, guest.getHomeAddress());
            ps.setInt(5, guest.getROOM_NO());
            ps.setInt(6, guest.getGUEST_NO());
            ps.setString(7, guest.getRoom_Type());
            ps.setDate(8, guest.getCHECK_IN_DATE());
            ps.setDate(9, guest.getCHECK_OUT_DATE());
            ps.setString(10, guest.getDiscountDeal());
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        guest.setId(generatedKeys.getInt(1));
                    }
                }
                
                // Also insert into bookings table using guest_id (foreign key reference to guest_details)
                try {
                    String insertBookingSql = "INSERT INTO bookings (guest_id, room_number, check_in_date, check_out_date, status) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement psB = conn.prepareStatement(insertBookingSql)) {
                        psB.setInt(1, guest.getId());
                        psB.setString(2, String.valueOf(guest.getROOM_NO()));
                        psB.setDate(3, guest.getCHECK_IN_DATE());
                        psB.setDate(4, guest.getCHECK_OUT_DATE());
                        psB.setString(5, "CheckedIn");
                        psB.executeUpdate();
                    }
                } catch (SQLException ex) {
                    System.out.println("Error inserting bookings record: " + ex.getMessage());
                }
                
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error inserting guest: " + e.getMessage());
            return false;
        }
    }

    // Update guest details
    public boolean updateGuest(GuestDetails guest) {
        String sql = "UPDATE guest_details SET full_name=?, phone_number=?, email_address=?, home_address=?, room_no=?, guest_no=?, room_type=?, check_in_date=?, check_out_date=?, discount_deal=? WHERE guest_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, guest.getFULL_NAME());
            ps.setString(2, guest.getPHONE_NUMBER());
            ps.setString(3, guest.getEMAIL_ADDRESS());
            ps.setString(4, guest.getHomeAddress());
            ps.setInt(5, guest.getROOM_NO());
            ps.setInt(6, guest.getGUEST_NO());
            ps.setString(7, guest.getRoom_Type());
            ps.setDate(8, guest.getCHECK_IN_DATE());
            ps.setDate(9, guest.getCHECK_OUT_DATE());
            ps.setString(10, guest.getDiscountDeal());
            ps.setInt(11, guest.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating guest: " + e.getMessage());
            return false;
        }
    }

    // Delete a guest details record by ID
    public boolean deleteGuest(int id) {
        String sql = "DELETE FROM guest_details WHERE guest_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting guest: " + e.getMessage());
            return false;
        }
    }

    // Retrieve all guest bookings
    public List<GuestDetails> getAllGuests() {
        List<GuestDetails> guests = new ArrayList<>();
        String sql = "SELECT * FROM guest_details";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                GuestDetails guest = new GuestDetails(
                    rs.getInt("guest_id"),
                    rs.getString("full_name"),
                    rs.getString("phone_number"),
                    rs.getString("email_address"),
                    rs.getString("home_address"),
                    rs.getInt("room_no"),
                    rs.getInt("guest_no"),
                    rs.getString("room_type"),
                    rs.getDate("check_in_date"),
                    rs.getDate("check_out_date"),
                    rs.getString("discount_deal")
                );
                guests.add(guest);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching guest details list: " + e.getMessage());
        }
        return guests;
    }
}