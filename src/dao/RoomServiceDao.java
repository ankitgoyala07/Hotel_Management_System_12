package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.RoomServiceModel;

/**
 * DAO class for Room Service operations.
 * Handles database operations for room service requests.
 */
public class RoomServiceDao {
    private Connection conn;

    // Constructor with connection
    public RoomServiceDao(Connection conn) {
        this.conn = conn;
    }

    // DDL: Create table if not present in the database
    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS room_service ("
                   + "id INT AUTO_INCREMENT PRIMARY KEY, "
                   + "service_type VARCHAR(255) NOT NULL, "
                   + "room_no INT NOT NULL, "
                   + "instructions TEXT"
                   + ")";
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
            System.out.println("Table 'room_service' verified/created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating room_service table: " + e.getMessage());
        }
    }

    // Insert request
    public boolean insertRequest(RoomServiceModel request) {
        String sql = "INSERT INTO room_service (service_type, room_no, instructions) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, request.getServiceType());
            ps.setInt(2, request.getRoomNo());
            ps.setString(3, request.getInstructions());
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        request.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error inserting room service request: " + e.getMessage());
            return false;
        }
    }

    // Update request
    public boolean updateRequest(RoomServiceModel request) {
        String sql = "UPDATE room_service SET service_type=?, room_no=?, instructions=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, request.getServiceType());
            ps.setInt(2, request.getRoomNo());
            ps.setString(3, request.getInstructions());
            ps.setInt(4, request.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating room service request: " + e.getMessage());
            return false;
        }
    }

    // Delete request by ID
    public boolean deleteRequest(int id) {
        String sql = "DELETE FROM room_service WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting room service request: " + e.getMessage());
            return false;
        }
    }

    // Retrieve all requests
    public List<RoomServiceModel> getAllRequests() {
        List<RoomServiceModel> list = new ArrayList<>();
        String sql = "SELECT * FROM room_service";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                RoomServiceModel request = new RoomServiceModel(
                    rs.getInt("id"),
                    rs.getString("service_type"),
                    rs.getInt("room_no"),
                    rs.getString("instructions")
                );
                list.add(request);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching room service requests: " + e.getMessage());
        }
        return list;
    }
}
// git