package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class to verify database connectivity on application startup.
 */
public class DatabaseSetup {
    private static final String URL = "jdbc:mysql://localhost:3306/hotel_management";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    /**
     * Tests connection to the hotel_management database.
     */
    public static void initializeDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                System.out.println("Connected to hotel_management database successfully.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database connection failed during startup initialization: " + e.getMessage());
        }
    }
}