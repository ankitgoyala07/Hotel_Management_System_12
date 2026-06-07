package controller;

import dao.AddStaffDao;
import model.AddStaffModel;
import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * Controller class to handle adding new staff members and validating their inputs.
 *
 * @author i3
 */
public class AddStaffController {
    private final AddStaffDao dao = new AddStaffDao();

    /**
     * Validates input values, gathers them into an AddStaffModel, and saves them.
     * Shows error/success dialogs as appropriate.
     *
     * @return true if save succeeded, false otherwise
     */
    public boolean handleSaveStaff(Component parent, String staffId, String name, String phone,
                                   String email, String address, String role, String shift) {
        if (staffId.isEmpty() || name.isEmpty() || phone.isEmpty() ||
            email.isEmpty() || address.isEmpty() || role.isEmpty() || shift.isEmpty()) {
            JOptionPane.showMessageDialog(parent,
                "Please fill in all fields.",
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate Phone (basic digits/spaces/hyphens check)
        if (!phone.matches("^\\+?[0-9\\-\\s]{7,15}$")) {
            JOptionPane.showMessageDialog(parent,
                "Please enter a valid phone number.",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validate Email
        if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            JOptionPane.showMessageDialog(parent,
                "Please enter a valid email address.",
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Check if Staff ID already exists
        if (dao.staffExists(staffId)) {
            JOptionPane.showMessageDialog(parent,
                "Staff ID already exists. Please use a unique Staff ID.",
                "Save Failed", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Create the model
        AddStaffModel staff = new AddStaffModel(staffId, name, phone, email, address, role, shift);
        boolean success = dao.insertStaff(staff);

        if (success) {
            JOptionPane.showMessageDialog(parent,
                "Staff member added successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(parent,
                "Database error. Failed to add staff member.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
