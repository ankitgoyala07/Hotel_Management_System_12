package dao;

import database.MySqlConnection;
import model.DiscountModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for handling discount-related database operations.
 */
public class DiscountDao {
    private final MySqlConnection mysql = new MySqlConnection();

    /**
     * Retrieves all discount deals from the database.
     * 
     * @return List of DiscountModel records
     */
    public List<DiscountModel> getAllDiscounts() {
        List<DiscountModel> list = new ArrayList<>();
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return list;
        }

        String sql = "SELECT * FROM discounts";
        try (PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
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
        } catch (Exception e) {
            System.err.println("Error getting discounts: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return list;
    }

    /**
     * Adds a new discount deal to the database.
     * 
     * @param discount The deal to add
     * @return true if successful, false otherwise
     */
    public boolean addDiscount(DiscountModel discount) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return false;
        }

        String sql = "INSERT INTO discounts (deal_code, deal_name, reservations_left, end_date, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, discount.getDealCode());
            pstm.setString(2, discount.getDealName());
            pstm.setInt(3, discount.getReservationsLeft());
            pstm.setString(4, discount.getEndDate());
            pstm.setString(5, discount.getStatus());
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error adding discount: " + e.getMessage());
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }

    /**
     * Deletes a discount deal from the database using its code.
     * 
     * @param dealCode The code of the deal to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteDiscount(String dealCode) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return false;
        }

        String sql = "DELETE FROM discounts WHERE deal_code = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, dealCode);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error deleting discount: " + e.getMessage());
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }

    /**
     * Retrieves a single discount deal by its unique deal code.
     * 
     * @param dealCode The code of the deal to find
     * @return DiscountModel if found, null otherwise
     */
    public DiscountModel getDiscountByCode(String dealCode) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return null;
        }

        String sql = "SELECT * FROM discounts WHERE deal_code = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, dealCode);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return new DiscountModel(
                        rs.getString("deal_code"),
                        rs.getString("deal_name"),
                        rs.getInt("reservations_left"),
                        rs.getString("end_date"),
                        rs.getString("status")
                    );
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting discount by code: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return null;
    }

    /**
     * Decrements the reservations left by 1 and updates the status
     * to 'full' or 'finished' if conditions are met.
     * 
     * @param dealCode The code of the deal to update
     * @return true if update was successful, false otherwise
     */
    public boolean decrementReservations(String dealCode) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return false;
        }

        String selectSql = "SELECT reservations_left, end_date FROM discounts WHERE deal_code = ?";
        String updateSql = "UPDATE discounts SET reservations_left = ?, status = ? WHERE deal_code = ?";

        try {
            int currentRes = -1;
            String endDateStr = "";
            try (PreparedStatement pstm = conn.prepareStatement(selectSql)) {
                pstm.setString(1, dealCode);
                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        currentRes = rs.getInt("reservations_left");
                        endDateStr = rs.getString("end_date");
                    }
                }
            }

            if (currentRes <= 0) {
                return false; // Already full or not found
            }

            int newRes = currentRes - 1;
            String newStatus = "ongoing";

            if (newRes <= 0) {
                newStatus = "full";
            } else {
                // Check if end date has passed
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date end = sdf.parse(endDateStr);
                    java.util.Date today = new java.util.Date();
                    java.util.Calendar calToday = java.util.Calendar.getInstance();
                    calToday.set(java.util.Calendar.HOUR_OF_DAY, 0);
                    calToday.set(java.util.Calendar.MINUTE, 0);
                    calToday.set(java.util.Calendar.SECOND, 0);
                    calToday.set(java.util.Calendar.MILLISECOND, 0);
                    if (end.before(calToday.getTime())) {
                        newStatus = "finished";
                    }
                } catch (Exception ex) {
                    // Date parsing error, keep status ongoing
                }
            }

            try (PreparedStatement pstm = conn.prepareStatement(updateSql)) {
                pstm.setInt(1, newRes);
                pstm.setString(2, newStatus);
                pstm.setString(3, dealCode);
                return pstm.executeUpdate() > 0;
            }

        } catch (Exception e) {
            System.err.println("Error decrementing reservations: " + e.getMessage());
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }
}
