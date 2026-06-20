package controller;

import dao.GuestManagementDao;
import model.GuestManagementModel;
import view.GuestManagement;
import view.gest_dashbord;
import view.login;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Controller class to handle all logic and actions for Guest Management.
 * Follows clean MVC architecture.
 */
public class GuestManagementController {
    private final GuestManagement view;
    private final GuestManagementDao dao = new GuestManagementDao();
    private List<GuestManagementModel> currentGuestList;
    private Timer refreshTimer;

    public GuestManagementController(GuestManagement view) {
        this.view = view;
        initController();
    }

    private void initController() {
        // Load data and stats
        refreshData();

        // Start real-time update timer
        refreshTimer = new Timer(5000, e -> refreshDataSilent());
        refreshTimer.start();

        // Stop timer when window is closed natively
        view.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                stopTimer();
            }
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                stopTimer();
            }
        });

        // 1. Sidebar action listeners
        view.getBtnDashboard().addActionListener(e -> {
            stopTimer();
            new FrontdeskDeshboardControler();
            view.dispose();
        });
        view.getBtnGuest().addActionListener(e -> {
            // Already on guest management, just refresh
            refreshData();
        });
        view.getBtnBooking().addActionListener(e -> {
            stopTimer();
            new BookingController();
            view.dispose();
        });
        view.getBtnMealtime().addActionListener(e -> {
            stopTimer();
            new view.MealTime().setVisible(true);
            view.dispose();
        });
        view.getBtnBilling().addActionListener(e -> {
            stopTimer();
            new BillingController();
            view.dispose();
        });
        view.getBtnLogout().addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(
                view,
                "Are you sure you want to log out?",
                "Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (option == JOptionPane.YES_OPTION) {
                stopTimer();
                new LoginController();
                view.dispose();
            }
        });

        // 2. Search logic (filtering on key release)
        view.getTxtSearch().addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                String currentText = view.getTxtSearch().getText();
                if (currentText.equals("Search by name or rooms") || currentText.equals("🔍  Search by name or rooms")) {
                    view.getTxtSearch().setText("");
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (view.getTxtSearch().getText().trim().isEmpty()) {
                    view.getTxtSearch().setText("🔍  Search by name or rooms");
                }
            }
        });

        view.getTxtSearch().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String query = view.getTxtSearch().getText();
                if (query.equals("Search by name or rooms") || query.equals("🔍  Search by name or rooms")) {
                    query = "";
                }
                loadGuests(query);
            }
        });

        // 3. Delete Guest action
        view.getBtnDeleteGuest().addActionListener(e -> handleDeleteGuest());

        view.setVisible(true);
    }

    private void stopTimer() {
        if (refreshTimer != null && refreshTimer.isRunning()) {
            refreshTimer.stop();
        }
    }

    private void refreshData() {
        refreshDataSilent();
    }

    private void refreshDataSilent() {
        // Update stats
        view.getLblTotalGuests().setText(String.valueOf(dao.getTotalGuestsCount()));
        view.getLblCheckinToday().setText(String.valueOf(dao.getCheckinTodayCount()));
        view.getLblCheckoutToday().setText(String.valueOf(dao.getCheckoutTodayCount()));

        // Fetch query from search field
        String query = view.getTxtSearch().getText();
        if (query.equals("Search by name or rooms") || query.equals("🔍  Search by name or rooms")) {
            query = "";
        }
        
        // Load table maintaining selection
        int selectedRow = view.getTable().getSelectedRow();
        int selectedGuestId = -1;
        if (selectedRow != -1 && currentGuestList != null && selectedRow < currentGuestList.size()) {
            selectedGuestId = currentGuestList.get(selectedRow).getGuestId();
        }

        currentGuestList = dao.getAllGuests(query);
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);

        int rowToSelect = -1;
        for (int i = 0; i < currentGuestList.size(); i++) {
            GuestManagementModel guest = currentGuestList.get(i);
            model.addRow(new Object[]{
                guest.getName(),
                guest.getRoom(),
                guest.getStatus(),
                guest.getCheckIn() != null ? guest.getCheckIn().toString() : "N/A",
                guest.getCheckOut() != null ? guest.getCheckOut().toString() : "N/A"
            });
            if (guest.getGuestId() == selectedGuestId) {
                rowToSelect = i;
            }
        }
        
        if (rowToSelect != -1 && rowToSelect < view.getTable().getRowCount()) {
            view.getTable().setRowSelectionInterval(rowToSelect, rowToSelect);
        }
    }

    private void loadGuests(String query) {
        currentGuestList = dao.getAllGuests(query);
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);

        for (GuestManagementModel guest : currentGuestList) {
            model.addRow(new Object[]{
                guest.getName(),
                guest.getRoom(),
                guest.getStatus(),
                guest.getCheckIn() != null ? guest.getCheckIn().toString() : "N/A",
                guest.getCheckOut() != null ? guest.getCheckOut().toString() : "N/A"
            });
        }
    }

    private void handleDeleteGuest() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a guest from the table to delete.", "Select Guest", JOptionPane.WARNING_MESSAGE);
            return;
        }

        GuestManagementModel selectedGuest = currentGuestList.get(selectedRow);
        int option = JOptionPane.showConfirmDialog(
            view,
            "Are you sure you want to delete guest: " + selectedGuest.getName() + "?\nThis will also change room " + selectedGuest.getRoom() + " status back to Available.",
            "Delete Guest",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            try {
                int guestId = selectedGuest.getGuestId();
                String roomNo = selectedGuest.getRoom();
                if (dao.deleteGuest(guestId)) {
                    JOptionPane.showMessageDialog(view, "Guest deleted and room released successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Refresh table list
                    loadGuests("");
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to delete guest.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
