import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class DbTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hotel_management";
        String user = "root";
        String password = "1234";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                System.out.println("Connected to hotel_management!");
                String[] tables = {"rooms", "bookings", "users"};
                for (String table : tables) {
                    System.out.println("\nTable: " + table);
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery("SELECT * FROM " + table + " LIMIT 1")) {
                        ResultSetMetaData md = rs.getMetaData();
                        int columns = md.getColumnCount();
                        for (int i = 1; i <= columns; i++) {
                            System.out.println(" - " + md.getColumnName(i) + " (" + md.getColumnTypeName(i) + ")");
                        }
                    } catch (Exception e) {
                        System.out.println("Error reading table " + table + ": " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
