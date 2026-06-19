package controller;

import dao.BookingManagementDao;
import model.BookingModel;
import view.BookingManagement;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Controller class to handle business logic for Booking Management.
 * Manages search, filter drop-downs, table loading, and sidebar navigation.
 */
public class BookingController {
    private final BookingManagement view;
    private final BookingManagementDao dao;

    public BookingController() {
        this.view = new BookingManagement();
        this.dao = new BookingManagementDao();
        initController();
    }

    private void initController() {
        // Load default bookings list
        loadBookings();

        // 1. Hook up filters (Combo boxes)
        if (view.getComboRoomType() != null) {
            view.getComboRoomType().addActionListener(e -> loadBookings());
        }
        if (view.getComboStatus() != null) {
            view.getComboStatus().addActionListener(e -> loadBookings());
        }

        // 2. Hook up Search field
        if (view.getTxtSearch() != null) {
            view.getTxtSearch().addActionListener(e -> loadBookings());
            // Add key release listener for instant search responsiveness
            view.getTxtSearch().addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyReleased(java.awt.event.KeyEvent evt) {
                    loadBookings();
                }
            });
        }

        // 3. Sidebar navigation
        if (view.getBtnDashboard() != null) {
            view.getBtnDashboard().addActionListener(e -> {
                new FrontdeskDeshboardControler();
                view.dispose();
            });
        }
        if (view.getBtnGuest() != null) {
            view.getBtnGuest().addActionListener(e -> {
                new GuestManagementController(new view.GuestManagement());
                view.dispose();
            });
        }
        if (view.getBtnBooking() != null) {
            view.getBtnBooking().addActionListener(e -> {
                loadBookings();
            });
        }
        if (view.getBtnMealtime() != null) {
            view.getBtnMealtime().addActionListener(e -> {
                new view.OrderFood().setVisible(true);
            });
        }
        if (view.getBtnBilling() != null) {
            view.getBtnBilling().addActionListener(e -> {
                new BillingController();
                view.dispose();
            });
        }
        if (view.getBtnLogout() != null) {
            view.getBtnLogout().addActionListener(e -> {
                int option = javax.swing.JOptionPane.showConfirmDialog(
                    view,
                    "Are you sure you want to log out?",
                    "Logout",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE
                );
                if (option == javax.swing.JOptionPane.YES_OPTION) {
                    new LoginController();
                    view.dispose();
                }
            });
        }

        // Set view visible
        view.setVisible(true);
    }

    private void loadBookings() {
        String search = "";
        if (view.getTxtSearch() != null) {
            search = view.getTxtSearch().getText().trim();
        }

        String roomType = "All";
        if (view.getComboRoomType() != null && view.getComboRoomType().getSelectedItem() != null) {
            roomType = view.getComboRoomType().getSelectedItem().toString();
        }

        String status = "All";
        if (view.getComboStatus() != null && view.getComboStatus().getSelectedItem() != null) {
            status = view.getComboStatus().getSelectedItem().toString();
        }

        // Fetch filtered bookings from DAO (unlimited range for UI list representation)
        List<BookingModel> list = dao.getBookings(search, roomType, status, 0, 100);

        // Populate jTableBookings using getters
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        for (BookingModel b : list) {
            String stayPeriod = "";
            if (b.getCheckInDate() != null && b.getCheckOutDate() != null) {
                stayPeriod = sdf.format(b.getCheckInDate()) + " - " + sdf.format(b.getCheckOutDate());
            }

            model.addRow(new Object[]{
                b.getGuestName(),
                b.getRoomNumber(),
                b.getRoomType(),
                stayPeriod,
                b.getStatus(),
                "$" + String.format("%.2f", b.getTotalAmount())
            });
        }
    }
}
