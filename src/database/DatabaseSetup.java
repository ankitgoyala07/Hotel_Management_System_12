package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseSetup {
    private static final String URL = "jdbc:mysql://localhost:3306/hotel_management";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static void initializeDatabase() {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            System.out.println("Connected to hotel_management database.");

            // Create system_settings table if it doesn't exist
            String createTableSql = "CREATE TABLE IF NOT EXISTS system_settings ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "hotel_name VARCHAR(255), "
                    + "hotel_id VARCHAR(100), "
                    + "address VARCHAR(255), "
                    + "pan_number VARCHAR(100), "
                    + "owner VARCHAR(100), "
                    + "quick_note TEXT, "
                    + "phone VARCHAR(50), "
                    + "website VARCHAR(100)"
                    + ")";
            stmt.executeUpdate(createTableSql);
            System.out.println("Table 'system_settings' verified/created.");

            String createStaffAttendanceSql = "CREATE TABLE IF NOT EXISTS staff_attendance ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "staff_id VARCHAR(50) NOT NULL, "
                    + "attendance_date DATE NOT NULL, "
                    + "is_present TINYINT(1) NOT NULL DEFAULT 0, "
                    + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "UNIQUE KEY unique_staff_date (staff_id, attendance_date), "
                    + "CONSTRAINT fk_staff_attendance_staff "
                    + "FOREIGN KEY (staff_id) REFERENCES staff(staff_id) "
                    + "ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")";
            stmt.executeUpdate(createStaffAttendanceSql);
            System.out.println("Table 'staff_attendance' verified/created.");

            // Seed default system settings if table is empty
            String checkQuery = "SELECT COUNT(*) FROM system_settings";
            java.sql.ResultSet rs = stmt.executeQuery(checkQuery);
            if (rs.next() && rs.getInt(1) == 0) {
                String seedSql = "INSERT INTO system_settings (hotel_name, hotel_id, address, pan_number, owner, quick_note, phone, website) "
                        + "VALUES ('Ankit', 'GH-001', '123 Main Street', '123456789', 'Ankit Goyala', "
                        + "'We give the best\\nexperience to our\\ncustomers', '+977-9843465098', 'www.grandhotel.com')";
                stmt.executeUpdate(seedSql);
                System.out.println("Default system settings seeded successfully.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}