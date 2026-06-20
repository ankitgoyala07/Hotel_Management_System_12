package controller;

import view.Roomservice;
import view.gest_dashbord;
import view.BookRoom;
import view.OrderFood;
import view.Feedback;
import model.RoomServiceModel;
import dao.RoomServiceDao;
import database.MySqlConnection;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for Room Service page.
 * Follows clean MVC architecture.
 */
public class RoomServiceController {
    private final Roomservice view;
    private RoomServiceDao dao;

    // Helper class to represent selected services in the cart
    private static class SelectedService {
        String name;
        double price;
        SelectedService(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }

    private final List<SelectedService> currentOrder = new ArrayList<>();

    public RoomServiceController(Roomservice view) {
        this.view = view;
        initDatabase();
        loadRoomNo();
        bindListeners();
        updateOrderUI();
    }

    private void initDatabase() {
        try {
            MySqlConnection mysql = new MySqlConnection();
            Connection conn = mysql.Openconnection();
            if (conn != null) {
                this.dao = new RoomServiceDao(conn);
                this.dao.createTableIfNotExists();
            } else {
                System.out.println("Warning: Database connection could not be established.");
            }
        } catch (Exception e) {
            System.out.println("Exception initializing database in controller: " + e.getMessage());
        }
    }

    // Auto-load current guest's room number if booked
    private void loadRoomNo() {
        String username = LoginController.loggedInUsername;
        if (username != null) {
            try {
                MySqlConnection mysql = new MySqlConnection();
                try (Connection conn = mysql.Openconnection()) {
                    if (conn != null) {
                        String userSql = "SELECT email, phone FROM users WHERE username = ?";
                        String email = null;
                        String phone = null;
                        try (PreparedStatement ps = conn.prepareStatement(userSql)) {
                            ps.setString(1, username);
                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()) {
                                    email = rs.getString("email");
                                    phone = rs.getString("phone");
                                }
                            }
                        }
                        if (email != null || phone != null) {
                           String guestSql = "SELECT room_no FROM guest_details WHERE email_address = ? OR phone_number = ? ORDER BY guest_id DESC LIMIT 1";
                           try (PreparedStatement ps = conn.prepareStatement(guestSql)) {
                               ps.setString(1, email);
                               ps.setString(2, phone);
                               try (ResultSet rs = ps.executeQuery()) {
                                   if (rs.next()) {
                                       view.getTxtRoomNo().setText(String.valueOf(rs.getInt("room_no")));
                                   }
                               }
                           }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error loading room number in room service: " + e.getMessage());
            }
        }
    }

    private void bindListeners() {
        // Sidebar navigation links
        view.getBtnDashboard().addActionListener(e -> openDashboard());
        view.getBtnRoomBrowsing().addActionListener(e -> openRoomBrowsing());
        view.getBtnOrderFood().addActionListener(e -> openOrderFood());
        view.getBtnFeedback().addActionListener(e -> openFeedback());
        view.getBtnLogout().addActionListener(e -> logout());

        // Add service buttons
        view.getBtnAdd1().addActionListener(e -> addService("Room Cleaning", 5.00));
        view.getBtnAdd2().addActionListener(e -> addService("Extra Blanket", 2.00));
        view.getBtnAdd3().addActionListener(e -> addService("Laundry", 5.00));
        view.getBtnAdd4().addActionListener(e -> addService("Gym AND Jumba", 10.00));
        view.getBtnAdd5().addActionListener(e -> addService("Infinity Pool", 8.00));

        // Remove buttons
        view.getBtnItem1Remove().addActionListener(e -> removeRow1());
        view.getBtnItem2Remove().addActionListener(e -> removeRow2());

        // Submit Request
        view.getBtnSubmitRequest().addActionListener(e -> handleSubmitRequest());
    }

    private void addService(String name, double price) {
        currentOrder.add(new SelectedService(name, price));
        updateOrderUI();
    }

    private void removeRow1() {
        if (!currentOrder.isEmpty()) {
            currentOrder.remove(currentOrder.size() - 1);
            updateOrderUI();
        }
    }

    private void removeRow2() {
        if (currentOrder.size() > 1) {
            currentOrder.remove(currentOrder.size() - 2);
            updateOrderUI();
        }
    }

    private void updateOrderUI() {
        int totalCount = currentOrder.size();
        view.getLblOrderBadge().setText(String.valueOf(totalCount));

        double totalAmount = 0.0;
        for (SelectedService s : currentOrder) {
            totalAmount += s.price;
        }
        view.getLblTotalValue().setText(String.format("$%.2f", totalAmount));

        // Show last added item in Row 1
        if (totalCount > 0) {
            SelectedService item1 = currentOrder.get(totalCount - 1);
            view.getLblOrderItem1Name().setText(item1.name);
            view.getLblOrderItem1Price().setText(String.format("$%.2f", item1.price));
            setRow1Visible(true);
        } else {
            setRow1Visible(false);
        }

        // Show second to last added item in Row 2
        if (totalCount > 1) {
            SelectedService item2 = currentOrder.get(totalCount - 2);
            view.getLblOrderItem2Name().setText(item2.name);
            view.getLblOrderItem2Price().setText(String.format("$%.2f", item2.price));
            setRow2Visible(true);
        } else {
            setRow2Visible(false);
        }
    }

    private void setRow1Visible(boolean visible) {
        view.getLblOrderItem1Name().setVisible(visible);
        view.getLblOrderItem1Price().setVisible(visible);
        view.getBtnItem1Remove().setVisible(visible);
    }

    private void setRow2Visible(boolean visible) {
        view.getLblOrderItem2Name().setVisible(visible);
        view.getLblOrderItem2Price().setVisible(visible);
        view.getBtnItem2Remove().setVisible(visible);
    }

    private void openDashboard() {
        new GuestDashboardController(new gest_dashbord());
        view.dispose();
    }

    private void openRoomBrowsing() {
        BookRoom roomView = new BookRoom();
        new BookRoomController(roomView);
        roomView.setVisible(true);
        view.dispose();
    }

    private void openOrderFood() {
        new OrderFood().setVisible(true);
        view.dispose();
    }

    private void openFeedback() {
        Feedback fbView = new Feedback();
        new FeedbackController(fbView);
        fbView.setVisible(true);
        view.dispose();
    }

    private void logout() {
        int option = JOptionPane.showConfirmDialog(
            view,
            "Are you sure want to logout?",
            "Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        if (option == JOptionPane.YES_OPTION) {
            new LoginController();
            view.dispose();
        }
    }

    private void handleSubmitRequest() {
        if (dao == null) {
            JOptionPane.showMessageDialog(view, "Database connection not available. Cannot submit request.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (currentOrder.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Your order is empty! Please select at least one service.", "Empty Order", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String roomNoStr = view.getTxtRoomNo().getText().trim();
        if (roomNoStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter your Room Number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidRoomNo(roomNoStr)) {
            JOptionPane.showMessageDialog(view, "Invalid room number! You can only enter your booked room number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int roomNo;
        try {
            roomNo = Integer.parseInt(roomNoStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Room Number must be a valid integer.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Save all ordered services in DB
        boolean allSuccess = true;
        for (SelectedService service : currentOrder) {
            RoomServiceModel request = new RoomServiceModel(service.name, roomNo, "Ordered via Room Service Menu");
            boolean success = dao.insertRequest(request);
            if (!success) {
                allSuccess = false;
            }
        }

        if (allSuccess) {
            JOptionPane.showMessageDialog(view, "Your room service requests have been submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            currentOrder.clear();
            updateOrderUI();
            openDashboard();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to submit some or all requests. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidRoomNo(String enteredRoomNoStr) {
        String username = LoginController.loggedInUsername;
        if (username == null) {
            return false;
        }

        int enteredRoomNo;
        try {
            enteredRoomNo = Integer.parseInt(enteredRoomNoStr.trim());
        } catch (NumberFormatException e) {
            return false;
        }

        MySqlConnection mysql = new MySqlConnection();
        try (Connection conn = mysql.Openconnection()) {
            if (conn == null) return false;

            // 1. Get email and phone from users table
            String email = null;
            String phone = null;
            String userSql = "SELECT email, phone FROM users WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(userSql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        email = rs.getString("email");
                        phone = rs.getString("phone");
                    }
                }
            }

            if (email != null || phone != null) {
                // 2. Get room number from guest_details table
                String guestSql = "SELECT room_no FROM guest_details WHERE email_address = ? OR phone_number = ? ORDER BY guest_id DESC LIMIT 1";
                try (PreparedStatement ps = conn.prepareStatement(guestSql)) {
                    ps.setString(1, email);
                    ps.setString(2, phone);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            int bookedRoomNo = rs.getInt("room_no");
                            return enteredRoomNo == bookedRoomNo;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error validating room number in room service: " + e.getMessage());
        }
        return false;
    }
}
