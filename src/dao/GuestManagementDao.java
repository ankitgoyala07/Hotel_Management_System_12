package dao;

import database.MySqlConnection;
import model.GuestManagementModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for guest management screen database operations.
 */
public class GuestManagementDao {
    private final MySqlConnection mysql = new MySqlConnection();

    public List<GuestManagementModel> getAllGuests(String searchQuery) {
        List<GuestManagementModel> list = new ArrayList<>();
        Connection conn = mysql.Openconnection();
        if (conn == null) return list;

        String sql = "SELECT guest_id, full_name, room_no, status, check_in_date, check_out_date FROM guest_details";
        boolean hasFilter = searchQuery != null && !searchQuery.trim().isEmpty();
        if (hasFilter) {
            sql += " WHERE full_name LIKE ? OR CAST(room_no AS CHAR) LIKE ?";
        }
        sql += " ORDER BY guest_id DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (hasFilter) {
                String filter = "%" + searchQuery.trim() + "%";
                ps.setString(1, filter);
                ps.setString(2, filter);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new GuestManagementModel(
                        rs.getInt("guest_id"),
                        rs.getString("full_name"),
                        rs.getString("room_no") != null ? rs.getString("room_no") : "N/A",
                        rs.getString("status") != null ? rs.getString("status") : "Checked In",
                        rs.getDate("check_in_date"),
                        rs.getDate("check_out_date")
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching guest list: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return list;
    }

    public int getTotalGuestsCount() {
        Connection conn = mysql.Openconnection();
        if (conn == null) return 0;
        String sql = "SELECT COUNT(*) FROM guest_details";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error getting total guests count: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return 0;
    }

    public int getCheckinTodayCount() {
        Connection conn = mysql.Openconnection();
        if (conn == null) return 0;
        String sql = "SELECT COUNT(*) FROM guest_details WHERE check_in_date = CURRENT_DATE()";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error getting check-in count: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return 0;
    }

    public int getCheckoutTodayCount() {
        Connection conn = mysql.Openconnection();
        if (conn == null) return 0;
        String sql = "SELECT COUNT(*) FROM guest_details WHERE check_out_date = CURRENT_DATE()";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error getting check-out count: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return 0;
    }

    public boolean deleteGuest(int guestId) {
        Connection conn = mysql.Openconnection();
        if (conn == null) return false;
        try {
            conn.setAutoCommit(false);
            
            // 1. Get room number for this guest
            String roomNo = null;
            String sqlGetRoom = "SELECT room_no FROM guest_details WHERE guest_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlGetRoom)) {
                ps.setInt(1, guestId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        roomNo = rs.getString("room_no");
                    }
                }
            }

            // 2. Delete guest record
            String sqlDel = "DELETE FROM guest_details WHERE guest_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlDel)) {
                ps.setInt(1, guestId);
                ps.executeUpdate();
            }

            // 3. Mark the room as Available
            if (roomNo != null) {
                String sqlRoom = "UPDATE rooms SET status = 'Available' WHERE room_number = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlRoom)) {
                    ps.setString(1, roomNo);
                    ps.executeUpdate();
                }
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {}
            System.out.println("Error deleting guest: " + e.getMessage());
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception ex) {}
            mysql.closeConnection(conn);
        }
        return false;
    }
}