package controller;

import dao.StaffManagementDao;
import model.StaffManagementModel;
import view.StaffManagement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Controller class to handle business logic for Staff Management view.
 * Manages database table loading and sidebar navigation.
 */
public class StaffManagementController {
    private final StaffManagement view;
    private final StaffManagementDao dao;

    public StaffManagementController() {
        this.view = new StaffManagement();
        this.dao = new StaffManagementDao();
        initController();
    }

    private void initController() {
        // Load staff from database
        loadStaff();

        // Hook up sidebar navigation buttons
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
                // Already on Staffs
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
        if (view.getBtnAddStaff() != null) {
            view.getBtnAddStaff().addActionListener(e -> {
                new AddStaffController();
                view.dispose();
            });
        }

        if (view.getBtnAttendance() != null) {
            view.getBtnAttendance().addActionListener(e -> {
                new StaffAttendenceController();
                view.dispose();
            });
        }

        if (view.getBtnDelete() != null) {
            view.getBtnDelete().addActionListener(e -> {
                int selectedRow = view.getTable().getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(view, "Please select a staff member from the table to delete.", "Delete Staff", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String staffId = view.getTable().getValueAt(selectedRow, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete staff ID: " + staffId + "?", "Delete Staff", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = dao.deleteStaff(staffId);
                    if (success) {
                        JOptionPane.showMessageDialog(view, "Staff deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadStaff();
                    } else {
                        JOptionPane.showMessageDialog(view, "Failed to delete staff member.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

        // Display view
        view.setVisible(true);
    }

    private void loadStaff() {
        if (view.getTable() != null) {
            List<StaffManagementModel> list = dao.getAllStaff();
            DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
            model.setRowCount(0);
            for (StaffManagementModel s : list) {
                model.addRow(new Object[]{
                    s.getStaffId(),
                    s.getName(),
                    s.getPhone(),
                    s.getEmail(),
                    s.getAddress(),
                    s.getRole(),
                    s.getShift()
                });
            }
        }
    }
}