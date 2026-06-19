package controller;

import view.Gust_Details;
import view.gest_dashbord;
import view.BookRoom;
import view.OrderFood;
import view.Feedback;
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
        preFillRoomType();
    }

    private void preFillRoomType() {
        if (view.getComboRoomType() != null) {
            String rt = view.getRoomType();
            if (rt == null || rt.trim().isEmpty()) {
                rt = "Single Bed Room";
            }
            for (int i = 0; i < view.getComboRoomType().getItemCount(); i++) {
                String item = view.getComboRoomType().getItemAt(i);
                if (item.equalsIgnoreCase(rt) 
                    || (rt.equalsIgnoreCase("Single bed") && item.equalsIgnoreCase("Single Bed Room"))
                    || (rt.equalsIgnoreCase("Double bed") && item.equalsIgnoreCase("Double Bed Room"))
                    || (rt.equalsIgnoreCase("Single") && item.equalsIgnoreCase("Single Bed Room"))
                    || (rt.equalsIgnoreCase("Double") && item.equalsIgnoreCase("Double Bed Room"))) {
                    view.getComboRoomType().setSelectedIndex(i);
                    break;
                }
            }
        }
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
        new Feedback().setVisible(true);
        view.dispose();
    }

    private void logout() {
        new LoginController();
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
        String homeAddress = view.getTxtHomeAddress().getText().trim();
        String roomType = "";
        if (view.getComboRoomType() != null && view.getComboRoomType().getSelectedItem() != null) {
            roomType = view.getComboRoomType().getSelectedItem().toString().trim();
        }
        if (roomType.isEmpty()) {
            roomType = view.getRoomType();
        }
        String dealCode = view.getTxtDiscountDeal().getText().trim();

        java.util.Date checkInUtil = view.getDateChooserCheckIn().getDate();
        java.util.Date checkOutUtil = view.getDateChooserCheckOut().getDate();

        // 2. Validate inputs
        if (fullName.isEmpty() || phoneStr.isEmpty() || email.isEmpty() || homeAddress.isEmpty() || checkInUtil == null || checkOutUtil == null) {
            JOptionPane.showMessageDialog(view, "Please fill in all the required fields (Full Name, Phone, Email, Home Address, Dates).", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (roomType == null || roomType.trim().isEmpty()) {
            roomType = "Single Bed Room"; // Default fallback
        }

        // Dynamically assign an available room
        int roomNo = dao.findAvailableRoomNo(roomType);
        if (roomNo == -1) {
            JOptionPane.showMessageDialog(view, "No available rooms of type '" + roomType + "' found in the database. Please browse other rooms.", "Rooms Full", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date checkInSql = new Date(checkInUtil.getTime());
        Date checkOutSql = new Date(checkOutUtil.getTime());

        // 3. Validate Deal Code if provided
        if (!dealCode.isEmpty()) {
            dao.DiscountDao discountDao = new dao.DiscountDao();
            model.DiscountModel deal = discountDao.getDiscountByCode(dealCode);
            if (deal == null) {
                JOptionPane.showMessageDialog(view, "Invalid Deal Code! Booking aborted.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!"ongoing".equalsIgnoreCase(deal.getStatus())) {
                JOptionPane.showMessageDialog(view, "This deal is no longer active (Status: " + deal.getStatus() + "). Booking aborted.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Decrement reservations in DB
            boolean decremented = discountDao.decrementReservations(dealCode);
            if (!decremented) {
                JOptionPane.showMessageDialog(view, "Failed to apply deal code. Booking aborted.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // 4. Create Model (guest_no defaults to 1 since UI doesn't collect guest count)
        GuestDetails guest = new GuestDetails(fullName, phoneStr, email, homeAddress, roomNo, 1, roomType, checkInSql, checkOutSql, dealCode);

        // 5. Save via DAO
        boolean success = dao.insertGuest(guest);

        if (success) {
            // Update room status in the database to Occupied
            dao.updateRoomStatus(roomNo, "Occupied");
            JOptionPane.showMessageDialog(view, "Booking Confirmed. Your room number is: " + roomNo, "Success", JOptionPane.INFORMATION_MESSAGE);
            // Navigate back to guest dashboard on success
            openDashboard();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to confirm booking. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
