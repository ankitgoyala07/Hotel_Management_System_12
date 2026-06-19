package dao;

/**
 * Data Access Object (DAO) executing CRUD operations on the Room details table
 */

import database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.roommanagementModel;

public class roommanagementDAO {

    public List<roommanagementModel> getRooms() {
        List<roommanagementModel> roomsList = new ArrayList<>();
        MySqlConnection db = new MySqlConnection();
        Connection conn = db.Openconnection();

        if (conn == null) {
            System.out.println("Warning: Database connection failed.");
            return roomsList;
        }

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT room_number, room_type, room_floor, room_facilities, status, price_per_night FROM rooms ORDER BY room_number ASC");
            
            while (rs.next()) {
                roommanagementModel room = new roommanagementModel(
                    rs.getString("room_number"),
                    rs.getString("room_type"),
                    rs.getString("room_floor"),
                    rs.getString("room_facilities"),
                    rs.getString("status"),
                    rs.getDouble("price_per_night")
                );
                roomsList.add(room);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error querying rooms: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }

        return roomsList;
    }

    public boolean addRoom(roommanagementModel room) {
        MySqlConnection db = new MySqlConnection();
        Connection conn = db.Openconnection();
        if (conn == null) return false;

        String sql = "INSERT INTO rooms (room_number, room_type, room_floor, room_facilities, status, price_per_night) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, room.getRoomNumber().replace("#", "").trim());
            pstm.setString(2, room.getRoomType());
            pstm.setString(3, room.getRoomFloor());
            pstm.setString(4, room.getRoomFacility());
            pstm.setString(5, room.getStatus());
            pstm.setDouble(6, room.getPricePerNight());
            int result = pstm.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            System.out.println("Error adding room: " + e.getMessage());
            return false;
        } finally {
            db.closeConnection(conn);
        }
    }
}
