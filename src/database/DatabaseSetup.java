package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSetup {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private static final String DB_NAME = "hotel_management";

    public static void initializeDatabase() {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 1. Connect to MySQL server to create the database if it doesn't exist
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            
            String createDbSql = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            stmt.executeUpdate(createDbSql);
            System.out.println("Database '" + DB_NAME + "' initialized or already exists.");
            stmt.close();
            conn.close();

            // 2. Connect directly to the hotel_management database to create tables
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            stmt = conn.createStatement();

            // Create users table
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "user_id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "username VARCHAR(50) NOT NULL UNIQUE, "
                    + "email VARCHAR(100) NOT NULL, "
                    + "password VARCHAR(255) NOT NULL"
                    + ")";
            stmt.executeUpdate(createUsersTable);
            System.out.println("Table 'users' initialized or already exists.");

            // Create default admin user if table is empty
            String checkUsers = "SELECT COUNT(*) FROM users";
            try (ResultSet rs = stmt.executeQuery(checkUsers)) {
                if (rs.next() && rs.getInt(1) == 0) {
                    String insertAdmin = "INSERT INTO users (username, email, password) VALUES ('admin', 'admin@hotel.com', 'admin123')";
                    stmt.executeUpdate(insertAdmin);
                    System.out.println("Default admin user created ('admin' / 'admin123').");
                }
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
            }
        }
    }
}
