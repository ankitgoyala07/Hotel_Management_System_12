package dao;

import database.MySqlConnection;
import model.BillingModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Data Access Object for handling billing-related database query operations.
 */
public class BillingDao {
    private final MySqlConnection mysql = new MySqlConnection();

    /**
     * Retrieves dynamic billing information based on a room number.
     * Queries guest stay dates, room type, and active orders to compute the total invoice.
     *
     * @param roomNumber the identifier of the room to calculate the bill for
     * @return BillingModel initialized with database values or fallbacks
     */
    public BillingModel getBillingForRoom(String roomNumber) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return new BillingModel("", roomNumber, "", 0, 0.0, 0.0, 0.0, "");
        }

        try {
            int roomNum = -1;
            try {
                roomNum = Integer.parseInt(roomNumber.trim());
            } catch (Exception e) {}

            String guestId = "";
            String stayPeriod = "";
            int nights = 0;
            double roomRate = 0.0;
            String roomType = "";
            double foodOrders = 0.0;
            double roomService = 0.0;

            // 1. Query guest stay info
            String guestSql = "SELECT guest_id, full_name, room_type, check_in_date, check_out_date "
                            + "FROM guest_details WHERE room_no = ? ORDER BY guest_id DESC LIMIT 1";
            try (PreparedStatement pstm = conn.prepareStatement(guestSql)) {
                pstm.setInt(1, roomNum);
                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        guestId = String.valueOf(rs.getInt("guest_id"));
                        roomType = rs.getString("room_type");
                        java.sql.Date checkIn = rs.getDate("check_in_date");
                        java.sql.Date checkOut = rs.getDate("check_out_date");
                        if (checkIn != null && checkOut != null) {
                            stayPeriod = checkIn.toString() + " to " + checkOut.toString();
                            long diff = checkOut.getTime() - checkIn.getTime();
                            nights = (int) java.util.concurrent.TimeUnit.DAYS.convert(diff, java.util.concurrent.TimeUnit.MILLISECONDS);
                            if (nights <= 0) {
                                nights = 1;
                            }
                        }
                    }
                }
            }

            // If a valid room type was loaded, retrieve the rate
            if (!roomType.isEmpty()) {
                // 2. Query room rate
                String roomSql = "SELECT price_per_night FROM rooms WHERE room_number = ?";
                try (PreparedStatement pstm = conn.prepareStatement(roomSql)) {
                    pstm.setString(1, roomNumber);
                    try (ResultSet rs = pstm.executeQuery()) {
                        if (rs.next()) {
                            roomRate = rs.getDouble("price_per_night");
                        }
                    }
                }
                if (roomRate <= 0.0) {
                    if (roomType.equalsIgnoreCase("Single")) roomRate = 80.00;
                    else if (roomType.equalsIgnoreCase("Double")) roomRate = 120.00;
                    else if (roomType.equalsIgnoreCase("Suite")) roomRate = 250.00;
                    else if (roomType.equalsIgnoreCase("Deluxe")) roomRate = 350.00;
                    else if (roomType.equalsIgnoreCase("Penthouse")) roomRate = 800.00;
                }

                // 3. Query food orders sum
                String foodSql = "SELECT SUM(price * quantity) FROM food_orders WHERE room_no = ?";
                try (PreparedStatement pstm = conn.prepareStatement(foodSql)) {
                    pstm.setInt(1, roomNum);
                    try (ResultSet rs = pstm.executeQuery()) {
                        if (rs.next()) {
                            foodOrders = rs.getDouble(1);
                        }
                    }
                }

                // 4. Query room service sum
                String serviceSql = "SELECT service_type FROM room_service WHERE room_no = ?";
                try (PreparedStatement pstm = conn.prepareStatement(serviceSql)) {
                    pstm.setInt(1, roomNum);
                    try (ResultSet rs = pstm.executeQuery()) {
                        while (rs.next()) {
                            String serviceType = rs.getString("service_type");
                            if (serviceType != null) {
                                serviceType = serviceType.trim();
                                if (serviceType.equalsIgnoreCase("Room Cleaning")) {
                                    roomService += 5.00;
                                } else if (serviceType.equalsIgnoreCase("Extra Blanket")) {
                                    roomService += 2.00;
                                } else if (serviceType.equalsIgnoreCase("Laundry")) {
                                    roomService += 5.00;
                                } else if (serviceType.equalsIgnoreCase("Gym AND Jumba") || serviceType.equalsIgnoreCase("Gym & Jumba")) {
                                    roomService += 10.00;
                                } else if (serviceType.equalsIgnoreCase("Infinity Pool")) {
                                    roomService += 8.00;
                                }
                            }
                        }
                    }
                }
            }

            return new BillingModel(guestId, roomNumber, stayPeriod, nights, roomRate, roomService, foodOrders, roomType);
        } catch (Exception e) {
            System.out.println("Error getting billing from database: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }

        return new BillingModel("", roomNumber, "", 0, 0.0, 0.0, 0.0, "");
    }

    /**
     * Retrieves dynamic billing information based on a guest ID.
     *
     * @param guestIdStr the unique guest ID
     * @return BillingModel initialized with database values or fallbacks
     */
    public BillingModel getBillingForGuest(String guestIdStr) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return new BillingModel(guestIdStr, "", "", 0, 0.0, 0.0, 0.0, "");
        }

        try {
            int guestId = -1;
            try {
                guestId = Integer.parseInt(guestIdStr.trim());
            } catch (Exception e) {}

            String roomNumber = "";
            String stayPeriod = "";
            int nights = 0;
            double roomRate = 0.0;
            String roomType = "";
            double foodOrders = 0.0;
            double roomService = 0.0;

            // 1. Query guest stay info by guest_id
            String guestSql = "SELECT guest_id, room_no, room_type, check_in_date, check_out_date "
                            + "FROM guest_details WHERE guest_id = ? LIMIT 1";
            try (PreparedStatement pstm = conn.prepareStatement(guestSql)) {
                pstm.setInt(1, guestId);
                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        roomNumber = String.valueOf(rs.getInt("room_no"));
                        roomType = rs.getString("room_type");
                        java.sql.Date checkIn = rs.getDate("check_in_date");
                        java.sql.Date checkOut = rs.getDate("check_out_date");
                        if (checkIn != null && checkOut != null) {
                            stayPeriod = checkIn.toString() + " to " + checkOut.toString();
                            long diff = checkOut.getTime() - checkIn.getTime();
                            nights = (int) java.util.concurrent.TimeUnit.DAYS.convert(diff, java.util.concurrent.TimeUnit.MILLISECONDS);
                            if (nights <= 0) {
                                nights = 1;
                            }
                        }
                    }
                }
            }

            // If a valid room was found, load the other details
            if (!roomNumber.isEmpty()) {
                int roomNum = -1;
                try {
                    roomNum = Integer.parseInt(roomNumber.trim());
                } catch (Exception e) {}

                // 2. Query room rate
                String roomSql = "SELECT price_per_night FROM rooms WHERE room_number = ?";
                try (PreparedStatement pstm = conn.prepareStatement(roomSql)) {
                    pstm.setString(1, roomNumber);
                    try (ResultSet rs = pstm.executeQuery()) {
                        if (rs.next()) {
                            roomRate = rs.getDouble("price_per_night");
                        }
                    }
                }
                if (roomRate <= 0.0) {
                    if (roomType != null) {
                        if (roomType.equalsIgnoreCase("Single")) roomRate = 80.00;
                        else if (roomType.equalsIgnoreCase("Double")) roomRate = 120.00;
                        else if (roomType.equalsIgnoreCase("Suite")) roomRate = 250.00;
                        else if (roomType.equalsIgnoreCase("Deluxe")) roomRate = 350.00;
                        else if (roomType.equalsIgnoreCase("Penthouse")) roomRate = 800.00;
                    }
                }

                // 3. Query food orders sum
                String foodSql = "SELECT SUM(price * quantity) FROM food_orders WHERE room_no = ?";
                try (PreparedStatement pstm = conn.prepareStatement(foodSql)) {
                    pstm.setInt(1, roomNum);
                    try (ResultSet rs = pstm.executeQuery()) {
                        if (rs.next()) {
                            foodOrders = rs.getDouble(1);
                        }
                    }
                }

                // 4. Query room service sum
                String serviceSql = "SELECT service_type FROM room_service WHERE room_no = ?";
                try (PreparedStatement pstm = conn.prepareStatement(serviceSql)) {
                    pstm.setInt(1, roomNum);
                    try (ResultSet rs = pstm.executeQuery()) {
                        while (rs.next()) {
                            String serviceType = rs.getString("service_type");
                            if (serviceType != null) {
                                serviceType = serviceType.trim();
                                if (serviceType.equalsIgnoreCase("Room Cleaning")) {
                                    roomService += 5.00;
                                } else if (serviceType.equalsIgnoreCase("Extra Blanket")) {
                                    roomService += 2.00;
                                } else if (serviceType.equalsIgnoreCase("Laundry")) {
                                    roomService += 5.00;
                                } else if (serviceType.equalsIgnoreCase("Gym AND Jumba") || serviceType.equalsIgnoreCase("Gym & Jumba")) {
                                    roomService += 10.00;
                                } else if (serviceType.equalsIgnoreCase("Infinity Pool")) {
                                    roomService += 8.00;
                                }
                            }
                        }
                    }
                }
            }

            return new BillingModel(guestIdStr, roomNumber, stayPeriod, nights, roomRate, roomService, foodOrders, roomType);
        } catch (Exception e) {
            System.out.println("Error getting billing for guest from database: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }

        return new BillingModel(guestIdStr, "", "", 0, 0.0, 0.0, 0.0, "");
    }

    /**
     * Checks out the room, updating rooms status to 'Available', guest_details to 'Checked Out',
     * and bookings to 'CheckedOut'.
     *
     * @param roomNumber the room number to checkout
     * @return true if successful
     */
    public boolean checkoutRoom(String roomNumber) {
        Connection conn = mysql.Openconnection();
        if (conn == null) return false;

        try {
            conn.setAutoCommit(false);

            // 1. Update rooms status to 'Available'
            String roomSql = "UPDATE rooms SET status = 'Available' WHERE room_number = ?";
            try (PreparedStatement pstm = conn.prepareStatement(roomSql)) {
                pstm.setString(1, roomNumber);
                pstm.executeUpdate();
            }

            int roomNum = -1;
            try {
                roomNum = Integer.parseInt(roomNumber.trim());
            } catch (Exception e) {}

            // 2. Get checked in guest_id
            int guestId = -1;
            String guestGetSql = "SELECT guest_id FROM guest_details WHERE room_no = ? AND status = 'Checked In'";
            try (PreparedStatement pstm = conn.prepareStatement(guestGetSql)) {
                pstm.setInt(1, roomNum);
                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        guestId = rs.getInt("guest_id");
                    }
                }
            }

            // 3. Update guest_details status to 'Checked Out'
            String guestUpdateSql = "UPDATE guest_details SET status = 'Checked Out' WHERE room_no = ? AND status = 'Checked In'";
            try (PreparedStatement pstm = conn.prepareStatement(guestUpdateSql)) {
                pstm.setInt(1, roomNum);
                pstm.executeUpdate();
            }

            // 4. Update bookings status to 'CheckedOut'
            if (guestId != -1) {
                String bookingSql = "UPDATE bookings SET status = 'CheckedOut' WHERE guest_id = ? AND status = 'CheckedIn'";
                try (PreparedStatement pstm = conn.prepareStatement(bookingSql)) {
                    pstm.setInt(1, guestId);
                    pstm.executeUpdate();
                }
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {}
            System.out.println("Error during checkout: " + e.getMessage());
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }
}
