package dao;

import database.MySqlConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import model.admindashboardModel;

public class admindashboardDAO {

    public admindashboardModel getDashboardData() {
        MySqlConnection db = new MySqlConnection();
        Connection conn = db.Openconnection();

        if (conn == null) {
            System.out.println("Warning: Database connection failed. Returning mock dashboard data.");
            return new admindashboardModel(23, 12, 60, 40, 20);
        }

        int todayCheckIn = 0;
        int todayCheckOut = 0;
        int totalRooms = 0;
        int availableRooms = 0;
        int occupiedRooms = 0;

        try {
            Statement stmt = conn.createStatement();
            
            // 1. Get Today's Check-ins
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM bookings WHERE DATE(check_in_date) = CURDATE()");
            if (rs.next()) {
                todayCheckIn = rs.getInt(1);
            }
            rs.close();

            // 2. Get Today's Check-outs
            rs = stmt.executeQuery("SELECT COUNT(*) FROM bookings WHERE DATE(check_out_date) = CURDATE()");
            if (rs.next()) {
                todayCheckOut = rs.getInt(1);
            }
            rs.close();

            // 3. Get Total Rooms
            rs = stmt.executeQuery("SELECT COUNT(*) FROM rooms");
            if (rs.next()) {
                totalRooms = rs.getInt(1);
            }
            rs.close();

            // 4. Get Available Rooms
            rs = stmt.executeQuery("SELECT COUNT(*) FROM rooms WHERE status = 'Available'");
            if (rs.next()) {
                availableRooms = rs.getInt(1);
            }
            rs.close();

            // 5. Get Occupied Rooms
            rs = stmt.executeQuery("SELECT COUNT(*) FROM rooms WHERE status = 'Occupied'");
            if (rs.next()) {
                occupiedRooms = rs.getInt(1);
            }
            rs.close();

            stmt.close();
        } catch (Exception e) {
            System.out.println("Error fetching dashboard statistics: " + e.getMessage());
            return new admindashboardModel(23, 12, 60, 40, 20);
        } finally {
            db.closeConnection(conn);
        }

        return new admindashboardModel(todayCheckIn, todayCheckOut, totalRooms, availableRooms, occupiedRooms);
    }
}
