import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbInit {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/?allowMultiQueries=true";
        String user = "root";
        String password = "1234";
        String sqlFilePath = "c:/Users/rikes/OneDrive/文档/Desktop/NetBeansProjects/Hotel_Management_System_12/database_schema.sql";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Reading SQL file...");
            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(sqlFilePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // Skip comments
                    if (line.trim().startsWith("--") || line.trim().startsWith("#")) {
                        continue;
                    }
                    sb.append(line).append("\n");
                }
            }

            // Split queries by semicolon, handling simple cases
            String[] queries = sb.toString().split(";");

            System.out.println("Connecting to MySQL...");
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement()) {
                System.out.println("Executing initialization SQL...");
                for (String query : queries) {
                    if (query.trim().isEmpty()) {
                        continue;
                    }
                    try {
                        stmt.execute(query.trim());
                    } catch (Exception e) {
                        System.err.println("Error executing query:\n" + query.trim() + "\nError: " + e.getMessage());
                    }
                }
                System.out.println("Database initialization completed successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
