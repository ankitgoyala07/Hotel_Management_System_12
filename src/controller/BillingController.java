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
        if (view.getBtnDashboard() != null) {
            view.getBtnDashboard().addActionListener(e -> {
                new FrontdeskDeshboardControler();
                view.dispose();
            });
        }

        if (view.getBtnLogout() != null) {
            view.getBtnLogout().addActionListener(e -> {
                new LoginController();
                view.dispose();
            });
        }

        // Show window
        view.setVisible(true);
    }
}
