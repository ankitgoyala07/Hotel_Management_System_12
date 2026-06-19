package controller;

import dao.DiscountDao;
import model.DiscountModel;
import view.Discount;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Controller class to handle business logic for Discount and Offers view.
 * Manages database table loading and sidebar navigation.
 */
public class DiscountController {
    private final Discount view;
    private final DiscountDao dao;

    public DiscountController() {
        this.view = new Discount();
        this.dao = new DiscountDao();
        initController();
    }

    private void initController() {
        // Load discounts from database
        loadDiscounts();

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
                // Already on Discount
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
                // Do nothing
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

        // Hook up Add and Delete buttons
        if (view.getBtnAddDiscount() != null) {
            view.getBtnAddDiscount().addActionListener(e -> {
                new AddDiscountController();
                view.dispose();
            });
        }

        if (view.getBtnDeleteDiscount() != null) {
            view.getBtnDeleteDiscount().addActionListener(e -> {
                int selectedRow = view.getTable().getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(view, "Please select a deal to delete.", "Delete Deal", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String dealCode = view.getTable().getValueAt(selectedRow, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete deal code: " + dealCode + "?", "Delete Deal", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean deleted = dao.deleteDiscount(dealCode);
                    if (deleted) {
                        JOptionPane.showMessageDialog(view, "Deal deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadDiscounts();
                    } else {
                        JOptionPane.showMessageDialog(view, "Failed to delete deal.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

        // Display view
        view.setVisible(true);
    }

    private void loadDiscounts() {
        if (view.getTable() != null) {
            List<DiscountModel> list = dao.getAllDiscounts();
            DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
            model.setRowCount(0);
            for (DiscountModel d : list) {
                model.addRow(new Object[]{
                    d.getDealCode(),
                    d.getDealName(),
                    d.getReservationsLeft(),
                    d.getEndDate(),
                    d.getStatus()
                });
            }
        }
    }
}
