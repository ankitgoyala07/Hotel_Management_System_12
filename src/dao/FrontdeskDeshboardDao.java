package dao;

import database.MySqlConnection;
import model.FrontdeskDeshboardModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for handling dashboard-related room operations.
 *
 * @author i3
 */
public class FrontdeskDeshboardDao {
    private final MySqlConnection mysql = new MySqlConnection();

    /**
     * Retrieves all room records from the database.
     *
     * @return List of FrontdeskDeshboardModel
     */
    public List<FrontdeskDeshboardModel> getAllRooms() {
        List<FrontdeskDeshboardModel> list = new ArrayList<>();
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            System.out.println("Connection failed.");
            return list;
        }

        String sql = "SELECT room_number, room_type, status FROM rooms ORDER BY room_number";
        try (PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                FrontdeskDeshboardModel room = new FrontdeskDeshboardModel(
                    rs.getString("room_number"),
                    rs.getString("room_type"),
                    rs.getString("status")
                );
                list.add(room);
            }
        } catch (Exception e) {
            System.out.println("Error loading rooms: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return list;
    }

    /**
     * Updates the status of a specific room in the database.
     *
     * @param roomNumber the identifier of the room
     * @param status the new status ('Available' or 'Occupied')
     * @return true if successful, false otherwise
     */
    public boolean updateRoomStatus(String roomNumber, String status) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return false;
        }

        String sql = "UPDATE rooms SET status = ? WHERE room_number = ?";
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setString(1, status);
                pstm.setString(2, roomNumber);
                pstm.executeUpdate();
            }

            // If checking out, update guest_details and bookings status
            if (status.equalsIgnoreCase("Available")) {
                int roomNoInt = -1;
                try {
                    roomNoInt = Integer.parseInt(roomNumber.replace("#", "").trim());
                } catch (NumberFormatException e) {
                    // ignore
                }
                
                // Get the guest_id of the guest currently checked in this room
                int checkedInGuestId = -1;
                String sqlGetCheckedIn = "SELECT guest_id FROM guest_details WHERE room_no = ? AND status = 'Checked In'";
                try (PreparedStatement pstmGet = conn.prepareStatement(sqlGetCheckedIn)) {
                    pstmGet.setInt(1, roomNoInt);
                    try (ResultSet rs = pstmGet.executeQuery()) {
                        if (rs.next()) {
                            checkedInGuestId = rs.getInt("guest_id");
                        }
                    }
                }

                String sql2 = "UPDATE guest_details SET status = 'Checked Out' WHERE room_no = ? AND status = 'Checked In'";
                try (PreparedStatement pstm2 = conn.prepareStatement(sql2)) {
                    pstm2.setInt(1, roomNoInt);
                    pstm2.executeUpdate();
                }

                // If a checked-in guest was found, also update their booking record status in bookings table
                if (checkedInGuestId != -1) {
                    String sql3 = "UPDATE bookings SET status = 'CheckedOut' WHERE guest_id = ? AND status = 'CheckedIn'";
                    try (PreparedStatement pstm3 = conn.prepareStatement(sql3)) {
                        pstm3.setInt(1, checkedInGuestId);
                        pstm3.executeUpdate();
                    }
                }

                // Delete food orders for this room
                if (roomNoInt != -1) {
                    String deleteFoodSql = "DELETE FROM food_orders WHERE room_no = ?";
                    try (PreparedStatement pstm = conn.prepareStatement(deleteFoodSql)) {
                        pstm.setInt(1, roomNoInt);
                        pstm.executeUpdate();
                    }
                }

                // Delete room service requests for this room
                if (roomNoInt != -1) {
                    String deleteServiceSql = "DELETE FROM room_service WHERE room_no = ?";
                    try (PreparedStatement pstm = conn.prepareStatement(deleteServiceSql)) {
                        pstm.setInt(1, roomNoInt);
                        pstm.executeUpdate();
                    }
                }
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                // ignore
            }
            System.out.println("Error updating room status: " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (Exception ex) {
                // ignore
            }
            mysql.closeConnection(conn);
        }
        return false;
    }

    /**
     * Inserts a default walk-in guest when a room is booked directly from the dashboard.
     */
    public boolean insertDefaultGuestForRoom(String roomNumber, String roomType) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return false;
        }
        
        String sql = "INSERT INTO guest_details (full_name, phone_number, email_address, home_address, room_no, guest_no, room_type, check_in_date, check_out_date, discount_deal, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            int roomNo = -1;
            try {
                roomNo = Integer.parseInt(roomNumber.replace("#", "").trim());
            } catch (NumberFormatException e) {
                // ignore
            }
            
            long now = System.currentTimeMillis();
            java.sql.Date today = new java.sql.Date(now);
            java.sql.Date tomorrow = new java.sql.Date(now + (24 * 60 * 60 * 1000)); // default to 1 day stay
            
            ps.setString(1, "Walk-in Guest (" + roomNumber + ")");
            ps.setString(2, "N/A");
            ps.setString(3, "walkin@hms.com");
            ps.setString(4, "Walk-in");
            ps.setInt(5, roomNo);
            ps.setInt(6, 1);
            ps.setString(7, roomType);
            ps.setDate(8, today);
            ps.setDate(9, tomorrow);
            ps.setString(10, "None");
            ps.setString(11, "Checked In");
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                int guestId = -1;
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        guestId = generatedKeys.getInt(1);
                    }
                }
                
                // Also insert into bookings table using guest_id (foreign key reference to guest_details)
                if (guestId != -1) {
                    try {
                        String insertBookingSql = "INSERT INTO bookings (guest_id, room_number, check_in_date, check_out_date, status) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement psB = conn.prepareStatement(insertBookingSql)) {
                            psB.setInt(1, guestId);
                            psB.setString(2, roomNumber.replace("#", "").trim());
                            psB.setDate(3, today);
                            psB.setDate(4, tomorrow);
                            psB.setString(5, "CheckedIn");
                            psB.executeUpdate();
                        }
                    } catch (java.sql.SQLException ex) {
                        System.out.println("Error inserting bookings record in walk-in: " + ex.getMessage());
                    }
                }
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error inserting default guest: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return false;
    }

    /**
     * Retrieves the count of rooms based on status.
     *
     * @param status the status to search for
     * @return number of rooms with that status
     */
    public int getRoomsCountByStatus(String status) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return 0;
        }

        String sql = "SELECT COUNT(*) FROM rooms WHERE status = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, status);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("Error getting rooms count by status: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return 0;
    }

    /**
     * Retrieves the total count of rooms.
     *
     * @return total number of rooms
     */
    public int getTotalRoomsCount() {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return 0;
        }

        String sql = "SELECT COUNT(*) FROM rooms";
        try (PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error getting total rooms count: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return 0;
    }
}
