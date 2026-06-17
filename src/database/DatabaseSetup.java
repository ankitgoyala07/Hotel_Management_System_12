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

            // Create users table if it doesn't exist
            String createUsersSql = "CREATE TABLE IF NOT EXISTS users ("
                    + "user_id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "username VARCHAR(100) UNIQUE NOT NULL, "
                    + "email VARCHAR(100) UNIQUE NOT NULL, "
                    + "phone VARCHAR(20) NOT NULL, "
                    + "password VARCHAR(100) NOT NULL, "
                    + "role VARCHAR(50) NOT NULL, "
                    + "security_questions VARCHAR(255)"
                    + ")";
            stmt.executeUpdate(createUsersSql);
            System.out.println("Table 'users' verified/created.");

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

            // Create rooms table if it doesn't exist
            String createRoomsSql = "CREATE TABLE IF NOT EXISTS rooms ("
                    + "room_number VARCHAR(10) PRIMARY KEY, "
                    + "room_type VARCHAR(20) NOT NULL, "
                    + "status VARCHAR(20) NOT NULL DEFAULT 'Available'"
                    + ")";
            stmt.executeUpdate(createRoomsSql);
            System.out.println("Table 'rooms' verified/created.");

            // Seed rooms table if empty
            String checkRoomsQuery = "SELECT COUNT(*) FROM rooms";
            try (java.sql.ResultSet rsRooms = stmt.executeQuery(checkRoomsQuery)) {
                if (rsRooms.next() && rsRooms.getInt(1) == 0) {
                    String seedRoomsSql = "INSERT INTO rooms (room_number, room_type, status) VALUES "
                            + "('001', 'Single', 'Occupied'), "
                            + "('101', 'Single', 'Occupied'), ('102', 'Single', 'Occupied'), ('103', 'Single', 'Occupied'), "
                            + "('104', 'Single', 'Occupied'), ('105', 'Single', 'Occupied'), ('106', 'Single', 'Available'), "
                            + "('107', 'Single', 'Occupied'), ('108', 'Single', 'Available'), ('109', 'Single', 'Available'), "
                            + "('110', 'Single', 'Available'), ('111', 'Single', 'Available'), ('112', 'Single', 'Occupied'), "
                            + "('113', 'Single', 'Available'), ('114', 'Single', 'Occupied'), ('115', 'Single', 'Available'), "
                            + "('116', 'Single', 'Available'), ('117', 'Single', 'Occupied'), ('118', 'Single', 'Occupied'), "
                            + "('119', 'Single', 'Available'), ('120', 'Single', 'Occupied'), ('121', 'Single', 'Occupied'), "
                            + "('122', 'Single', 'Available'), ('123', 'Single', 'Occupied'), ('124', 'Single', 'Available'), "
                            + "('201', 'Double', 'Occupied'), ('202', 'Double', 'Occupied'), ('203', 'Double', 'Occupied'), "
                            + "('204', 'Double', 'Occupied'), ('205', 'Double', 'Occupied'), ('206', 'Double', 'Occupied'), "
                            + "('207', 'Double', 'Occupied'), ('208', 'Double', 'Available'), ('209', 'Double', 'Available'), "
                            + "('210', 'Double', 'Available'), ('211', 'Double', 'Available'), ('212', 'Double', 'Available'), "
                            + "('213', 'Double', 'Available'), ('214', 'Double', 'Available'), ('215', 'Double', 'Occupied'), "
                            + "('216', 'Double', 'Occupied'), ('217', 'Double', 'Occupied'), ('218', 'Double', 'Occupied'), "
                            + "('219', 'Double', 'Occupied'), ('220', 'Double', 'Occupied'), ('221', 'Double', 'Occupied'), "
                            + "('222', 'Double', 'Occupied'), ('223', 'Double', 'Available'), "
                            + "('301', 'VIP', 'Occupied'), ('302', 'VIP', 'Occupied'), ('303', 'VIP', 'Occupied'), "
                            + "('304', 'VIP', 'Occupied'), ('305', 'VIP', 'Occupied'), ('306', 'VIP', 'Available'), "
                            + "('307', 'VIP', 'Available'), ('308', 'VIP', 'Available'), ('309', 'VIP', 'Available'), "
                            + "('310', 'VIP', 'Available')";
                    stmt.executeUpdate(seedRoomsSql);
                    System.out.println("Default rooms seeded successfully.");
                }
            }

            // Create billings table if it doesn't exist
            String createBillingsSql = "CREATE TABLE IF NOT EXISTS billings ("
                    + "bill_id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "guest_id VARCHAR(50) NOT NULL, "
                    + "room_number VARCHAR(10) NOT NULL, "
                    + "stay_period VARCHAR(100) NOT NULL, "
                    + "nights INT NOT NULL, "
                    + "room_rate DECIMAL(10, 2) NOT NULL, "
                    + "room_service DECIMAL(10, 2) NOT NULL DEFAULT 0.00, "
                    + "food_orders DECIMAL(10, 2) NOT NULL DEFAULT 0.00, "
                    + "laundry DECIMAL(10, 2) NOT NULL DEFAULT 0.00, "
                    + "mini_bar DECIMAL(10, 2) NOT NULL DEFAULT 0.00"
                    + ")";
            stmt.executeUpdate(createBillingsSql);
            System.out.println("Table 'billings' verified/created.");

            // Seed billings table if empty
            String checkBillingsQuery = "SELECT COUNT(*) FROM billings";
            try (java.sql.ResultSet rsBillings = stmt.executeQuery(checkBillingsQuery)) {
                if (rsBillings.next() && rsBillings.getInt(1) == 0) {
                    String seedBillingsSql = "INSERT INTO billings (guest_id, room_number, stay_period, nights, room_rate, room_service, food_orders, laundry, mini_bar) VALUES "
                            + "('001', '001', 'Oct 14 - Oct 18 (4 Nights)', 4, 250.00, 65.50, 145.00, 36.00, 22.00)";
                    stmt.executeUpdate(seedBillingsSql);
                    System.out.println("Default billings seeded successfully.");
                }
            }

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