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
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, status);
            pstm.setString(2, roomNumber);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error updating room status: " + e.getMessage());
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
