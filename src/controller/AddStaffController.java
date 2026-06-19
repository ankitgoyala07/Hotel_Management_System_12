package controller;

import dao.AddStaffDao;
import model.AddStaffModel;
import view.AddStaff;
import javax.swing.JOptionPane;

/**
 * Controller class to handle business logic for Add Staff view.
 */
public class AddStaffController {
    private final AddStaff view;
    private final AddStaffDao dao;

    public AddStaffController() {
        this.view = new AddStaff();
        this.dao = new AddStaffDao();
        initController();
    }

    private void initController() {
        // Reset action
        if (view.getBtnReset() != null) {
            view.getBtnReset().addActionListener(e -> {
                view.getTxtStaffId().setText("");
                view.getTxtName().setText("");
                view.getTxtPhone().setText("");
                view.getTxtEmail().setText("");
                view.getTxtAddress().setText("");
                view.getTxtRole().setText("");
                view.getTxtShift().setText("");
            });
        }

        // Save action
        if (view.getBtnSave() != null) {
            view.getBtnSave().addActionListener(e -> {
                String staffId = view.getTxtStaffId().getText().trim();
                String name = view.getTxtName().getText().trim();
                String phone = view.getTxtPhone().getText().trim();
                String email = view.getTxtEmail().getText().trim();
                String address = view.getTxtAddress().getText().trim();
                String role = view.getTxtRole().getText().trim();
                String shift = view.getTxtShift().getText().trim();

                if (staffId.isEmpty() || name.isEmpty() || phone.isEmpty() || email.isEmpty() ||
                    address.isEmpty() || role.isEmpty() || shift.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (dao.staffExists(staffId)) {
                    JOptionPane.showMessageDialog(view, "Staff ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                AddStaffModel staff = new AddStaffModel(staffId, name, phone, email, address, role, shift);
                boolean success = dao.insertStaff(staff);
                if (success) {
                    JOptionPane.showMessageDialog(view, "Staff added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    new StaffManagementController();
                    view.dispose();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to add staff member.", "Error", JOptionPane.ERROR_MESSAGE);
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
}
