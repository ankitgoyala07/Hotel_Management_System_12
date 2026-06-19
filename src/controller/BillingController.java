package controller;

import dao.BillingDao;
import model.BillingModel;
import view.Billing;
import javax.swing.JOptionPane;

/**
 * Controller class to handle all logic and actions for the Billing and Invoicing screen.
 * Uses public view getters to follow the clean architecture flow.
 */
public class BillingController {
    private final Billing view;
    private final BillingDao dao = new BillingDao();

    /**
     * Default constructor (opens default Room 101 bill)
     */
    public BillingController() {
        this("101");
    }

    /**
     * Parameterized constructor to show invoice details for a specific room
     *
     * @param roomNumber the room number to calculate the bill for
     */
    public BillingController(String roomNumber) {
        this.view = new Billing();
        initController(roomNumber);
    }

    private void initController(String roomNumber) {
        // Retrieve billing details from the database
        BillingModel model = dao.getBillingForRoom(roomNumber);

        // Populate header / role
        if (view.getLblRole() != null) {
            view.getLblRole().setText("Frontdesk Staff");
        }

        // Populate Invoicing metadata labels
        if (view.getLblGuestIdValue() != null) {
            view.getLblGuestIdValue().setText(model.getGuestId());
        }
        if (view.getLblRoomIdValue() != null) {
            view.getLblRoomIdValue().setText("Room " + model.getRoomId());
        }
        if (view.getLblStayPeriodValue() != null) {
            view.getLblStayPeriodValue().setText(model.getStayPeriod());
        }

        // Populate row items
        // Row 1: Room Charges
        if (view.getLblRow1Qty() != null) {
            view.getLblRow1Qty().setText(model.getNights() + " Nights");
        }
        if (view.getLblRow1Price() != null) {
            view.getLblRow1Price().setText("$" + String.format("%.2f", model.getRoomRate()));
        }
        if (view.getLblRow1Amount() != null) {
            view.getLblRow1Amount().setText("$" + String.format("%.2f", model.getStayAmount()));
        }

        // Row 2: Room Service
        if (view.getLblRow2Qty() != null) {
            view.getLblRow2Qty().setText(model.getRoomService() > 0 ? "1" : "0");
        }
        if (view.getLblRow2Price() != null) {
            view.getLblRow2Price().setText("$" + String.format("%.2f", model.getRoomService()));
        }
        if (view.getLblRow2Amount() != null) {
            view.getLblRow2Amount().setText("$" + String.format("%.2f", model.getRoomService()));
        }

        // Row 3: Food Orders
        if (view.getLblRow3Qty() != null) {
            view.getLblRow3Qty().setText(model.getFoodOrders() > 0 ? "1" : "0");
        }
        if (view.getLblRow3Price() != null) {
            view.getLblRow3Price().setText("$" + String.format("%.2f", model.getFoodOrders()));
        }
        if (view.getLblRow3Amount() != null) {
            view.getLblRow3Amount().setText("$" + String.format("%.2f", model.getFoodOrders()));
        }

        // Row 4: Laundry
        if (view.getLblRow4Qty() != null) {
            view.getLblRow4Qty().setText(model.getLaundry() > 0 ? "1" : "0");
        }
        if (view.getLblRow4Price() != null) {
            view.getLblRow4Price().setText("$" + String.format("%.2f", model.getLaundry()));
        }
        if (view.getLblRow4Amount() != null) {
            view.getLblRow4Amount().setText("$" + String.format("%.2f", model.getLaundry()));
        }

        // Row 5: Mini Bar
        if (view.getLblRow5Qty() != null) {
            view.getLblRow5Qty().setText(model.getMiniBar() > 0 ? "1" : "0");
        }
        if (view.getLblRow5Price() != null) {
            view.getLblRow5Price().setText("$" + String.format("%.2f", model.getMiniBar()));
        }
        if (view.getLblRow5Amount() != null) {
            view.getLblRow5Amount().setText("$" + String.format("%.2f", model.getMiniBar()));
        }

        // Totals
        if (view.getLblSubtotalValue() != null) {
            view.getLblSubtotalValue().setText("$" + String.format("%.2f", model.getSubtotal()));
        }
        if (view.getLblTaxValue() != null) {
            view.getLblTaxValue().setText("$" + String.format("%.2f", model.getTax()));
        }
        if (view.getLblGrandTotalValue() != null) {
            view.getLblGrandTotalValue().setText("$" + String.format("%.2f", model.getGrandTotal()));
        }

        // Bind Action Listeners
        if (view.getBtnPrintInvoice() != null) {
            view.getBtnPrintInvoice().addActionListener(e -> {
                JOptionPane.showMessageDialog(view, "Invoice printed successfully!", "Print", JOptionPane.INFORMATION_MESSAGE);
            });
        }

        // Sidebar Navigation
        javax.swing.JButton btnDashboard = view.getBtnDashboard();
        javax.swing.JButton btnLogout = view.getBtnLogout();
        javax.swing.JButton btnGuest = getPrivateButton("Guest");
        javax.swing.JButton btnBooking = getPrivateButton("Booking");
        javax.swing.JButton btnMealtime = getPrivateButton("Mealtime");
        javax.swing.JButton btnBilling = getPrivateButton("Billing");

        if (btnDashboard != null) {
            btnDashboard.addActionListener(e -> {
                new FrontdeskDeshboardControler();
                view.dispose();
            });
        }
        if (btnGuest != null) {
            btnGuest.addActionListener(e -> {
                new GuestManagementController(new view.GuestManagement());
                view.dispose();
            });
        }
        if (btnBooking != null) {
            btnBooking.addActionListener(e -> {
                new BookingController();
                view.dispose();
            });
        }
        if (btnMealtime != null) {
            btnMealtime.addActionListener(e -> {
                new view.OrderFood().setVisible(true);
            });
        }
        if (btnBilling != null) {
            btnBilling.addActionListener(e -> {
                // Already on billing, do nothing
            });
        }
        if (btnLogout != null) {
            btnLogout.addActionListener(e -> {
                int option = JOptionPane.showConfirmDialog(
                    view,
                    "Are you sure you want to log out?",
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

        // Show window
        view.setVisible(true);
    }

    private javax.swing.JButton getPrivateButton(String name) {
        try {
            java.lang.reflect.Field field = view.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return (javax.swing.JButton) field.get(view);
        } catch (Exception e) {
            return null;
        }
    }
}
