package controller;

import view.reports;
import dao.ReportsDao;
import model.FeedbackModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Controller class to handle business logic for the Reports view.
 * Connects the sidebar buttons for navigation and populates database statistics.
 */
public class reportsController {
    private final reports view;
    private final ReportsDao dao;

    public reportsController() {
        this.view = new reports();
        this.dao = new ReportsDao();
        initController();
    }

    private void initController() {
        // Load statistics and feedback reviews
        loadStats();
        loadFeedback();

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
                new admindashboardController();
                view.dispose();
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
                // Already on reports view, do nothing
            });
        }

        // Show the view
        view.setVisible(true);
    }

    private void loadStats() {
        int totalBookings = dao.getTotalBookings();
        double attendance = dao.getOverallAttendancePercentage();
        int totalOffers = dao.getTotalOffers();

        if (view.getLblTotalBookings() != null) {
            view.getLblTotalBookings().setText(String.format("%02d", totalBookings));
        }
        if (view.getLblStaffAttendance() != null) {
            view.getLblStaffAttendance().setText(String.format("%.1f%%", attendance));
        }
        if (view.getLblTotalOffers() != null) {
            view.getLblTotalOffers().setText(String.format("%02d", totalOffers));
        }
    }

    private void loadFeedback() {
        if (view.getTblFeedback() != null) {
            List<FeedbackModel> list = dao.getAllFeedback();
            DefaultTableModel model = (DefaultTableModel) view.getTblFeedback().getModel();
            model.setRowCount(0);
            for (FeedbackModel fb : list) {
                model.addRow(new Object[]{
                    fb.getServiceRating(),
                    fb.getCleanlinessRating(),
                    fb.getFoodRating(),
                    fb.getReviewText()
                });
            }
        }
    }
}

