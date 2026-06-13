package controller;

import dao.FrontdeskDeshboardDao;
import model.FrontdeskDeshboardModel;
import view.FrontDeskDashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class to handle all logic and actions for the Front Desk Dashboard.
 *
 * @author i3
 */
public class FrontdeskDeshboardControler {
    private final FrontDeskDashboard view;
    private final FrontdeskDeshboardDao dao = new FrontdeskDeshboardDao();

    public FrontdeskDeshboardControler() {
        this.view = new FrontDeskDashboard();
        initController();
    }

    private void initController() {
        try {
            // Set up room labels and load data
            setupRooms();

            // Set up sidebar actions (Logout, etc.)
            setupSidebar();

            // Make the dashboard visible
            view.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error initializing controller: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupRooms() throws Exception {
        // Retrieve private panels via Reflection
        JPanel jPanel6 = (JPanel) getPrivateField("jPanel6"); // Single Bedrooms Panel
        JPanel jPanel7 = (JPanel) getPrivateField("jPanel7"); // Double Bedrooms Panel
        JPanel jPanel8 = (JPanel) getPrivateField("jPanel8"); // VIP Rooms Panel

        // Retrieve and sort room labels in each panel by layout coordinates (top-down, left-to-right)
        List<JLabel> singleLabels = getSortedRoomLabels(jPanel6);
        List<JLabel> doubleLabels = getSortedRoomLabels(jPanel7);
        List<JLabel> vipLabels = getSortedRoomLabels(jPanel8);

        // Fetch all rooms from the database
        List<FrontdeskDeshboardModel> allRooms = dao.getAllRooms();

        // Categorize rooms by type
        List<FrontdeskDeshboardModel> singleRooms = allRooms.stream()
            .filter(r -> r.getRoomType().equalsIgnoreCase("Single"))
            .collect(Collectors.toList());

        List<FrontdeskDeshboardModel> doubleRooms = allRooms.stream()
            .filter(r -> r.getRoomType().equalsIgnoreCase("Double"))
            .collect(Collectors.toList());

        List<FrontdeskDeshboardModel> vipRooms = allRooms.stream()
            .filter(r -> r.getRoomType().equalsIgnoreCase("VIP"))
            .collect(Collectors.toList());

        // Bind data to labels
        bindRoomsToLabels(singleLabels, singleRooms);
        bindRoomsToLabels(doubleLabels, doubleRooms);
        bindRoomsToLabels(vipLabels, vipRooms);

        // Update real-time calculations in the top overview bar
        updateOverviewStats();
    }

    private List<JLabel> getSortedRoomLabels(JPanel panel) {
        List<JLabel> labels = new ArrayList<>();
        if (panel != null) {
            for (Component comp : panel.getComponents()) {
                if (comp instanceof JLabel) {
                    labels.add((JLabel) comp);
                }
            }
        }
        // Sort by Y coordinate first, then X coordinate to align with the visual grid
        labels.sort((l1, l2) -> {
            if (l1.getY() != l2.getY()) {
                return Integer.compare(l1.getY(), l2.getY());
            }
            return Integer.compare(l1.getX(), l2.getX());
        });

        // Filter out duplicate components sharing identical X and Y coordinates
        List<JLabel> uniqueLabels = new ArrayList<>();
        for (JLabel lbl : labels) {
            boolean duplicate = false;
            for (JLabel unique : uniqueLabels) {
                if (unique.getX() == lbl.getX() && unique.getY() == lbl.getY()) {
                    duplicate = true;
                    lbl.setVisible(false); // Hide the overlapping duplicate label
                    break;
                }
            }
            if (!duplicate) {
                uniqueLabels.add(lbl);
            }
        }
        return uniqueLabels;
    }

    private void bindRoomsToLabels(List<JLabel> labels, List<FrontdeskDeshboardModel> rooms) {
        int limit = Math.min(labels.size(), rooms.size());
        
        // Hide unused labels if database has fewer rooms than design layout
        for (int i = limit; i < labels.size(); i++) {
            labels.get(i).setVisible(false);
        }

        for (int i = 0; i < limit; i++) {
            JLabel label = labels.get(i);
            FrontdeskDeshboardModel room = rooms.get(i);

            label.setText(room.getRoomNumber());
            updateLabelVisuals(label, room.getStatus());

            // Clear existing mouse listeners to avoid duplicate bindings
            for (java.awt.event.MouseListener ml : label.getMouseListeners()) {
                label.removeMouseListener(ml);
            }

            // Bind click actions for booking and checking out
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    handleRoomClick(room, label);
                }
            });
        }
    }

    private void updateLabelVisuals(JLabel label, String status) {
        if (status.equalsIgnoreCase("Occupied")) {
            label.setBackground(new Color(0, 255, 0)); // Green color for occupied
            label.setForeground(Color.BLACK);
        } else {
            label.setBackground(Color.WHITE); // White color for available
            label.setForeground(Color.BLACK);
        }
    }

    private void handleRoomClick(FrontdeskDeshboardModel room, JLabel label) {
        if (room.getStatus().equalsIgnoreCase("Available")) {
            int option = JOptionPane.showConfirmDialog(
                view,
                "Do you want to book Room " + room.getRoomNumber() + "?",
                "Book Room",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (option == JOptionPane.YES_OPTION) {
                if (dao.updateRoomStatus(room.getRoomNumber(), "Occupied")) {
                    room.setStatus("Occupied");
                    updateLabelVisuals(label, "Occupied");
                    updateOverviewStats();
                    JOptionPane.showMessageDialog(view, "Room " + room.getRoomNumber() + " booked successfully!");
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to book room.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            int option = JOptionPane.showConfirmDialog(
                view,
                "Do you want to check out of Room " + room.getRoomNumber() + "?",
                "Checkout Room",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (option == JOptionPane.YES_OPTION) {
                if (dao.updateRoomStatus(room.getRoomNumber(), "Available")) {
                    room.setStatus("Available");
                    updateLabelVisuals(label, "Available");
                    updateOverviewStats();
                    JOptionPane.showMessageDialog(view, "Room " + room.getRoomNumber() + " checked out successfully!");
                    
                    // Redirect to Billing Section
                    try {
                        JButton btnSystemSetting1 = (JButton) getPrivateField("btnSystemSetting1");
                        if (btnSystemSetting1 != null) {
                            btnSystemSetting1.doClick();
                        }
                    } catch (Exception ex) {
                        System.out.println("Error redirecting to billing: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to check out room.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void updateOverviewStats() {
        try {
            JLabel lblAvailableCount = (JLabel) getPrivateField("jLabel4");
            JLabel lblTotalCount = (JLabel) getPrivateField("jLabel7");
            JLabel lblOccupiedCount = (JLabel) getPrivateField("jLabel10");

            if (lblTotalCount != null) {
                lblTotalCount.setText(String.valueOf(dao.getTotalRoomsCount()));
            }
            if (lblOccupiedCount != null) {
                lblOccupiedCount.setText(String.valueOf(dao.getRoomsCountByStatus("Occupied")));
            }
            if (lblAvailableCount != null) {
                lblAvailableCount.setText(String.valueOf(dao.getRoomsCountByStatus("Available")));
            }
        } catch (Exception e) {
            System.out.println("Error updating overview stats: " + e.getMessage());
        }
    }

    private void setupSidebar() throws Exception {
        // Retrieve private navigation buttons
        JButton btnRooms = (JButton) getPrivateField("btnRooms"); // Guests
        JButton btnDiscounts = (JButton) getPrivateField("btnDiscounts"); // Bookings
        JButton btnStaffs = (JButton) getPrivateField("btnStaffs"); // Meal time
        JButton btnSystemSetting1 = (JButton) getPrivateField("btnSystemSetting1"); // Billing
        JButton btnSystemSetting = (JButton) getPrivateField("btnSystemSetting"); // Dashboard
        JButton btnReports = (JButton) getPrivateField("btnReports"); // Logout

        // Wire up Logout action
        if (btnReports != null) {
            btnReports.addActionListener(e -> {
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

        // Add action placeholders for other sidebar items to make dashboard responsive
        if (btnRooms != null) {
            btnRooms.addActionListener(e -> JOptionPane.showMessageDialog(view, "Navigating to Guests section."));
        }
        if (btnDiscounts != null) {
            btnDiscounts.addActionListener(e -> JOptionPane.showMessageDialog(view, "Navigating to Bookings section."));
        }
        if (btnStaffs != null) {
            btnStaffs.addActionListener(e -> JOptionPane.showMessageDialog(view, "Navigating to Meal time section."));
        }
        if (btnSystemSetting1 != null) {
            btnSystemSetting1.setContentAreaFilled(false); // Make Billing button transparent to match others
            btnSystemSetting1.addActionListener(e -> JOptionPane.showMessageDialog(view, "Navigating to Billing section."));
        }
        if (btnSystemSetting != null) {
            btnSystemSetting.addActionListener(e -> JOptionPane.showMessageDialog(view, "You are already on the Dashboard."));
        }
    }

    private Object getPrivateField(String fieldName) throws Exception {
        Field field = FrontDeskDashboard.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(view);
    }
}
