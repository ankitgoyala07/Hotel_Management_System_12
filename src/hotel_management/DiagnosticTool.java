package hotel_management;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DiagnosticTool {
    public static void main(String[] args) {
        System.out.println("=== HOTEL MANAGEMENT SYSTEM DIAGNOSTICS ===");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Java Vendor: " + System.getProperty("java.vendor"));
        System.out.println("OS Name: " + System.getProperty("os.name"));
        System.out.println("OS Version: " + System.getProperty("os.version"));
        System.out.println("Classpath: " + System.getProperty("java.class.path"));
        
        System.out.println("\nChecking MySQL Driver...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("SUCCESS: MySQL JDBC Driver (com.mysql.cj.jdbc.Driver) found on classpath.");
        } catch (ClassNotFoundException e) {
            System.out.println("FAILED: MySQL JDBC Driver NOT found. Please add the mysql-connector-j JAR to your NetBeans libraries.");
            e.printStackTrace();
            return;
        }

        String dbUrl = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "1234";
        String dbName = "hotel_management";

        System.out.println("\nAttempting to connect to MySQL Server (localhost:3306)...");
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password)) {
            System.out.println("SUCCESS: Connected to MySQL server successfully.");
            
            System.out.println("\nChecking if database '" + dbName + "' exists...");
            try (ResultSet rs = conn.getMetaData().getCatalogs()) {
                boolean dbExists = false;
                while (rs.next()) {
                    String catalog = rs.getString(1);
                    if (dbName.equalsIgnoreCase(catalog)) {
                        dbExists = true;
                        break;
                    }
                }
                if (dbExists) {
                    System.out.println("SUCCESS: Database '" + dbName + "' exists.");
                } else {
                    System.out.println("INFO: Database '" + dbName + "' does not exist yet (it will be auto-created on normal run).");
                }
            }
        } catch (Exception e) {
            System.out.println("FAILED: Could not connect to MySQL server. Please make sure MySQL is running and your password is '" + password + "'.");
            System.out.println("Error details: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        System.out.println("\nAttempting to connect to '" + dbName + "' database...");
        try (Connection conn = DriverManager.getConnection(dbUrl + dbName, username, password)) {
            System.out.println("SUCCESS: Connected to database '" + dbName + "' successfully.");
            
            System.out.println("\nChecking database tables:");
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getTables(dbName, null, "%", new String[]{"TABLE"})) {
                boolean hasTables = false;
                while (rs.next()) {
                    hasTables = true;
                    System.out.println(" - Found table: " + rs.getString("TABLE_NAME"));
                }
                if (!hasTables) {
                    System.out.println(" - No tables found (they will be auto-created on normal run).");
                }
            }
        } catch (Exception e) {
            System.out.println("FAILED: Could not connect to '" + dbName + "' database directly.");
            System.out.println("Error details: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
        System.out.println("\nChecking GUI resources...");
        try {
            var resource = DiagnosticTool.class.getResource("/images/hotelpic.png");
            if (resource != null) {
                System.out.println("SUCCESS: '/images/hotelpic.png' found.");
            } else {
                System.out.println("FAILED: '/images/hotelpic.png' NOT found. Make sure the 'images' folder exists under src/ and contains 'hotelpic.png'.");
            }
        } catch (Exception e) {
            System.out.println("FAILED: Error checking resources: " + e.getMessage());
        }

        System.out.println("\n=== ALL DIAGNOSTIC CHECKS COMPLETED ===");
    }
}
