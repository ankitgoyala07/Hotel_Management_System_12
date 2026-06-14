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
                   + "phone_number INT, "
                   + "email_address VARCHAR(255), "
                   + "room_no INT, "
                   + "guest_no INT, "
                   + "room_type VARCHAR(255), "
                   + "check_in_date DATE, "
                   + "check_out_date DATE"
                   + ")";
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
            System.out.println("Table 'guest_details' verified/created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating guest_details table: " + e.getMessage());
        }
    }

    // Insert guest booking details
    public boolean insertGuest(GuestDetails guest) {
        String sql = "INSERT INTO guest_details (full_name, phone_number, email_address, room_no, guest_no, room_type, check_in_date, check_out_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, guest.getFULL_NAME());
            ps.setInt(2, guest.getPHONE_NUMBER());
            ps.setString(3, guest.getEMAIL_ADDRESS());
            ps.setInt(4, guest.getROOM_NO());
            ps.setInt(5, guest.getGUEST_NO());
            ps.setString(6, guest.getRoom_Type());
            ps.setDate(7, guest.getCHECK_IN_DATE());
            ps.setDate(8, guest.getCHECK_OUT_DATE());
            
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

    // Update guest details
    public boolean updateGuest(GuestDetails guest) {
        String sql = "UPDATE guest_details SET full_name=?, phone_number=?, email_address=?, room_no=?, guest_no=?, room_type=?, check_in_date=?, check_out_date=? WHERE guest_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, guest.getFULL_NAME());
            ps.setInt(2, guest.getPHONE_NUMBER());
            ps.setString(3, guest.getEMAIL_ADDRESS());
            ps.setInt(4, guest.getROOM_NO());
            ps.setInt(5, guest.getGUEST_NO());
            ps.setString(6, guest.getRoom_Type());
            ps.setDate(7, guest.getCHECK_IN_DATE());
            ps.setDate(8, guest.getCHECK_OUT_DATE());
            ps.setInt(9, guest.getId());
            
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
                    rs.getInt("phone_number"),
                    rs.getString("email_address"),
                    rs.getInt("room_no"),
                    rs.getInt("guest_no"),
                    rs.getString("room_type"),
                    rs.getDate("check_in_date"),
                    rs.getDate("check_out_date")
                );
                guests.add(guest);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching guest details list: " + e.getMessage());
        }
        return guests;
    }
}
