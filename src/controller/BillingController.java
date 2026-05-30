package controller;

import dao.BillingDao;
import model.BillingModel;
import view.Billing;
import javax.swing.JOptionPane;

/**
 * Controller class to handle all logic and actions for the Billing and Invoicing screen.
 * Supports direct billing lookup by Room ID or Guest ID text inputs,
 * and handles room checkout dynamically when the invoice is printed.
 */
public class BillingController {
    private final Billing view;
    private final BillingDao dao = new BillingDao();

    /**
     * Default constructor (opens default Room 101 bill)
     */
    public BillingController() {
        this("");
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
        // Retrieve and populate initial billing details from the database
        BillingModel model = dao.getBillingForRoom(roomNumber);
        loadBilling(model);

        // Populate header / role
        if (view.getLblRole() != null) {
            view.getLblRole().setText("Frontdesk Staff");
        }

        // Add action listeners to JTextFields for searching on enter/action trigger
        if (view.getTxtRoomNo() != null) {
            view.getTxtRoomNo().addActionListener(e -> {
                String rNo = view.getTxtRoomNo().getText().trim();
                if (!rNo.isEmpty()) {
                    BillingModel res = dao.getBillingForRoom(rNo);
                    loadBilling(res);
                }
            });
        }

        if (view.getTxtGuestId() != null) {
            view.getTxtGuestId().addActionListener(e -> {
                String gId = view.getTxtGuestId().getText().trim();
                if (!gId.isEmpty()) {
                    BillingModel res = dao.getBillingForGuest(gId);
                    loadBilling(res);
                }
            });
        }

        // Action listener for Print Invoice (which checks out room)
        if (view.getBtnPrintInvoice() != null) {
            view.getBtnPrintInvoice().addActionListener(e -> {
                String roomNo = (view.getTxtRoomNo() != null) ? view.getTxtRoomNo().getText().trim() : "";
                if (roomNo.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Please enter a valid Room number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                boolean success = dao.checkoutRoom(roomNo);
                if (success) {
                    JOptionPane.showMessageDialog(view, "Invoice printed sucessfully. Room " + roomNo + " checked out successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Refresh stats
                    loadBilling(dao.getBillingForRoom(roomNo));
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to complete checkout for Room " + roomNo + ". The room might not be checked in.", "Error", JOptionPane.ERROR_MESSAGE);
                }
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
                new view.MealTime().setVisible(true);
                view.dispose();
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

    private void loadBilling(BillingModel model) {
        if (model == null) return;

        // Populate Invoicing metadata text fields
        if (view.getTxtGuestId() != null) {
            view.getTxtGuestId().setText(model.getGuestId());
        }
        if (view.getTxtRoomNo() != null) {
            view.getTxtRoomNo().setText(model.getRoomId());
        }
        if (view.getLblStayPeriodValue() != null) {
            view.getLblStayPeriodValue().setText(model.getStayPeriod());
        }

        // Populate row items
        // Row 1: Room Charges
        if (view.getLblRow1Desc() != null) {
            view.getLblRow1Desc().setText("Room (" + model.getRoomType() + ")");
        }
        if (view.getLblRow1Qty() != null) {
            view.getLblRow1Qty().setText(model.getNights() + " Days");
        }
        if (view.getLblRow1Amount() != null) {
            view.getLblRow1Amount().setText("$" + String.format("%.2f", model.getStayAmount()));
        }

        // Row 2: Room Service
        if (view.getLblRow2Desc() != null) {
            view.getLblRow2Desc().setText("Room Service & Amenities");
        }
        if (view.getLblRow2Qty() != null) {
            view.getLblRow2Qty().setText(model.getRoomService() > 0 ? "1" : "0");
        }
        if (view.getLblRow2Amount() != null) {
            view.getLblRow2Amount().setText("$" + String.format("%.2f", model.getRoomService()));
        }

        // Row 3: Discount Code
        if (view.getLblRow3Desc() != null) {
            if (model.hasDiscount()) {
                view.getLblRow3Desc().setText("Offer: " + model.getDiscountDeal());
            } else {
                view.getLblRow3Desc().setText("Discount Code");
            }
        }
        if (view.getLblRow3Qty() != null) {
            if (model.hasDiscount()) {
                view.getLblRow3Qty().setText("-10%");
            } else {
                view.getLblRow3Qty().setText("0%");
            }
        }
        if (view.getLblRow3Amount() != null) {
            if (model.hasDiscount()) {
                view.getLblRow3Amount().setText("-$" + String.format("%.2f", model.getDiscountAmount()));
            } else {
                view.getLblRow3Amount().setText("$0.00");
            }
        }

        // Row 3 (duplicate labels): Food Orders (using reflection)
        javax.swing.JLabel lblRow3Desc1 = getPrivateLabel("lblRow3Desc1");
        javax.swing.JLabel lblRow3Qty1 = getPrivateLabel("lblRow3Qty1");
        javax.swing.JLabel lblRow3Amount1 = getPrivateLabel("lblRow3Amount1");

        if (lblRow3Desc1 != null) {
            lblRow3Desc1.setText("Food orders");
        }
        if (lblRow3Qty1 != null) {
            lblRow3Qty1.setText(model.getFoodOrders() > 0 ? "1" : "0");
        }
        if (lblRow3Amount1 != null) {
            lblRow3Amount1.setText("$" + String.format("%.2f", model.getFoodOrders()));
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

    private javax.swing.JLabel getPrivateLabel(String name) {
        try {
            java.lang.reflect.Field field = view.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return (javax.swing.JLabel) field.get(view);
        } catch (Exception e) {
            return null;
        }
    }
}
