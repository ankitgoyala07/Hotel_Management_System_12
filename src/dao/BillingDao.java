package dao;

import database.MySqlConnection;
import model.BillingModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Data Access Object for handling billing-related database query operations.
 *
 * @author i3
 */
public class BillingDao {
    private final MySqlConnection mysql = new MySqlConnection();

    /**
     * Retrieves dynamic billing information based on a room number.
     * Computes rates dynamically according to room type (Single = $250, Double = $400, VIP = $800).
     *
     * @param roomNumber the identifier of the room to calculate the bill for
     * @return BillingModel initialized with database values or fallbacks
     */
    public BillingModel getBillingForRoom(String roomNumber) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return new BillingModel("001", roomNumber, "Oct 14 - Oct 18 (4 Nights)", 4, 250.00, 65.50, 145.00, 36.00, 22.00);
        }

        try {
            // First try to query the billings table
            String billingSql = "SELECT guest_id, room_number, stay_period, nights, room_rate, room_service, food_orders, laundry, mini_bar FROM billings WHERE room_number = ?";
            try (PreparedStatement pstm = conn.prepareStatement(billingSql)) {
                pstm.setString(1, roomNumber);
                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        String guestId = rs.getString("guest_id");
                        String stayPeriod = rs.getString("stay_period");
                        int nights = rs.getInt("nights");
                        double roomRate = rs.getDouble("room_rate");
                        double roomService = rs.getDouble("room_service");
                        double foodOrders = rs.getDouble("food_orders");
                        double laundry = rs.getDouble("laundry");
                        double miniBar = rs.getDouble("mini_bar");
                        return new BillingModel(guestId, roomNumber, stayPeriod, nights, roomRate, roomService, foodOrders, laundry, miniBar);
                    }
                }
            }

            // Fallback: If not found in billings, query the rooms table
            String sql = "SELECT room_type, status FROM rooms WHERE room_number = ?";
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setString(1, roomNumber);
                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        String roomType = rs.getString("room_type");
                        
                        double rate = 250.00;
                        if (roomType.equalsIgnoreCase("Double")) {
                            rate = 400.00;
                        } else if (roomType.equalsIgnoreCase("VIP")) {
                            rate = 800.00;
                        }

                        // Simulated stays/services since booking/guest details are not fully in db schema yet
                        String guestId = "G" + roomNumber;
                        String stayPeriod = "Oct 14 - Oct 18 (4 Nights)";
                        return new BillingModel(guestId, roomNumber, stayPeriod, 4, rate, 65.50, 145.00, 36.00, 22.00);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error getting billing from database: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        
        // Return default bill fallback if not in database
        return new BillingModel("001", roomNumber, "Oct 14 - Oct 18 (4 Nights)", 4, 250.00, 65.50, 145.00, 36.00, 22.00);
    }
}
