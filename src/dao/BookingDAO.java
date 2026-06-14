package dao;

import database.MySqlConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.BookingModel;

public class BookingDAO {

    public List<BookingModel> getBookings(String search, String roomType, String statusFilter, int offset, int limit) {
        List<BookingModel> bookings = new ArrayList<>();
        MySqlConnection db = new MySqlConnection();
        Connection conn = db.Openconnection();

        if (conn == null) {
            System.out.println("Warning: Database connection failed. Returning mock data matching Figma design.");
            return getMockBookings(search, roomType, statusFilter, offset, limit);
        }

        try {
            StringBuilder sql = new StringBuilder(
                "SELECT b.booking_id, CONCAT(g.first_name, ' ', g.last_name) AS guest_name, " +
                "b.room_number, r.room_type, b.check_in_date, b.check_out_date, b.status, " +
                "COALESCE(bl.amount, 0.00) AS total_amount " +
                "FROM bookings b " +
                "JOIN guests g ON b.guest_id = g.guest_id " +
                "JOIN rooms r ON b.room_number = r.room_number " +
                "LEFT JOIN billings bl ON b.booking_id = bl.booking_id " +
                "WHERE 1=1 "
            );

            if (search != null && !search.trim().isEmpty()) {
                sql.append("AND (CONCAT(g.first_name, ' ', g.last_name) LIKE '%").append(search).append("%' ")
                   .append("OR b.booking_id LIKE '%").append(search).append("%') ");
            }

            if (roomType != null && !roomType.equalsIgnoreCase("All Types") && !roomType.trim().isEmpty()) {
                sql.append("AND r.room_type = '").append(roomType).append("' ");
            }

            if (statusFilter != null && !statusFilter.equalsIgnoreCase("All Status") && !statusFilter.trim().isEmpty()) {
                // Handle different spelling in DB vs UI
                String dbStatus = statusFilter;
                if (statusFilter.equalsIgnoreCase("Checked-in")) dbStatus = "CheckedIn";
                else if (statusFilter.equalsIgnoreCase("Checked-out")) dbStatus = "CheckedOut";
                sql.append("AND b.status = '").append(dbStatus).append("' ");
            }

            sql.append("ORDER BY b.booking_id DESC LIMIT ").append(limit).append(" OFFSET ").append(offset);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql.toString());

            while (rs.next()) {
                BookingModel model = new BookingModel(
                    rs.getInt("booking_id"),
                    rs.getString("guest_name"),
                    rs.getString("room_number"),
                    rs.getString("room_type"),
                    rs.getDate("check_in_date"),
                    rs.getDate("check_out_date"),
                    rs.getString("status"),
                    rs.getDouble("total_amount")
                );
                bookings.add(model);
            }

        } catch (Exception e) {
            System.err.println("Error querying bookings: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }

        if (bookings.isEmpty() && (search == null || search.trim().isEmpty()) && 
            (roomType == null || roomType.equalsIgnoreCase("All Types")) && 
            (statusFilter == null || statusFilter.equalsIgnoreCase("All Status"))) {
            return getMockBookings(search, roomType, statusFilter, offset, limit);
        }

        return bookings;
    }

    public int getBookingsCount(String search, String roomType, String statusFilter) {
        MySqlConnection db = new MySqlConnection();
        Connection conn = db.Openconnection();

        if (conn == null) {
            return getMockBookings(search, roomType, statusFilter, 0, 100).size();
        }

        int count = 0;
        try {
            StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) AS total FROM bookings b " +
                "JOIN guests g ON b.guest_id = g.guest_id " +
                "JOIN rooms r ON b.room_number = r.room_number " +
                "WHERE 1=1 "
            );

            if (search != null && !search.trim().isEmpty()) {
                sql.append("AND (CONCAT(g.first_name, ' ', g.last_name) LIKE '%").append(search).append("%' ")
                   .append("OR b.booking_id LIKE '%").append(search).append("%') ");
            }

            if (roomType != null && !roomType.equalsIgnoreCase("All Types") && !roomType.trim().isEmpty()) {
                sql.append("AND r.room_type = '").append(roomType).append("' ");
            }

            if (statusFilter != null && !statusFilter.equalsIgnoreCase("All Status") && !statusFilter.trim().isEmpty()) {
                String dbStatus = statusFilter;
                if (statusFilter.equalsIgnoreCase("Checked-in")) dbStatus = "CheckedIn";
                else if (statusFilter.equalsIgnoreCase("Checked-out")) dbStatus = "CheckedOut";
                sql.append("AND b.status = '").append(dbStatus).append("' ");
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql.toString());
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (Exception e) {
            System.err.println("Error counting bookings: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }

        if (count == 0 && (search == null || search.trim().isEmpty()) && 
            (roomType == null || roomType.equalsIgnoreCase("All Types")) && 
            (statusFilter == null || statusFilter.equalsIgnoreCase("All Status"))) {
            return getMockBookings(search, roomType, statusFilter, 0, 100).size();
        }

        return count;
    }

    private List<BookingModel> getMockBookings(String search, String roomType, String statusFilter, int offset, int limit) {
        List<BookingModel> allMock = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            allMock.add(new BookingModel(5, "Sarah Jenkins", "402", "Double bed", sdf.parse("2023-10-14"), sdf.parse("2023-10-18"), "Confirmed", 1240.00));
            allMock.add(new BookingModel(6, "David Redsun", "210", "VIP", sdf.parse("2023-10-12"), sdf.parse("2023-10-15"), "CheckedIn", 645.00));
            allMock.add(new BookingModel(7, "Elena Rodriguez", "505", "Single bed", sdf.parse("2023-10-20"), sdf.parse("2023-10-22"), "Pending", 2100.00));
            allMock.add(new BookingModel(8, "Michael Kross", "--", "Double bed", sdf.parse("2023-10-15"), sdf.parse("2023-10-17"), "Cancelled", 0.00));
            
            // Add some extra items to show pagination
            allMock.add(new BookingModel(1, "John Doe", "101", "Single", sdf.parse("2026-05-25"), sdf.parse("2026-05-30"), "CheckedIn", 400.00));
            allMock.add(new BookingModel(2, "Jane Smith", "202", "Double", sdf.parse("2026-05-28"), sdf.parse("2026-06-02"), "CheckedIn", 600.00));
            allMock.add(new BookingModel(3, "Bob Johnson", "301", "Suite", sdf.parse("2026-05-29"), sdf.parse("2026-06-05"), "CheckedIn", 1750.00));
            allMock.add(new BookingModel(4, "Alice Brown", "303", "Suite", sdf.parse("2026-05-30"), sdf.parse("2026-06-03"), "CheckedIn", 1000.00));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Apply filters in-memory for mock
        List<BookingModel> filtered = new ArrayList<>();
        for (BookingModel m : allMock) {
            boolean matchesSearch = true;
            if (search != null && !search.trim().isEmpty()) {
                String term = search.toLowerCase();
                matchesSearch = m.getGuestName().toLowerCase().contains(term) || String.valueOf(m.getBookingId()).contains(term);
            }

            boolean matchesType = true;
            if (roomType != null && !roomType.equalsIgnoreCase("All Types") && !roomType.trim().isEmpty()) {
                matchesType = m.getRoomType().equalsIgnoreCase(roomType);
            }

            boolean matchesStatus = true;
            if (statusFilter != null && !statusFilter.equalsIgnoreCase("All Status") && !statusFilter.trim().isEmpty()) {
                String filterValue = statusFilter.replaceAll("-", "").toLowerCase();
                String modelValue = m.getStatus().replaceAll("-", "").toLowerCase();
                matchesStatus = modelValue.equals(filterValue);
            }

            if (matchesSearch && matchesType && matchesStatus) {
                filtered.add(m);
            }
        }

        // Apply pagination
        List<BookingModel> paginated = new ArrayList<>();
        for (int i = offset; i < filtered.size() && paginated.size() < limit; i++) {
            paginated.add(filtered.get(i));
        }

        return paginated;
    }
}
