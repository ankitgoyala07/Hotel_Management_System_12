package dao;

import database.MySqlConnection;
import model.BookingManagementModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Data Access Object for handling Booking Management lookups from guest_details.
 */
public class BookingManagementDao {
    private final MySqlConnection mysql = new MySqlConnection();

    /**
     * Retrieves active booking information for a room number if it is currently Checked In.
     *
     * @param roomNo the room number to lookup
     * @return BookingManagementModel if checked in guest is found, null otherwise
     */
    public BookingManagementModel getBookingDetails(int roomNo) {
        Connection conn = mysql.Openconnection();
        if (conn == null) return null;

        String sql = "SELECT full_name, phone_number, email_address, home_address, room_type, check_in_date, check_out_date, discount_deal "
                   + "FROM guest_details "
                   + "WHERE room_no = ? AND status = 'Checked In' "
                   + "ORDER BY guest_id DESC LIMIT 1";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, roomNo);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return new BookingManagementModel(
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("email_address"),
                        rs.getString("home_address"),
                        rs.getString("room_type"),
                        rs.getDate("check_in_date"),
                        rs.getDate("check_out_date"),
                        rs.getString("discount_deal")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error querying booking details: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return null;
    }
}
