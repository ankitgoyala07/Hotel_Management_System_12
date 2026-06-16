package dao;

import database.MySqlConnection;
import model.StaffManagementModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for handling staff-related database operations.
 *
 * @author i3
 */
public class StaffManagementDao {
    private final MySqlConnection mysql = new MySqlConnection();

    /**
     * Retrieves all staff records from the database.
     *
     * @return list of StaffManagementModel objects
     */
    public List<StaffManagementModel> getAllStaff() {
        List<StaffManagementModel> list = new ArrayList<>();
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            System.out.println("Connection failed.");
            return list;
        }

        String sql = "SELECT * FROM staff";
        try (PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                StaffManagementModel staff = new StaffManagementModel();
                staff.setStaffId(rs.getString("staff_id"));
                staff.setName(rs.getString("name"));
                staff.setPhone(rs.getString("phone"));
                staff.setEmail(rs.getString("email"));
                staff.setAddress(rs.getString("address"));
                staff.setRole(rs.getString("role"));
                staff.setShift(rs.getString("shift"));
                list.add(staff);
            }
        } catch (Exception e) {
            System.out.println("Error loading staff: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return list;
    }

    /**
     * Deletes a staff record from the database by staff ID.
     *
     * @param staffId the unique identifier of the staff member
     * @return true if deletion is successful, false otherwise
     */
    public boolean deleteStaff(String staffId) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            System.out.println("Connection failed.");
            return false;
        }

        String sql = "DELETE FROM staff WHERE staff_id = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, staffId);
            int rows = pstm.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("Error deleting staff: " + e.getMessage());
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }

    /**
     * Inserts a new staff record into the database.
     *
     * @param staff the StaffManagementModel object to insert
     * @return true if insertion is successful, false otherwise
     */
    public boolean insertStaff(StaffManagementModel staff) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            System.out.println("Connection failed.");
            return false;
        }

        String sql = "INSERT INTO staff (staff_id, name, phone, email, address, role, shift) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, staff.getStaffId());
            pstm.setString(2, staff.getName());
            pstm.setString(3, staff.getPhone());
            pstm.setString(4, staff.getEmail());
            pstm.setString(5, staff.getAddress());
            pstm.setString(6, staff.getRole());
            pstm.setString(7, staff.getShift());
            int rows = pstm.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("Error inserting staff: " + e.getMessage());
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }

    /**
     * Checks if a staff ID already exists in the database.
     *
     * @param staffId the unique identifier to check
     * @return true if exists, false otherwise
     */
    public boolean staffExists(String staffId) {
        Connection conn = mysql.Openconnection();
        if (conn == null) return false;

        String sql = "SELECT staff_id FROM staff WHERE staff_id = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, staffId);
            try (ResultSet rs = pstm.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            System.out.println("Error checking staff existence: " + e.getMessage());
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }
}
