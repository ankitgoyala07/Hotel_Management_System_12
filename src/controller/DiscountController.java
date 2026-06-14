package controller;

import dao.DiscountDao;
import model.DiscountModel;
import view.DiscountOffers;
import view.admindashboard;
import view.roommanagement;
import view.loginpage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Controller class coordinating the DiscountOffers view with the DiscountModel and DiscountDao.
 */
public class DiscountController {
    private final DiscountOffers view;
    private final DiscountDao dao;
    private boolean isFilteringOngoing = false;

    public DiscountController(DiscountOffers view) {
        this.view = view;
        this.dao = new DiscountDao();
        initController();
    }

    private void initController() {
        // Setup initial table data
        loadTableData();

        // Bind Sidebar Navigation Clicks
        setupNavigation();

        // Bind Main Action Buttons
        setupActionButtons();

        // Bind Table Cell Click for row deletion (Action column)
        setupTableAction();
    }

    private void loadTableData() {
        List<DiscountModel> list = dao.getAllDiscounts();
        DefaultTableModel model = (DefaultTableModel) view.getTblDeals().getModel();
        model.setRowCount(0);

        for (DiscountModel d : list) {
            if (isFilteringOngoing) {
                String status = d.getStatus().toLowerCase();
                if (!status.equals("active") && !status.equals("ongoing")) {
                    continue;
                }
            }
            model.addRow(new Object[]{
                d.getDealCode(),
                d.getDealName(),
                d.getReservationsLeft(),
                d.getEndDate(),
                d.getStatus(),
                "Delete"
            });
        }
    }

    private void setupNavigation() {
        // Dashboard Navigation
        setupNavLabel(view.getLblNavDashboard(), () -> {
            java.awt.EventQueue.invokeLater(() -> {
                new admindashboard().setVisible(true);
            });
            view.dispose();
        });

        // Rooms Navigation
        setupNavLabel(view.getLblNavRooms(), () -> {
            java.awt.EventQueue.invokeLater(() -> {
                new roommanagement().setVisible(true);
            });
            view.dispose();
        });

        // Discount (current page, does nothing but visual feedback)
        setupNavLabel(view.getLblNavDiscount(), null);

        // Staffs Navigation
        setupNavLabel(view.getLblNavStaffs(), () -> {
            JOptionPane.showMessageDialog(view, "Staff Management is under development.", "Information", JOptionPane.INFORMATION_MESSAGE);
        });

        // System Settings Navigation
        setupNavLabel(view.getLblNavSettings(), () -> {
            JOptionPane.showMessageDialog(view, "System Settings is under development.", "Information", JOptionPane.INFORMATION_MESSAGE);
        });

        // Reports Navigation
        setupNavLabel(view.getLblNavReports(), () -> {
            JOptionPane.showMessageDialog(view, "Reports is under development.", "Information", JOptionPane.INFORMATION_MESSAGE);
        });

        // Logout Navigation
        setupNavLabel(view.getLblNavLogout(), () -> {
            java.awt.EventQueue.invokeLater(() -> {
                new loginpage().setVisible(true);
            });
            view.dispose();
        });
    }

    private void setupNavLabel(JLabel label, Runnable action) {
        if (label == null) return;
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Define original styling
        Color originalBg = label.getBackground();
        Color originalFg = label.getForeground();
        boolean originalOpaque = label.isOpaque();

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (action != null) {
                    action.run();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // If it is the current page, highlight is already set by view default, so only highlight inactive ones
                if (label != view.getLblNavDiscount()) {
                    label.setOpaque(true);
                    label.setBackground(new Color(232, 240, 254)); // Light blue highlight
                    label.setForeground(new Color(26, 115, 232));  // Blue text
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (label != view.getLblNavDiscount()) {
                    label.setOpaque(originalOpaque);
                    label.setBackground(originalBg);
                    label.setForeground(originalFg);
                }
            }
        });
    }

    private void setupActionButtons() {
        // Toggle ongoing filter
        view.getBtnOngoing().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isFilteringOngoing = !isFilteringOngoing;
                if (isFilteringOngoing) {
                    view.getBtnOngoing().setText("Show All");
                    // Change style to highlight active filter
                    view.getBtnOngoing().setBackground(new Color(232, 240, 254));
                } else {
                    view.getBtnOngoing().setText("Ongoing");
                    view.getBtnOngoing().setBackground(null);
                }
                loadTableData();
            }
        });

        // Add Deal dialog
        view.getBtnAddDeal().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddDealDialog();
            }
        });
    }

    private void showAddDealDialog() {
        // Create custom panel with input fields
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtCode = new JTextField();
        JTextField txtName = new JTextField();
        JTextField txtReservations = new JTextField();
        JTextField txtEndDate = new JTextField();
        JComboBox<String> comboStatus = new JComboBox<>(new String[]{"Active", "Expired"});

        panel.add(new JLabel("Deal Code (e.g. WINTER20):"));
        panel.add(txtCode);
        panel.add(new JLabel("Deal Name:"));
        panel.add(txtName);
        panel.add(new JLabel("Reservations Left:"));
        panel.add(txtReservations);
        panel.add(new JLabel("End Date (YYYY-MM-DD):"));
        panel.add(txtEndDate);
        panel.add(new JLabel("Status:"));
        panel.add(comboStatus);

        int result = JOptionPane.showConfirmDialog(
            view, 
            panel, 
            "Add New Discount/Offer Deal", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String code = txtCode.getText().trim();
            String name = txtName.getText().trim();
            String reservationsStr = txtReservations.getText().trim();
            String endDate = txtEndDate.getText().trim();
            String status = (String) comboStatus.getSelectedItem();

            // Input Validation
            if (code.isEmpty() || name.isEmpty() || reservationsStr.isEmpty() || endDate.isEmpty()) {
                JOptionPane.showMessageDialog(view, "All fields are required.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int reservations;
            try {
                reservations = Integer.parseInt(reservationsStr);
                if (reservations < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Reservations must be a valid non-negative integer.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            DiscountModel newDiscount = new DiscountModel(code, name, reservations, endDate, status);
            boolean success = dao.addDiscount(newDiscount);
            if (success) {
                JOptionPane.showMessageDialog(view, "New discount deal added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add deal. The deal code might already exist.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void setupTableAction() {
        view.getTblDeals().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTblDeals().rowAtPoint(e.getPoint());
                int col = view.getTblDeals().columnAtPoint(e.getPoint());
                
                // Index 5 corresponds to the "Action" column in the table model
                if (row >= 0 && col == 5) {
                    String dealCode = (String) view.getTblDeals().getValueAt(row, 0);
                    if (dealCode == null) return;

                    int confirm = JOptionPane.showConfirmDialog(
                        view,
                        "Are you sure you want to delete the deal: " + dealCode + "?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean success = dao.deleteDiscount(dealCode);
                        if (success) {
                            JOptionPane.showMessageDialog(view, "Deal deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            loadTableData();
                        } else {
                            JOptionPane.showMessageDialog(view, "Failed to delete deal from the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }
}
