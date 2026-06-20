package dao;

import database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.MealTimeModel;

/**
 * MealTimeDao manages meal time schedules in the MySQL database.
 */
public class MealTimeDao {
    private final MySqlConnection mysql = new MySqlConnection();

    /**
     * Retrieves all meal schedule records.
     * @return List of MealTimeModel records
     */
    public List<MealTimeModel> getAllMealTimes() {
        List<MealTimeModel> list = new ArrayList<>();
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            System.out.println("Connection failed.");
            return list;
        }

        String sql = "SELECT room_type, breakfast_timing, lunch_timing, dinner_timing FROM mealtime";
        try (PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                list.add(new MealTimeModel(
                    rs.getString("room_type"),
                    rs.getString("breakfast_timing"),
                    rs.getString("lunch_timing"),
                    rs.getString("dinner_timing")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error fetching meal times: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return list;
    }

    /**
     * Updates an existing meal schedule record matching the room type.
     * @param roomType Room type identifier
     * @param updatedMealTime The updated record properties
     * @return true if record was found and updated, false otherwise
     */
    public boolean updateMealTimeByRoom(String roomType, MealTimeModel updatedMealTime) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            System.out.println("Connection failed.");
            return false;
        }

        String sql = "UPDATE mealtime SET breakfast_timing = ?, lunch_timing = ?, dinner_timing = ? WHERE room_type = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, updatedMealTime.getBreakfastTiming());
            pstm.setString(2, updatedMealTime.getLunchTiming());
            pstm.setString(3, updatedMealTime.getDinnerTiming());
            pstm.setString(4, roomType);
            int rowsUpdated = pstm.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            System.out.println("Error updating meal time: " + e.getMessage());
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }
}
