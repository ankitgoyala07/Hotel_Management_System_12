package controller;

import dao.MealTimeDao;
import java.lang.reflect.Field;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MealTimeModel;
import view.MealTime;

/**
 * MealTimeController coordinates interactions between the MealTime JFrame View
 * and the MealTimeDao data layer, handling database syncing and UI bindings.
 */
public class MealTimeController {
    private final MealTime view;
    private final MealTimeDao dao;
    private boolean isEditing = false;

    /**
     * Parameterized constructor.
     * @param view The JFrame view class for MealTime
     */
    public MealTimeController(MealTime view) {
        this.view = view;
        this.dao = new MealTimeDao();
        initController();
    }

    /**
     * Default constructor.
     */
    public MealTimeController() {
        this.view = new MealTime();
        this.dao = new MealTimeDao();
    }

    /**
     * Initializes all UI listeners, reflection controls, and button actions.
     */
    private void initController() {
        // Retrieve private components via Reflection
        javax.swing.JButton btnDashboard = getPrivateButton("Dashboard");
        javax.swing.JButton btnGuest = getPrivateButton("Guest");
        javax.swing.JButton btnBooking = getPrivateButton("Booking");
        javax.swing.JButton btnMealtime = getPrivateButton("Mealtime");
        javax.swing.JButton btnBilling = getPrivateButton("Billing");
        javax.swing.JButton btnLogout = getPrivateButton("Logout");

        javax.swing.JButton btnSave = getPrivateButton("jButtonEdit");
        javax.swing.JButton btnEdit = getPrivateButton("jButtonEdit1");

        // Set up edit/save button states initially
        if (btnSave != null) {
            btnSave.setEnabled(false);
            btnSave.addActionListener(e -> saveTimings(btnSave, btnEdit));
        }
        if (btnEdit != null) {
            btnEdit.setEnabled(true);
            btnEdit.addActionListener(e -> enableEditing(btnSave, btnEdit));
        }

        // Sidebar Navigation
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
                // Already on Mealtime, just refresh
                loadTableData();
            });
        }
        if (btnBilling != null) {
            btnBilling.addActionListener(e -> {
                new BillingController();
                view.dispose();
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
    }

    /**
     * Enables table editing mode.
     */
    private void enableEditing(javax.swing.JButton btnSave, javax.swing.JButton btnEdit) {
        isEditing = true;
        if (btnSave != null) btnSave.setEnabled(true);
        if (btnEdit != null) btnEdit.setEnabled(false);
        JOptionPane.showMessageDialog(view,
            "Editing enabled. Double-click timing cells to edit, then click SAVE.",
            "Edit Mode",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Saves table changes to the MySQL database and resets editing state.
     */
    private void saveTimings(javax.swing.JButton btnSave, javax.swing.JButton btnEdit) {
        javax.swing.JTable table = view.getjTableSchedule();
        if (table == null) return;

        // Stop active editing to commit changes in cell editors
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        boolean allSuccess = true;

        for (int i = 0; i < rowCount; i++) {
            String roomType = (String) model.getValueAt(i, 0);
            String breakfast = (String) model.getValueAt(i, 1);
            String lunch = (String) model.getValueAt(i, 2);
            String dinner = (String) model.getValueAt(i, 3);

            MealTimeModel updated = new MealTimeModel(roomType, breakfast, lunch, dinner);
            boolean success = dao.updateMealTimeByRoom(roomType, updated);
            if (!success) {
                allSuccess = false;
            }
        }

        if (allSuccess) {
            JOptionPane.showMessageDialog(view,
                "Meal timings updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            isEditing = false;
            if (btnSave != null) btnSave.setEnabled(false);
            if (btnEdit != null) btnEdit.setEnabled(true);
            loadTableData(); // Refresh table view to reflect database
        } else {
            JOptionPane.showMessageDialog(view,
                "Failed to update some meal timings in database.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Fetches all meal schedules from the DAO layer and populates the table in the view.
     */
    public void loadTableData() {
        if (view == null || view.getjTableSchedule() == null) {
            return;
        }

        // Fetch data
        List<MealTimeModel> list = dao.getAllMealTimes();

        // Create a custom table model that respects the isEditing state
        DefaultTableModel model = new DefaultTableModel(
            new Object [][] {},
            new String [] { "ROOM TYPE", "BREAKFAST TIMING", "LUNCH TIMING", "DINNER TIMING" }
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return isEditing && col > 0;
            }
            @Override
            public Class<?> getColumnClass(int col) {
                return String.class;
            }
        };

        // Populate table model
        for (MealTimeModel item : list) {
            model.addRow(new Object[]{
                item.getRoomType(),
                item.getBreakfastTiming(),
                item.getLunchTiming(),
                item.getDinnerTiming()
            });
        }

        view.getjTableSchedule().setModel(model);
    }

    /**
     * Helper method to retrieve private JButton fields from the view using reflection.
     */
    private javax.swing.JButton getPrivateButton(String name) {
        try {
            Field field = view.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return (javax.swing.JButton) field.get(view);
        } catch (Exception e) {
            return null;
        }
    }
}
