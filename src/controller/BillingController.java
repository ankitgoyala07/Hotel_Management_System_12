package controller;

import dao.BillingDao;
import model.BillingModel;
import view.Billing;
import view.FrontDeskDashboard;
import view.GuestManagement;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Controller class to handle all logic and actions for the Billing section.
 *
 * @author i3
 */
public class BillingController {
    private final Billing view;
    private final BillingDao dao = new BillingDao();
    private final BillingModel model;

    public BillingController() {
        this("001");
    }

    public BillingController(String roomNumber) {
        this.view = new Billing();
        this.model = dao.getBillingForRoom(roomNumber);
        initController();
    }

    private void initController() {
        // 1. Populate all labels on the Invoice Card from model
        view.getLblGuestIdValue().setText(model.getGuestId());
        view.getLblRoomIdValue().setText(model.getRoomId());
        view.getLblStayPeriodValue().setText(model.getStayPeriod());

        // Table details
        view.getLblRow1Qty().setText(model.getNights() + " Nights");
        view.getLblRow1Price().setText(String.format("$%.2f", model.getRoomRate()));
        view.getLblRow1Amount().setText(String.format("$%.2f", model.getStayAmount()));

        // Service charges
        view.getLblRow2Qty().setText("1");
        view.getLblRow2Price().setText(String.format("$%.2f", model.getRoomService()));
        view.getLblRow2Amount().setText(String.format("$%.2f", model.getRoomService()));

        view.getLblRow3Qty().setText("1");
        view.getLblRow3Price().setText(String.format("$%.2f", model.getFoodOrders()));
        view.getLblRow3Amount().setText(String.format("$%.2f", model.getFoodOrders()));

        view.getLblRow4Qty().setText("3 Items");
        view.getLblRow4Price().setText(String.format("$%.2f", model.getLaundry() / 3.0));
        view.getLblRow4Amount().setText(String.format("$%.2f", model.getLaundry()));

        view.getLblRow5Qty().setText("—");
        view.getLblRow5Price().setText("—");
        view.getLblRow5Amount().setText(String.format("$%.2f", model.getMiniBar()));

        // Summary totals
        view.getLblSubtotalValue().setText(String.format("$%.2f", model.getSubtotal()));
        view.getLblTaxValue().setText(String.format("$%.2f", model.getTax()));
        view.getLblGrandTotalValue().setText(String.format("$%.2f", model.getGrandTotal()));

        // 2. Wire up buttons
        view.getBtnDownloadPdf().addActionListener(e -> JOptionPane.showMessageDialog(view,
                "Invoice ID: INV-" + model.getGuestId() + "-" + model.getRoomId() + "\nInvoice downloaded as PDF successfully!",
                "Download Invoice",
                JOptionPane.INFORMATION_MESSAGE));

        view.getBtnPrintInvoice().addActionListener(e -> JOptionPane.showMessageDialog(view,
                "Printing invoice for Guest ID " + model.getGuestId() + "...\nSent to system printer.",
                "Print Invoice",
                JOptionPane.INFORMATION_MESSAGE));

        // 3. Wire up sidebar navigation labels
        view.getLblNavDashboard().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new FrontdeskDeshboardControler();
                view.dispose();
            }
        });

        view.getLblNavGuests().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new GuestManagement().setVisible(true);
                view.dispose();
            }
        });

        view.getLblNavBookings().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(view, "Navigating to Bookings section.");
            }
        });

        view.getLblNavMealTime().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(view, "Navigating to Meal time section.");
            }
        });

        view.getLblNavLogout().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
            }
        });

        view.setVisible(true);
    }
}
