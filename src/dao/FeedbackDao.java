package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.FeedbackModel;

/**
 * DAO class for Feedback operations.
 * Handles database operations for guest feedback.
 */
public class FeedbackDao {
    private Connection conn;

    // Constructor with connection
    public FeedbackDao(Connection conn) {
        this.conn = conn;
    }

    // DDL: Create table if not present in the database
    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS feedback ("
                   + "id INT AUTO_INCREMENT PRIMARY KEY, "
                   + "service_rating INT NOT NULL, "
                   + "cleanliness_rating INT NOT NULL, "
                   + "food_rating INT NOT NULL, "
                   + "review_text TEXT"
                   + ")";
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
            System.out.println("Table 'feedback' verified/created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating feedback table: " + e.getMessage());
        }
    }

    // Insert feedback
    public boolean insertFeedback(FeedbackModel feedback) {
        String sql = "INSERT INTO feedback (service_rating, cleanliness_rating, food_rating, review_text) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, feedback.getServiceRating());
            ps.setInt(2, feedback.getCleanlinessRating());
            ps.setInt(3, feedback.getFoodRating());
            ps.setString(4, feedback.getReviewText());
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        feedback.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error inserting feedback: " + e.getMessage());
            return false;
        }
    }

    // Update feedback
    public boolean updateFeedback(FeedbackModel feedback) {
        String sql = "UPDATE feedback SET service_rating=?, cleanliness_rating=?, food_rating=?, review_text=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, feedback.getServiceRating());
            ps.setInt(2, feedback.getCleanlinessRating());
            ps.setInt(3, feedback.getFoodRating());
            ps.setString(4, feedback.getReviewText());
            ps.setInt(5, feedback.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating feedback: " + e.getMessage());
            return false;
        }
    }

    // Delete feedback by ID
    public boolean deleteFeedback(int id) {
        String sql = "DELETE FROM feedback WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting feedback: " + e.getMessage());
            return false;
        }
    }

    // Retrieve all feedback
    public List<FeedbackModel> getAllFeedback() {
        List<FeedbackModel> list = new ArrayList<>();
        String sql = "SELECT * FROM feedback";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
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
        } catch (SQLException e) {
            System.out.println("Error fetching feedback list: " + e.getMessage());
        }
        return list;
    }
}
