package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.BookRoomModel;

/**
 * DAO class for room browsing operations.
 * Handles database operations for rooms categories.
 */
public class BookRoomDao {
    private Connection conn;

    // Constructor with connection
    public BookRoomDao(Connection conn) {
        this.conn = conn;
    }

    // DDL: Create table if not present in the database
    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS room_categories ("
                   + "id INT AUTO_INCREMENT PRIMARY KEY, "
                   + "room_type VARCHAR(255) NOT NULL, "
                   + "price DOUBLE NOT NULL, "
                   + "room_size VARCHAR(50), "
                   + "bed_type VARCHAR(100), "
                   + "description TEXT"
                   + ")";
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
            System.out.println("Table 'room_categories' verified/created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating room_categories table: " + e.getMessage());
        }
    }

    // Insert a room category
    public boolean insertRoom(BookRoomModel room) {
        String sql = "INSERT INTO room_categories (room_type, price, room_size, bed_type, description) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, room.getRoomType());
            ps.setDouble(2, room.getPrice());
            ps.setString(3, room.getRoomSize());
            ps.setString(4, room.getBedType());
            ps.setString(5, room.getDescription());
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        room.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error inserting room category: " + e.getMessage());
            return false;
        }
    }

    // Update room category details
    public boolean updateRoom(BookRoomModel room) {
        String sql = "UPDATE room_categories SET room_type=?, price=?, room_size=?, bed_type=?, description=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, room.getRoomType());
            ps.setDouble(2, room.getPrice());
            ps.setString(3, room.getRoomSize());
            ps.setString(4, room.getBedType());
            ps.setString(5, room.getDescription());
            ps.setInt(6, room.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating room category: " + e.getMessage());
            return false;
        }
    }

    // Delete a room category by ID
    public boolean deleteRoom(int id) {
        String sql = "DELETE FROM room_categories WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting room category: " + e.getMessage());
            return false;
        }
    }

    // Retrieve all room categories
    public List<BookRoomModel> getAllRooms() {
        List<BookRoomModel> rooms = new ArrayList<>();
        String sql = "SELECT * FROM room_categories";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                BookRoomModel room = new BookRoomModel(
                    rs.getInt("id"),
                    rs.getString("room_type"),
                    rs.getDouble("price"),
                    rs.getString("room_size"),
                    rs.getString("bed_type"),
                    rs.getString("description")
                );
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching room categories: " + e.getMessage());
        }
        return rooms;
    }
}