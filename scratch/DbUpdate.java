import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbUpdate {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/shop";
        String user = "root";
        String password = "1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to shop database...");
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement()) {

                System.out.println("Modifying bookings table schema...");
                // Allow room_number to be NULL for cancelled/unassigned bookings
                stmt.execute("ALTER TABLE bookings MODIFY room_number VARCHAR(10) NULL");

                System.out.println("Inserting/updating rooms...");
                // Insert or update room 402
                stmt.execute("INSERT INTO rooms (room_number, room_type, status, price_per_night) " +
                             "VALUES ('402', 'Double bed', 'Available', 310.00) " +
                             "ON DUPLICATE KEY UPDATE room_type='Double bed', price_per_night=310.00");

                // Insert or update room 210
                stmt.execute("INSERT INTO rooms (room_number, room_type, status, price_per_night) " +
                             "VALUES ('210', 'VIP', 'Available', 215.00) " +
                             "ON DUPLICATE KEY UPDATE room_type='VIP', price_per_night=215.00");

                // Insert or update room 505
                stmt.execute("INSERT INTO rooms (room_number, room_type, status, price_per_night) " +
                             "VALUES ('505', 'Single bed', 'Available', 1050.00) " +
                             "ON DUPLICATE KEY UPDATE room_type='Single bed', price_per_night=1050.00");

                System.out.println("Inserting guests...");
                // Insert Figma mockup guests
                stmt.execute("INSERT INTO guests (first_name, last_name, email, phone, document_id) VALUES " +
                             "('Sarah', 'Jenkins', 'sarah.j@example.com', '+14155552671', 'PASS-SARAH'), " +
                             "('David', 'Redson', 'david.r@example.com', '+14155552672', 'PASS-DAVID'), " +
                             "('Elena', 'Rodriguez', 'elena.r@example.com', '+14155552673', 'PASS-ELENA'), " +
                             "('Michael', 'Kross', 'michael.k@example.com', '+14155552674', 'PASS-MICHAEL')");

                // Let's get the guest IDs. Assuming they are 5, 6, 7, 8 (since 1-4 exist from schema)
                // We can query guest IDs by document_id or insert bookings using subqueries to be safe
                System.out.println("Inserting bookings and billings...");
                
                // Sarah Jenkins
                stmt.execute("INSERT INTO bookings (guest_id, room_number, check_in_date, check_out_date, status) VALUES " +
                             "((SELECT guest_id FROM guests WHERE document_id='PASS-SARAH'), '402', '2023-10-14', '2023-10-18', 'Confirmed')");
                stmt.execute("INSERT INTO billings (booking_id, amount, payment_status) VALUES " +
                             "((SELECT booking_id FROM bookings WHERE guest_id=(SELECT guest_id FROM guests WHERE document_id='PASS-SARAH')), 1240.00, 'Paid')");

                // David Redson
                stmt.execute("INSERT INTO bookings (guest_id, room_number, check_in_date, check_out_date, status) VALUES " +
                             "((SELECT guest_id FROM guests WHERE document_id='PASS-DAVID'), '210', '2023-10-12', '2023-10-15', 'CheckedIn')");
                stmt.execute("INSERT INTO billings (booking_id, amount, payment_status) VALUES " +
                             "((SELECT booking_id FROM bookings WHERE guest_id=(SELECT guest_id FROM guests WHERE document_id='PASS-DAVID')), 645.00, 'Paid')");

                // Elena Rodriguez
                stmt.execute("INSERT INTO bookings (guest_id, room_number, check_in_date, check_out_date, status) VALUES " +
                             "((SELECT guest_id FROM guests WHERE document_id='PASS-ELENA'), '505', '2023-10-20', '2023-10-22', 'Pending')");
                stmt.execute("INSERT INTO billings (booking_id, amount, payment_status) VALUES " +
                             "((SELECT booking_id FROM bookings WHERE guest_id=(SELECT guest_id FROM guests WHERE document_id='PASS-ELENA')), 2100.00, 'Pending')");

                // Michael Kross
                stmt.execute("INSERT INTO bookings (guest_id, room_number, check_in_date, check_out_date, status) VALUES " +
                             "((SELECT guest_id FROM guests WHERE document_id='PASS-MICHAEL'), NULL, '2023-10-15', '2023-10-17', 'Cancelled')");
                stmt.execute("INSERT INTO billings (booking_id, amount, payment_status) VALUES " +
                             "((SELECT booking_id FROM bookings WHERE guest_id=(SELECT guest_id FROM guests WHERE document_id='PASS-MICHAEL')), 0.00, 'Refunded')");

                System.out.println("Data updates completed successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
