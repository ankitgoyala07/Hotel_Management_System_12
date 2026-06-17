package controller;

import dao.roommanagementDAO;
import model.roommanagementModel;
import view.roommanagement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class to handle business logic for Room Management.
 * Manages table loading, filters, adding new rooms, and navigation.
 */
public class roommanagementController {
    private final roommanagement view;
    private final roommanagementDAO dao;
    private List<roommanagementModel> allRooms;

    public roommanagementController() {
        this.view = new roommanagement();
        this.dao = new roommanagementDAO();
        initController();
    }

    private void initController() {
        // Load rooms from database
        refreshRoomsList();

        // 1. Filter action listeners
        if (view.getBtnAllRooms() != null) {
            view.getBtnAllRooms().addActionListener(e -> loadRoomTable(allRooms));
        }
        if (view.getBtnAvailableRooms() != null) {
            view.getBtnAvailableRooms().addActionListener(e -> {
                List<roommanagementModel> available = allRooms.stream()
                    .filter(r -> r.getStatus().equalsIgnoreCase("Available"))
                    .collect(Collectors.toList());
                loadRoomTable(available);
            });
        }
        if (view.getBtnBookedRooms() != null) {
            view.getBtnBookedRooms().addActionListener(e -> {
                List<roommanagementModel> booked = allRooms.stream()
                    .filter(r -> r.getStatus().equalsIgnoreCase("Occupied") || r.getStatus().equalsIgnoreCase("Booked"))
                    .collect(Collectors.toList());
                loadRoomTable(booked);
            });
        }

        // 2. Add Room action listener
        if (view.getBtnAddRoom() != null) {
            view.getBtnAddRoom().addActionListener(e -> handleAddRoom());
        }

        // 3. Sidebar navigation listeners
        if (view.getBtnDashboard() != null) {
            view.getBtnDashboard().addActionListener(e -> {
                new admindashboardController();
                view.dispose();
            });
        }
        if (view.getBtnDiscount() != null) {
            view.getBtnDiscount().addActionListener(e -> {
                new BookingController();
                view.dispose();
            });
        }
        if (view.getBtnLogout() != null) {
            view.getBtnLogout().addActionListener(e -> {
                new LoginController();
                view.dispose();
            });
        }

        // Display view
        view.setVisible(true);
    }

    private void refreshRoomsList() {
        allRooms = dao.getRooms();
        loadRoomTable(allRooms);
    }

    private void loadRoomTable(List<roommanagementModel> rooms) {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);
        for (roommanagementModel room : rooms) {
            model.addRow(new Object[]{
                room.getRoomNumber(),
                room.getRoomType(),
                room.getRoomFloor(),
                room.getRoomFacility(),
                room.getStatus()
            });
        }
    }

    private void handleAddRoom() {
        JTextField txtNumber = new JTextField();
        JComboBox<String> comboType = new JComboBox<>(new String[]{"Single", "Double", "Suite", "Deluxe"});
        JTextField txtPrice = new JTextField("150.00");

        Object[] message = {
            "Room Number:", txtNumber,
            "Room Type:", comboType,
            "Price per Night ($):", txtPrice
        };

        int option = JOptionPane.showConfirmDialog(view, message, "Add Room Details", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String roomNum = txtNumber.getText().trim();
            String roomType = (String) comboType.getSelectedItem();
            String priceStr = txtPrice.getText().trim();

            if (roomNum.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Room Number is required.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid price format.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Save to database
            roommanagementModel newRoom = new roommanagementModel(roomNum, roomType, "Available", price);
            boolean success = dao.addRoom(newRoom);
            if (success) {
                JOptionPane.showMessageDialog(view, "Room added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshRoomsList();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add room. (Possibly duplicate Room Number)", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
