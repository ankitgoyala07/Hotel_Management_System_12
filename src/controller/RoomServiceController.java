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

/**
 * Controller class for Room Service page.
 * Follows clean MVC architecture.
 */
public class RoomServiceController {
    private Roomservice view;
    private RoomServiceDao dao;

    public RoomServiceController(Roomservice view) {
        this.view = view;
        initDatabase();
        bindListeners();
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

    private void bindListeners() {
        // Sidebar navigation links
        view.getBtnDashboard().addActionListener(e -> openDashboard());
        view.getBtnRoomBrowsing().addActionListener(e -> openRoomBrowsing());
        view.getBtnOrderFood().addActionListener(e -> openOrderFood());
        view.getBtnFeedback().addActionListener(e -> openFeedback());
        view.getBtnLogout().addActionListener(e -> logout());

        // Submit Request
        view.getBtnSubmitRequest().addActionListener(e -> handleSubmitRequest());
    }

    private void openDashboard() {
        new gest_dashbord().setVisible(true);
        view.dispose();
    }

    private void openRoomBrowsing() {
        new BookRoom().setVisible(true);
        view.dispose();
    }

    private void openOrderFood() {
        new OrderFood().setVisible(true);
        view.dispose();
    }

    private void openFeedback() {
        new Feedback().setVisible(true);
        view.dispose();
    }

    private void logout() {
        new LoginController();
        view.dispose();
    }

    private void handleSubmitRequest() {
        if (dao == null) {
            JOptionPane.showMessageDialog(view, "Database connection not available. Cannot submit request.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 1. Retrieve inputs
        String serviceType = (String) view.getComboServiceType().getSelectedItem();
        String roomNoStr = view.getTxtRoomNo().getText().trim();
        String instructions = view.getTxtInstructions().getText().trim();

        // 2. Validate inputs not empty
        if (roomNoStr.isEmpty() || instructions.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter your Room Number and request details.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Parse and validate room number
        int roomNo;
        try {
            roomNo = Integer.parseInt(roomNoStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Room Number must be a valid integer.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4. Create Model
        RoomServiceModel request = new RoomServiceModel(serviceType, roomNo, instructions);

        // 5. Save via DAO
        boolean success = dao.insertRequest(request);

        if (success) {
            JOptionPane.showMessageDialog(view, "Your request for " + serviceType + " in room " + roomNo + " has been submitted. Our staff is on the way!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear fields
            view.getTxtRoomNo().setText("");
            view.getTxtInstructions().setText("");

            // Navigate back to dashboard on success
            openDashboard();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to submit request. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
