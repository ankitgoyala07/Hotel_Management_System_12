package dao;

import database.MySqlConnection;
import model.DiscountModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Discount database operations.
 */
public class DiscountDao {
    private final MySqlConnection dbConnection;

    public DiscountDao() {
        this.dbConnection = new MySqlConnection();
        ensureTableExists();
    }

    /**
     * Checks if the 'discounts' table exists and creates it if it does not.
     */
    private void ensureTableExists() {
        Connection conn = dbConnection.Openconnection();
        if (conn == null) {
            System.err.println("DiscountDao: Could not establish database connection.");
            return;
        }

        String sql = "CREATE TABLE IF NOT EXISTS discounts (" +
                     "deal_code VARCHAR(50) PRIMARY KEY, " +
                     "deal_name VARCHAR(100) NOT NULL, " +
                     "reservations_left INT NOT NULL, " +
                     "end_date VARCHAR(50) NOT NULL, " +
                     "status VARCHAR(50) NOT NULL" +
                     ")";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            System.out.println("DiscountDao: Discounts table verified/created successfully.");
        } catch (SQLException e) {
            System.err.println("DiscountDao: Error verifying or creating discounts table: " + e.getMessage());
        } finally {
            dbConnection.closeConnection(conn);
        }
    }

    /**
     * Retrieves all discount deals from the database.
     */
    public List<DiscountModel> getAllDiscounts() {
        List<DiscountModel> list = new ArrayList<>();
        Connection conn = dbConnection.Openconnection();
        if (conn == null) {
            return list;
        }

        String sql = "SELECT * FROM discounts";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DiscountModel model = new DiscountModel(
                    rs.getString("deal_code"),
                    rs.getString("deal_name"),
                    rs.getInt("reservations_left"),
                    rs.getString("end_date"),
                    rs.getString("status")
                );
                list.add(model);
            }
        } catch (SQLException e) {
            System.err.println("DiscountDao: Error retrieving discounts: " + e.getMessage());
        } finally {
            dbConnection.closeConnection(conn);
        }
        return list;
    }

    /**
     * Adds a new discount deal to the database.
     */
    public boolean addDiscount(DiscountModel discount) {
        Connection conn = dbConnection.Openconnection();
        if (conn == null) {
            return false;
        }

        String sql = "INSERT INTO discounts (deal_code, deal_name, reservations_left, end_date, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, discount.getDealCode());
            stmt.setString(2, discount.getDealName());
            stmt.setInt(3, discount.getReservationsLeft());
            stmt.setString(4, discount.getEndDate());
            stmt.setString(5, discount.getStatus());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("DiscountDao: Error inserting discount: " + e.getMessage());
            return false;
        } finally {
            dbConnection.closeConnection(conn);
        }
    }

    /**
     * Deletes a discount deal from the database.
     */
    public boolean deleteDiscount(String dealCode) {
        Connection conn = dbConnection.Openconnection();
        if (conn == null) {
            return false;
        }

        String sql = "DELETE FROM discounts WHERE deal_code = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dealCode);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("DiscountDao: Error deleting discount: " + e.getMessage());
            return false;
        } finally {
            dbConnection.closeConnection(conn);
        }
    }
}
