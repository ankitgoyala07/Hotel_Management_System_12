package controller;

import dao.admindashboardDAO;
import model.admindashboardModel;
import view.admindashboard;
import javax.swing.JOptionPane;

/**
 * Controller class to handle business logic for the Manager/Admin Dashboard view.
 * Exposes database stats via getters and binds action listeners.
 */
public class admindashboardController {
    private final admindashboard view;
    private final admindashboardDAO dashboardDAO;

    public admindashboardController() {
        this.view = new admindashboard();
        this.dashboardDAO = new admindashboardDAO();
        initController();
    }

    private void initController() {
        // Load statistics from the database
        admindashboardModel data = dashboardDAO.getDashboardData();

        // Populate labels in the view using public getters
        if (view.getLblCheckIn() != null) {
            view.getLblCheckIn().setText(String.valueOf(data.getTodayCheckIn()));
        }
        if (view.getLblCheckOut() != null) {
            view.getLblCheckOut().setText(String.valueOf(data.getTodayCheckOut()));
        }
        if (view.getLblTotal() != null) {
            view.getLblTotal().setText(String.valueOf(data.getTotalRooms()));
        }
        if (view.getLblAvailable() != null) {
            view.getLblAvailable().setText(String.valueOf(data.getAvailableRooms()));
        }
        if (view.getLblOccupied() != null) {
            view.getLblOccupied().setText(String.valueOf(data.getOccupiedRooms()));
        }
        if (view.getLblTotalStaffs() != null) {
            view.getLblTotalStaffs().setText(String.valueOf(data.getTotalStaffs()));
        }
        if (view.getLblTotalFrontdesk() != null) {
            view.getLblTotalFrontdesk().setText(String.valueOf(data.getFrontdeskStaff()));
        }
        if (view.getLblTotalChef() != null) {
            view.getLblTotalChef().setText(String.valueOf(data.getChefStaff()));
        }
        if (view.getLblTotalHelper() != null) {
            view.getLblTotalHelper().setText(String.valueOf(data.getHelperStaff()));
        }
        if (view.getLblTotalCleaner() != null) {
            view.getLblTotalCleaner().setText(String.valueOf(data.getCleanerStaff()));
        }

        // Set up Logout listener using view's public getter
        if (view.getBtnLogout() != null) {
            view.getBtnLogout().addActionListener(e -> {
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
            });
        }

        // Set up navigation listeners
        if (view.getBtnDashboard() != null) {
            view.getBtnDashboard().addActionListener(e -> {
                // Already on dashboard
            });
        }
        if (view.getBtnRooms() != null) {
            view.getBtnRooms().addActionListener(e -> {
                new roommanagementController();
                view.dispose();
            });
        }
        if (view.getBtnDiscount() != null) {
            view.getBtnDiscount().addActionListener(e -> {
                new DiscountController();
                view.dispose();
            });
        }
        if (view.getBtnStaffs() != null) {
            view.getBtnStaffs().addActionListener(e -> {
                new StaffManagementController();
                view.dispose();
            });
        }
        if (view.getBtnSystemSetting() != null) {
            view.getBtnSystemSetting().addActionListener(e -> {
                new systemController();
                view.dispose();
            });
        }
        if (view.getBtnReports() != null) {
            view.getBtnReports().addActionListener(e -> {
                new reportsController();
                view.dispose();
            });
        }

        // Silently make the dashboard visible without showing popups
        view.setVisible(true);
    }
}
