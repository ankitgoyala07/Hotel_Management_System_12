package controller;

import dao.DiscountDao;
import model.DiscountModel;
import view.AddDiscount;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Controller class to handle business logic for Adding Discount Deals.
 * Validates entries, auto-calculates deal status in real-time, and saves to database.
 */
public class AddDiscountController {
    private final AddDiscount view;
    private final DiscountDao dao;

    public AddDiscountController() {
        this.view = new AddDiscount();
        this.dao = new DiscountDao();
        initController();
    }

    private void initController() {
        // Pre-fill status field and make it read-only
        view.getTxtStatus().setText("ongoing");
        view.getTxtStatus().setEditable(false);

        // Hook up listeners for dynamic status calculation
        view.getTxtReservationsLeft().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                updateStatusField();
            }
        });

        view.getBtnEndDate().addPropertyChangeListener("date", evt -> updateStatusField());

        // Save action
        view.getBtnSave().addActionListener(e -> handleSaveDiscount());

        // Sidebar navigation
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

        // Display view
        view.setVisible(true);
    }

    private void updateStatusField() {
        try {
            String resStr = view.getTxtReservationsLeft().getText().trim();
            Date endDate = view.getBtnEndDate().getDate();

            if (resStr.isEmpty() || endDate == null) {
                view.getTxtStatus().setText("ongoing");
                return;
            }

            int reservations = Integer.parseInt(resStr);
            if (reservations <= 0) {
                view.getTxtStatus().setText("full");
                return;
            }

            Calendar calToday = Calendar.getInstance();
            calToday.set(Calendar.HOUR_OF_DAY, 0);
            calToday.set(Calendar.MINUTE, 0);
            calToday.set(Calendar.SECOND, 0);
            calToday.set(Calendar.MILLISECOND, 0);

            if (endDate.before(calToday.getTime())) {
                view.getTxtStatus().setText("finished");
            } else {
                view.getTxtStatus().setText("ongoing");
            }
        } catch (NumberFormatException e) {
            view.getTxtStatus().setText("ongoing");
        }
    }

    private void handleSaveDiscount() {
        String code = view.getTxtDealCode().getText().trim();
        String name = view.getTxtDealName().getText().trim();
        String resStr = view.getTxtReservationsLeft().getText().trim();
        Date endDate = view.getBtnEndDate().getDate();

        if (code.isEmpty() || name.isEmpty() || resStr.isEmpty() || endDate == null) {
            JOptionPane.showMessageDialog(view, "Please fill in all the fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int reservations;
        try {
            reservations = Integer.parseInt(resStr);
            if (reservations < 0) {
                JOptionPane.showMessageDialog(view, "Reservations Left cannot be negative.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Reservations Left must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String endDateStr = sdf.format(endDate);

        // Determine status
        String status = "ongoing";
        if (reservations <= 0) {
            status = "full";
        } else {
            Calendar calToday = Calendar.getInstance();
            calToday.set(Calendar.HOUR_OF_DAY, 0);
            calToday.set(Calendar.MINUTE, 0);
            calToday.set(Calendar.SECOND, 0);
            calToday.set(Calendar.MILLISECOND, 0);
            if (endDate.before(calToday.getTime())) {
                status = "finished";
            }
        }

        DiscountModel model = new DiscountModel(code, name, reservations, endDateStr, status);
        boolean success = dao.addDiscount(model);

        if (success) {
            JOptionPane.showMessageDialog(view, "Discount Deal added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            new DiscountController();
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to add Discount Deal. (Code might be duplicate)", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
