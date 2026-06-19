package controller;

import dao.systemDao;
import model.systemModel;
import view.SystemSetting;
import javax.swing.JOptionPane;

/**
 * Controller class to handle business logic for System Settings view.
 * Manages loading system configurations and sidebar navigation.
 */
public class systemController {
    private final SystemSetting view;
    private final systemDao dao;

    public systemController() {
        this.view = new SystemSetting();
        this.dao = new systemDao();
        initController();
    }

    private void initController() {
        // Load settings from database
        loadSettings();

        // Make all fields non-editable by default on screen load
        setEditableFields(false);

        // Hook up edit button
        if (view.getBtnEdit() != null) {
            view.getBtnEdit().addActionListener(e -> setEditableFields(true));
        }

        // Hook up save button
        if (view.getBtnSave() != null) {
            view.getBtnSave().addActionListener(e -> {
                String hotelName = view.getTxtHotelName().getText().trim();
                String hotelId = view.getTxtHotelId().getText().trim();
                String address = view.getTxtAddress().getText().trim();
                String panNumber = view.getTxtPanNumber().getText().trim();
                String owner = view.getTxtOwner().getText().trim();
                String quickNote = view.getTxtQuickNote().getText().trim();
                String phone = view.getTxtPhone().getText().trim();
                String website = view.getTxtWebsite().getText().trim();

                if (hotelName.isEmpty() || hotelId.isEmpty() || address.isEmpty() ||
                    panNumber.isEmpty() || owner.isEmpty() || quickNote.isEmpty() ||
                    phone.isEmpty() || website.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                systemModel model = new systemModel(hotelName, hotelId, address, panNumber, owner, quickNote, phone, website);
                boolean success = dao.updateSystemSettings(model);
                if (success) {
                    JOptionPane.showMessageDialog(view, "System settings updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    setEditableFields(false);
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update system settings.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

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
                new StaffManagementController();
                view.dispose();
            });
        }
        if (view.getBtnSystemSetting() != null) {
            view.getBtnSystemSetting().addActionListener(e -> {
                // Already on System Settings
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

    private void setEditableFields(boolean editable) {
        if (view.getTxtHotelName() != null) view.getTxtHotelName().setEditable(editable);
        if (view.getTxtHotelId() != null) view.getTxtHotelId().setEditable(editable);
        if (view.getTxtAddress() != null) view.getTxtAddress().setEditable(editable);
        if (view.getTxtPanNumber() != null) view.getTxtPanNumber().setEditable(editable);
        if (view.getTxtOwner() != null) view.getTxtOwner().setEditable(editable);
        if (view.getTxtQuickNote() != null) view.getTxtQuickNote().setEditable(editable);
        if (view.getTxtPhone() != null) view.getTxtPhone().setEditable(editable);
        if (view.getTxtWebsite() != null) view.getTxtWebsite().setEditable(editable);
    }

    private void loadSettings() {
        systemModel model = dao.getSystemSettings();
        if (model != null) {
            if (view.getTxtHotelName() != null) view.getTxtHotelName().setText(model.getHotelName());
            if (view.getTxtHotelId() != null) view.getTxtHotelId().setText(model.getHotelId());
            if (view.getTxtAddress() != null) view.getTxtAddress().setText(model.getAddress());
            if (view.getTxtPanNumber() != null) view.getTxtPanNumber().setText(model.getPanNumber());
            if (view.getTxtOwner() != null) view.getTxtOwner().setText(model.getOwner());
            if (view.getTxtQuickNote() != null) view.getTxtQuickNote().setText(model.getQuickNote());
            if (view.getTxtPhone() != null) view.getTxtPhone().setText(model.getPhone());
            if (view.getTxtWebsite() != null) view.getTxtWebsite().setText(model.getWebsite());
        }
    }
}