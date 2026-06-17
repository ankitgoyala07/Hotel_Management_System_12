package controller;

import dao.BookingDAO;
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
    private final BookingDAO dao;

    public BookingController() {
        this.view = new BookingManagement();
        this.dao = new BookingDAO();
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
                new admindashboardController();
                view.dispose();
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
                new LoginController();
                view.dispose();
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

        String roomType = "All Types";
        if (view.getComboRoomType() != null && view.getComboRoomType().getSelectedItem() != null) {
            roomType = view.getComboRoomType().getSelectedItem().toString();
        }

        String status = "All Status";
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
