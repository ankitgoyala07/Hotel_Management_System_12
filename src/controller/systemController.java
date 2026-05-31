package controller;

import dao.systemDao;
import model.systemModel;
import javax.swing.JOptionPane;
import view.SystemSetting;

/**
 * Controller class coordinating interactions between SystemSetting View and systemModel/systemDao.
 *
 * @author i3
 */
public class systemController {
    private final systemDao dao = new systemDao();

    /**
     * Loads the settings from the persistent layer and populates the text fields in the view.
     *
     * @param view the SystemSetting frame view
     */
    public void loadSettings(SystemSetting view) {
        if (view == null) return;

        systemModel model = dao.getSystemSettings();
        view.setHotelNameText(model.getHotelName());
        view.setHotelIdText(model.getHotelId());
        view.setAddressText(model.getAddress());
        view.setPanNumberText(model.getPanNumber());
        view.setOwnerText(model.getOwner());
        view.setEmailText(model.getEmail());
        view.setPhoneText(model.getPhone());
        view.setWebsiteText(model.getWebsite());
    }

    /**
     * Validates input values, gathers them into a systemModel, and saves them via systemDao.
     * Shows error dialogs on validation failure, or success dialog on success.
     *
     * @param view the SystemSetting frame view
     * @return true if save was successful, false otherwise
     */
    public boolean saveSettings(SystemSetting view) {
        if (view == null) return false;

        String hotelName = view.getHotelNameText().trim();
        String hotelId = view.getHotelIdText().trim();
        String address = view.getAddressText().trim();
        String panNumber = view.getPanNumberText().trim();
        String owner = view.getOwnerText().trim();
        String email = view.getEmailText().trim();
        String phone = view.getPhoneText().trim();
        String website = view.getWebsiteText().trim();

        // Validations
        if (hotelName.isEmpty()) {
            showError(view, "Hotel Name cannot be empty.");
            return false;
        }
        if (hotelId.isEmpty()) {
            showError(view, "Hotel ID cannot be empty.");
            return false;
        }
        if (address.isEmpty()) {
            showError(view, "Address cannot be empty.");
            return false;
        }
        if (panNumber.isEmpty()) {
            showError(view, "PAN Number cannot be empty.");
            return false;
        }
        if (owner.isEmpty()) {
            showError(view, "Owner cannot be empty.");
            return false;
        }
        if (email.isEmpty()) {
            showError(view, "Email cannot be empty.");
            return false;
        } else if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showError(view, "Please enter a valid Email Address (e.g. info@hotel.com).");
            return false;
        }
        if (phone.isEmpty()) {
            showError(view, "Phone number cannot be empty.");
            return false;
        } else if (!phone.matches("^\\+?[0-9\\-\\s]{7,15}$")) {
            showError(view, "Please enter a valid Phone Number.");
            return false;
        }
        if (website.isEmpty()) {
            showError(view, "Website cannot be empty.");
            return false;
        }

        // Create Model
        systemModel model = new systemModel(hotelName, hotelId, address, panNumber, owner, email, phone, website);

        // Persist using DAO
        boolean success = dao.updateSystemSettings(model);

        if (success) {
            JOptionPane.showMessageDialog(view,
                "System settings have been successfully updated!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            showError(view, "Failed to save settings. Please check your system logs or file permissions.");
            return false;
        }
    }

    private void showError(SystemSetting view, String message) {
        JOptionPane.showMessageDialog(view, message, "Validation Error", JOptionPane.WARNING_MESSAGE);
    }
}
