package controller;

import dao.BookingManagementDao;
import model.BookingManagementModel;
import view.BookingManagement;
import javax.swing.JOptionPane;

/**
 * Controller class to handle business logic for Booking Management view.
 * Handles room booking lookup by Room number input and fills guest info dynamically.
 */
public class BookingController {
    private final BookingManagement view;
    private final BookingManagementDao dao;

    public BookingController() {
        this.view = new BookingManagement();
        this.dao = new BookingManagementDao();
        initController();
    }

    private void initController() {
        // Clear guest detail fields initially
        clearFields();

        // Make guest detail fields non-editable
        if (view.getTxtFullName() != null) view.getTxtFullName().setEditable(false);
        if (view.getTxtPhoneNumber() != null) view.getTxtPhoneNumber().setEditable(false);
        if (view.getTxtEmailAddress() != null) view.getTxtEmailAddress().setEditable(false);
        if (view.getTxtHomeAddress() != null) view.getTxtHomeAddress().setEditable(false);
        if (view.getComboRoomType() != null) view.getComboRoomType().setEnabled(false);
        if (view.getCheckInDateChooser() != null) view.getCheckInDateChooser().setEnabled(false);
        if (view.getCheckOutDateChooser() != null) view.getCheckOutDateChooser().setEnabled(false);
        if (view.getTxtDiscountDeal() != null) view.getTxtDiscountDeal().setEditable(false);

        // Wire lookup action for the Search Room text field
        if (view.getTxtSearch() != null) {
            view.getTxtSearch().addActionListener(e -> {
                String searchVal = view.getTxtSearch().getText().trim();
                if (searchVal.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Please enter a Room ID/Number.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    int roomNo = Integer.parseInt(searchVal);
                    BookingManagementModel model = dao.getBookingDetails(roomNo);
                    if (model != null) {
                        view.getTxtFullName().setText(model.getFullName() != null ? model.getFullName() : "");
                        view.getTxtPhoneNumber().setText(model.getPhoneNumber() != null ? model.getPhoneNumber() : "");
                        view.getTxtEmailAddress().setText(model.getEmailAddress() != null ? model.getEmailAddress() : "");
                        view.getTxtHomeAddress().setText(model.getHomeAddress() != null ? model.getHomeAddress() : "");
                        
                        // Select Room Type
                        if (model.getRoomType() != null) {
                            String type = model.getRoomType().trim();
                            if (type.equalsIgnoreCase("Single") || type.equalsIgnoreCase("Single Bed") || type.equalsIgnoreCase("Single Bed Room")) {
                                view.getComboRoomType().setSelectedItem("Single Bed");
                            } else if (type.equalsIgnoreCase("Double") || type.equalsIgnoreCase("Double Bed") || type.equalsIgnoreCase("Double Bed Room")) {
                                view.getComboRoomType().setSelectedItem("Double Bed");
                            } else {
                                view.getComboRoomType().setSelectedItem("VIP");
                            }
                        }
                        
                        view.getCheckInDateChooser().setDate(model.getCheckInDate());
                        view.getCheckOutDateChooser().setDate(model.getCheckOutDate());
                        view.getTxtDiscountDeal().setText(model.getDiscountDeal() != null ? model.getDiscountDeal() : "");
                    } else {
                        clearFields();
                        JOptionPane.showMessageDialog(view, "Room is not booked", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Invalid Room ID/Number format.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // Sidebar Navigation
        if (view.getBtnDashboard() != null) {
            view.getBtnDashboard().addActionListener(e -> {
                new FrontdeskDeshboardControler();
                view.dispose();
            });
        }
        if (view.getBtnGuest() != null) {
            view.getBtnGuest().addActionListener(e -> {
                new GuestManagementController(new view.GuestManagement());
                view.dispose();
            });
        }
        if (view.getBtnBooking() != null) {
            view.getBtnBooking().addActionListener(e -> {
                // Already on booking management
            });
        }
        if (view.getBtnMealtime() != null) {
            view.getBtnMealtime().addActionListener(e -> {
                new MealTimeController();
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
                int option = JOptionPane.showConfirmDialog(
                    view,
                    "Are you sure you want to logout?",
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

        // Make frame visible
        view.setVisible(true);
    }

    private void clearFields() {
        if (view.getTxtFullName() != null) view.getTxtFullName().setText("");
        if (view.getTxtPhoneNumber() != null) view.getTxtPhoneNumber().setText("");
        if (view.getTxtEmailAddress() != null) view.getTxtEmailAddress().setText("");
        if (view.getTxtHomeAddress() != null) view.getTxtHomeAddress().setText("");
        if (view.getComboRoomType() != null) view.getComboRoomType().setSelectedIndex(0);
        if (view.getCheckInDateChooser() != null) view.getCheckInDateChooser().setDate(null);
        if (view.getCheckOutDateChooser() != null) view.getCheckOutDateChooser().setDate(null);
        if (view.getTxtDiscountDeal() != null) view.getTxtDiscountDeal().setText("");
    }
}
