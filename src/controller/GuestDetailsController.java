package controller;

import view.Gust_Details;
import view.gest_dashbord;
import view.BookRoom;
import view.OrderFood;
import view.Feedback;
import view.loginpage;
import model.GuestDetails;
import dao.GuestDetailsDao;
import database.MySqlConnection;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.Date;

/**
 * Controller class for Guest Details page.
 * Follows clean MVC architecture.
 */
public class GuestDetailsController {
    private Gust_Details view;
    private GuestDetailsDao dao;

    public GuestDetailsController(Gust_Details view) {
        this.view = view;
        initDatabase();
        bindListeners();
    }

    private void initDatabase() {
        try {
            MySqlConnection mysql = new MySqlConnection();
            Connection conn = mysql.Openconnection();
            if (conn != null) {
                this.dao = new GuestDetailsDao(conn);
                this.dao.createTableIfNotExists();
            } else {
                System.out.println("Warning: Database connection could not be established.");
            }
        } catch (Exception e) {
            System.out.println("Exception initializing database in controller: " + e.getMessage());
        }
    }

    private void bindListeners() {
        // Sidebar navigation
        view.getBtnDashboard().addActionListener(e -> openDashboard());
        view.getBtnRoomBrowsing().addActionListener(e -> openRoomBrowsing());
        view.getBtnOrderFood().addActionListener(e -> openOrderFood());
        view.getBtnFeedback().addActionListener(e -> openFeedback());
        view.getBtnLogout().addActionListener(e -> logout());

        // Form Submission
        view.getBtnConfirmBooking().addActionListener(e -> handleConfirmBooking());
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
        new loginpage().setVisible(true);
        view.dispose();
    }

    private void handleConfirmBooking() {
        if (dao == null) {
            JOptionPane.showMessageDialog(view, "Database connection not available. Cannot save booking.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 1. Retrieve inputs
        String fullName = view.getTxtFullName().getText().trim();
        String phoneStr = view.getTxtPhoneNumber().getText().trim();
        String email = view.getTxtEmailAddress().getText().trim();
        String roomType = (String) view.getComboRoomType().getSelectedItem();
        String roomNoStr = view.getTxtRoomNo().getText().trim();
        String guestNoStr = view.getTxtGuestNo().getText().trim();

        java.util.Date checkInUtil = view.getDateChooserCheckIn().getDate();
        java.util.Date checkOutUtil = view.getDateChooserCheckOut().getDate();

        // 2. Validate inputs
        if (fullName.isEmpty() || phoneStr.isEmpty() || email.isEmpty() || roomNoStr.isEmpty() || guestNoStr.isEmpty() || checkInUtil == null || checkOutUtil == null) {
            JOptionPane.showMessageDialog(view, "Please fill in all the fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int phone, roomNo, guestNo;
        try {
            phone = Integer.parseInt(phoneStr);
            roomNo = Integer.parseInt(roomNoStr);
            guestNo = Integer.parseInt(guestNoStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Phone, Room No, and Guest No must be valid numbers.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date checkInSql = new Date(checkInUtil.getTime());
        Date checkOutSql = new Date(checkOutUtil.getTime());

        // 3. Create Model
        GuestDetails guest = new GuestDetails(fullName, phone, email, roomNo, guestNo, roomType, checkInSql, checkOutSql);

        // 4. Save via DAO
        boolean success = dao.insertGuest(guest);

        if (success) {
            JOptionPane.showMessageDialog(view, "Booking Confirmed Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Navigate back to guest dashboard on success
            openDashboard();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to confirm booking. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
