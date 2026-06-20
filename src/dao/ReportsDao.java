package dao;

import database.MySqlConnection;
import model.FeedbackModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for retrieving report statistics and feedback lists.
 */
public class ReportsDao {
    private final MySqlConnection mysql = new MySqlConnection();

    /**
     * Gets the total bookings count.
     * 
     * @return count of bookings
     */
    public int getTotalBookings() {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return 0;
        }
        String sql = "SELECT COUNT(*) FROM bookings";
        try (PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.err.println("Error getting total bookings: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return 0;
    }

    /**
     * Gets the overall staff attendance percentage.
     * 
     * @return percentage as double (0.0 to 100.0)
     */
    public double getOverallAttendancePercentage() {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return 0.0;
        }
        String sql = "SELECT COALESCE(ROUND(SUM(is_present) * 100.0 / COUNT(*), 1), 0.0) FROM staff_attendance";
        try (PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            System.err.println("Error getting staff attendance: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return 0.0;
    }

    /**
     * Gets the total offers (discounts) count.
     * 
     * @return count of discounts
     */
    public int getTotalOffers() {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return 0;
        }
        String sql = "SELECT COUNT(*) FROM discounts";
        try (PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.err.println("Error getting total offers: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return 0;
    }

    /**
     * Retrieves all feedback/reviews submitted by guests.
     * 
     * @return List of FeedbackModel objects
     */
    public List<FeedbackModel> getAllFeedback() {
        List<FeedbackModel> list = new ArrayList<>();
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return list;
        }
        String sql = "SELECT * FROM feedback ORDER BY id DESC";
        try (PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                FeedbackModel feedback = new FeedbackModel(
                    rs.getInt("id"),
                    rs.getInt("service_rating"),
                    rs.getInt("cleanliness_rating"),
                    rs.getInt("food_rating"),
                    rs.getString("review_text")
                );
                list.add(feedback);
            }
        } catch (Exception e) {
            System.err.println("Error fetching all feedback: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return list;
    }
}
