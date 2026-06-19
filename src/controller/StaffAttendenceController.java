package controller;

import dao.StaffAttendenceDao;
import model.StaffAttendenceModel;
import view.StaffAttendence;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Controller class to handle business logic for Staff Attendance view.
 */
public class StaffAttendenceController {
    private final StaffAttendence view;
    private final StaffAttendenceDao dao;

    public StaffAttendenceController() {
        this.view = new StaffAttendence();
        this.dao = new StaffAttendenceDao();
        initController();
    }

    private void initController() {
        // Set date to today and lock date selection to today only
        if (view.getDateChooser() != null) {
            view.getDateChooser().setDate(new java.util.Date());
            view.getDateChooser().setEnabled(false);
        }

        // Load today's attendance
        loadAttendance();

        // Save action
        if (view.getBtnSave() != null) {
            view.getBtnSave().addActionListener(e -> {
                DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
                int rowCount = model.getRowCount();
                java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
                boolean allSuccess = true;

                for (int i = 0; i < rowCount; i++) {
                    String staffId = (String) model.getValueAt(i, 0);
                    Boolean isPresent = (Boolean) model.getValueAt(i, 3);
                    if (isPresent == null) {
                        isPresent = false;
                    }
                    boolean success = dao.saveAttendance(staffId, today, isPresent);
                    if (!success) {
                        allSuccess = false;
                    }
                }

                if (allSuccess) {
                    JOptionPane.showMessageDialog(view, "Attendance saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    new StaffManagementController();
                    view.dispose();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to save some attendance records.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // Sidebar navigation
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
                new reportsController();
                view.dispose();
            });
        }
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

        // Display view
        view.setVisible(true);
    }

    private void loadAttendance() {
        if (view.getTable() != null) {
            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
            List<StaffAttendenceModel> list = dao.getAttendanceByDate(today);
            DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
            model.setRowCount(0);
            for (StaffAttendenceModel staff : list) {
                model.addRow(new Object[]{
                    staff.getStaffId(),
                    staff.getName(),
                    staff.getTotalPercentage() + "%",
                    staff.isPresentToday()
                });
            }
        }
    }
}