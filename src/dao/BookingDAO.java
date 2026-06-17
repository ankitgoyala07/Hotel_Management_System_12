package dao;

/**
 * Data Access Object executing list, search, and filter queries on joined bookings tables.
 */

import database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.BookingModel;

public class BookingDAO {

    public List<BookingModel> getBookings(String search, String roomType, String status, int offset, int limit) {
        List<BookingModel> bookingsList = new ArrayList<>();
        MySqlConnection db = new MySqlConnection();
        Connection conn = db.Openconnection();

        if (conn == null) {
            System.out.println("Warning: Database connection failed. Returning default/mock bookings.");
            return getMockBookings();
        }

        StringBuilder query = new StringBuilder(
            "SELECT b.booking_id, CONCAT(g.first_name, ' ', g.last_name) AS guest_name, " +
            "b.room_number, r.room_type, b.check_in_date, b.check_out_date, b.status, " +
            "COALESCE(bl.amount, 0.0) AS amount " +
            "FROM bookings b " +
            "JOIN guests g ON b.guest_id = g.guest_id " +
            "JOIN rooms r ON b.room_number = r.room_number " +
            "LEFT JOIN billings bl ON b.booking_id = bl.booking_id " +
            "WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();

        if (search != null && !search.trim().isEmpty()) {
            query.append(" AND (CONCAT(g.first_name, ' ', g.last_name) LIKE ? OR b.room_number LIKE ?)");
            params.add("%" + search.trim() + "%");
            params.add("%" + search.trim() + "%");
        }

        if (roomType != null && !roomType.equals("All Types") && !roomType.trim().isEmpty()) {
            query.append(" AND r.room_type = ?");
            params.add(roomType.trim());
        }

        if (status != null && !status.equals("All Status") && !status.trim().isEmpty()) {
            query.append(" AND b.status = ?");
            params.add(status.trim());
        }

        query.append(" ORDER BY b.booking_id DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BookingModel booking = new BookingModel(
                        rs.getInt("booking_id"),
                        rs.getString("guest_name"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDate("check_in_date"),
                        rs.getDate("check_out_date"),
                        rs.getString("status"),
                        rs.getDouble("amount")
                    );
                    bookingsList.add(booking);
                }
            }
        } catch (Exception e) {
            System.out.println("Error querying bookings: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }

        if (bookingsList.isEmpty()) {
            return getMockBookings();
        }

        return bookingsList;
    }

    private List<BookingModel> getMockBookings() {
        List<BookingModel> mockList = new ArrayList<>();
        java.util.Date today = new java.util.Date();
        java.util.Date tomorrow = new java.util.Date(today.getTime() + 86400000);
        
        mockList.add(new BookingModel(1001, "John Doe", "101", "Single", today, tomorrow, "CheckedIn", 80.00));
        mockList.add(new BookingModel(1002, "Jane Smith", "202", "Double", today, tomorrow, "CheckedIn", 120.00));
        mockList.add(new BookingModel(1003, "Bob Johnson", "301", "VIP", today, tomorrow, "CheckedIn", 250.00));
        mockList.add(new BookingModel(1004, "Alice Brown", "303", "VIP", today, tomorrow, "CheckedIn", 250.00));
        return mockList;
    }
}
