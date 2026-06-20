/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.OrderFoodDao;
import model.OrderFoodModel;
import view.OrderFood;
import view.gest_dashbord;
import view.BookRoom;
import view.Feedback;
import view.login;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.MySqlConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for Food Ordering.
 * Handles business logic between the View and DAO layers.
 * Separates view elements from action listeners.
 *
 * @author Dell
 */
public class OrderFoodController {

    private final OrderFood view;
    private final OrderFoodDao orderFoodDao;
    private final List<OrderFoodModel> currentOrder;
    private static final double DELIVERY_FEE = 5.00;

    public OrderFoodController(OrderFood view) {
        this.view = view;
        this.orderFoodDao = new OrderFoodDao();
        this.currentOrder = new ArrayList<>();

        // Add default items from the mockup on initial load
        List<OrderFoodModel> menu = getAllMenuItems();
        if (menu.size() >= 7) {
            addItemToOrder(menu.get(4)); // Wagyu Beef Burger (ID 5)
            addItemToOrder(menu.get(6)); // Artisan Pour-Over Coffee (ID 7)
        }

        bindListeners();
        updateOrderUI();
    }

    private void bindListeners() {
        // Sidebar navigation links
        view.getBtnDashboard().addActionListener(e -> openDashboard());
        view.getBtnRoomBrowse().addActionListener(e -> openRoomBrowsing());
        view.getBtnOrderFood().addActionListener(e -> refreshPage());
        view.getBtnFeedback().addActionListener(e -> openFeedback());
        view.getBtnLogout().addActionListener(e -> logout());

        // Add buttons for menu items
        view.getBtnAdd1().addActionListener(e -> addMenuIndex(0));
        view.getBtnAdd2().addActionListener(e -> addMenuIndex(1));
        view.getBtnAdd3().addActionListener(e -> addMenuIndex(2));
        view.getBtnAdd4().addActionListener(e -> addMenuIndex(3));
        view.getBtnAdd5().addActionListener(e -> addMenuIndex(4));
        view.getBtnAdd6().addActionListener(e -> addMenuIndex(5));
        view.getBtnAdd7().addActionListener(e -> addMenuIndex(6));

        // Order Item 1 adjustment buttons
        view.getBtnItem1Minus().addActionListener(e -> decreaseItem1());
        view.getBtnItem1Plus().addActionListener(e -> increaseItem1());
        view.getBtnItem1Remove().addActionListener(e -> removeItem1());

        // Order Item 2 adjustment buttons
        view.getBtnItem2Minus().addActionListener(e -> decreaseItem2());
        view.getBtnItem2Plus().addActionListener(e -> increaseItem2());
        view.getBtnItem2Remove().addActionListener(e -> removeItem2());

        // Complete Order button
        view.getBtnComplete().addActionListener(e -> completeOrder());

        // Order Badge mouse listener
        view.getLblOrderBadge().setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        view.getLblOrderBadge().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                showFullOrderDialog();
            }
        });
    }

    // Navigation Actions
    private void openDashboard() {
        new GuestDashboardController(new gest_dashbord());
        view.dispose();
    }

    private void openRoomBrowsing() {
        BookRoom roomView = new BookRoom();
        new BookRoomController(roomView);
        roomView.setVisible(true);
        view.dispose();
    }

    private void refreshPage() {
        updateOrderUI();
    }

    private void openFeedback() {
        Feedback fbView = new Feedback();
        new FeedbackController(fbView);
        fbView.setVisible(true);
        view.dispose();
    }

    private void logout() {
        new LoginController();
        view.dispose();
    }

    // Menu Selection
    private void addMenuIndex(int index) {
        List<OrderFoodModel> menu = getAllMenuItems();
        if (index >= 0 && index < menu.size()) {
            addItemToOrder(menu.get(index));
            updateOrderUI();
        }
    }

    // Order Item Adjustments
    private void decreaseItem1() {
        if (!currentOrder.isEmpty()) {
            decreaseQuantity(currentOrder.get(0).getId());
            updateOrderUI();
        }
    }

    private void increaseItem1() {
        if (!currentOrder.isEmpty()) {
            increaseQuantity(currentOrder.get(0).getId());
            updateOrderUI();
        }
    }

    private void removeItem1() {
        if (!currentOrder.isEmpty()) {
            removeItemFromOrder(currentOrder.get(0).getId());
            updateOrderUI();
        }
    }

    private void decreaseItem2() {
        if (currentOrder.size() > 1) {
            decreaseQuantity(currentOrder.get(1).getId());
            updateOrderUI();
        }
    }

    private void increaseItem2() {
        if (currentOrder.size() > 1) {
            increaseQuantity(currentOrder.get(1).getId());
            updateOrderUI();
        }
    }

    private void removeItem2() {
        if (currentOrder.size() > 1) {
            removeItemFromOrder(currentOrder.get(1).getId());
            updateOrderUI();
        }
    }

    // Complete Order Checkout
    private void completeOrder() {
        String roomId = view.getTxtRoomId().getText().trim();
        if (currentOrder.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Your order is empty!", "Empty Order", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (roomId.isEmpty() || roomId.equals("Enter room id")) {
            JOptionPane.showMessageDialog(view, "Please enter your Room ID for delivery!", "Room ID Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!isValidRoomNo(roomId)) {
            JOptionPane.showMessageDialog(view, "Invalid room number! You can only order food to your booked room.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean success = placeOrder(roomId);
        if (success) {
            JOptionPane.showMessageDialog(view, "Order placed successfully!", "Order Success", JOptionPane.INFORMATION_MESSAGE);
            view.getTxtRoomId().setText("Enter room id");
            updateOrderUI();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to place order.", "Order Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // UI Rendering helpers
    private void updateOrderUI() {
        int totalItems = getTotalItemCount();
        view.getLblOrderBadge().setText(String.valueOf(totalItems));

        // Row 1
        if (currentOrder.size() > 0) {
            OrderFoodModel item1 = currentOrder.get(0);
            view.getLblOrderItem1Name().setText(item1.getName());
            view.getLblOrderItem1Price().setText(String.format("$%.2f", item1.getPrice() * item1.getQuantity()));
            view.getLblOrderItem1Qty().setText(String.valueOf(item1.getQuantity()));
            setRow1Visible(true);
        } else {
            setRow1Visible(false);
        }

        // Row 2
        if (currentOrder.size() > 1) {
            OrderFoodModel item2 = currentOrder.get(1);
            view.getLblOrderItem2Name().setText(item2.getName());
            view.getLblOrderItem2Price().setText(String.format("$%.2f", item2.getPrice() * item2.getQuantity()));
            view.getLblOrderItem2Qty().setText(String.valueOf(item2.getQuantity()));
            setRow2Visible(true);
        } else {
            setRow2Visible(false);
        }

        // Totals
        view.getLblTotalValue().setText(String.format("$%.2f", getTotal()));
    }

    private void setRow1Visible(boolean visible) {
        view.getLblOrderItem1Name().setVisible(visible);
        view.getLblOrderItem1Price().setVisible(visible);
        view.getLblOrderItem1Qty().setVisible(visible);
        view.getBtnItem1Minus().setVisible(visible);
        view.getBtnItem1Plus().setVisible(visible);
        view.getBtnItem1Remove().setVisible(visible);
    }

    private void setRow2Visible(boolean visible) {
        view.getLblOrderItem2Name().setVisible(visible);
        view.getLblOrderItem2Price().setVisible(visible);
        view.getLblOrderItem2Qty().setVisible(visible);
        view.getBtnItem2Minus().setVisible(visible);
        view.getBtnItem2Plus().setVisible(visible);
        view.getBtnItem2Remove().setVisible(visible);
    }

    /**
     * Returns all available menu items from DAO.
     */
    public List<OrderFoodModel> getAllMenuItems() {
        return orderFoodDao.getAllMenuItems();
    }

    /**
     * Returns menu items by category.
     */
    public List<OrderFoodModel> getMenuItemsByCategory(String category) {
        return orderFoodDao.getMenuItemsByCategory(category);
    }

    /**
     * Searches menu items by keyword.
     */
    public List<OrderFoodModel> searchMenu(String keyword) {
        return orderFoodDao.searchMenuItems(keyword);
    }

    /**
     * Adds a menu item to the current order (or increments quantity if exists).
     */
    public void addItemToOrder(OrderFoodModel item) {
        for (int i = 0; i < currentOrder.size(); i++) {
            OrderFoodModel ordered = currentOrder.get(i);
            if (ordered.getId() == item.getId()) {
                ordered.setQuantity(ordered.getQuantity() + 1);
                // Move it to the front of the list to show it in the fixed 2-row order list
                currentOrder.remove(i);
                currentOrder.add(0, ordered);
                return;
            }
        }
        OrderFoodModel newEntry = new OrderFoodModel(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getCategory()
        );
        newEntry.setQuantity(1);
        currentOrder.add(0, newEntry);
    }

    /**
     * Removes an item completely from the current order.
     */
    public void removeItemFromOrder(int itemId) {
        currentOrder.removeIf(item -> item.getId() == itemId);
    }

    /**
     * Increases quantity of an ordered item by 1.
     */
    public void increaseQuantity(int itemId) {
        for (OrderFoodModel item : currentOrder) {
            if (item.getId() == itemId) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
    }

    /**
     * Decreases quantity of an ordered item by 1. Removes if quantity reaches 0.
     */
    public void decreaseQuantity(int itemId) {
        for (OrderFoodModel item : currentOrder) {
            if (item.getId() == itemId) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                } else {
                    removeItemFromOrder(itemId);
                }
                return;
            }
        }
    }

    /**
     * Returns the current order list.
     */
    public List<OrderFoodModel> getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Clears all items from the current order.
     */
    public void clearOrder() {
        currentOrder.clear();
    }

    /**
     * Calculates the subtotal of current order.
     */
    public double getSubtotal() {
        double subtotal = 0;
        for (OrderFoodModel item : currentOrder) {
            subtotal += item.getSubtotal();
        }
        return subtotal;
    }

    /**
     * Returns the fixed delivery fee.
     */
    public double getDeliveryFee() {
        return currentOrder.isEmpty() ? 0.0 : DELIVERY_FEE;
    }

    /**
     * Calculates the total amount including delivery fee.
     */
    public double getTotal() {
        return getSubtotal() + getDeliveryFee();
    }

    /**
     * Returns the total number of items in the order.
     */
    public int getTotalItemCount() {
        int count = 0;
        for (OrderFoodModel item : currentOrder) {
            count += item.getQuantity();
        }
        return count;
    }

    /**
     * Places the order by saving it via DAO.
     */
    public boolean placeOrder(String roomId) {
        if (currentOrder.isEmpty()) {
            return false;
        }
        boolean success = orderFoodDao.saveOrder(currentOrder, roomId, getTotal());
        if (success) {
            clearOrder();
        }
        return success;
    }

    private void showFullOrderDialog() {
        if (currentOrder.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Your order is empty!", "Order Status", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        javax.swing.JDialog dialog = new javax.swing.JDialog(view, "Full Order List", true);
        dialog.setUndecorated(true);
        dialog.setSize(350, 450);
        dialog.setLocationRelativeTo(view);
        
        // Custom root panel with border
        javax.swing.JPanel rootPanel = new javax.swing.JPanel();
        rootPanel.setLayout(new java.awt.BorderLayout());
        rootPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 99, 235), 2));
        rootPanel.setBackground(java.awt.Color.WHITE);

        // Header Panel
        javax.swing.JPanel headerPanel = new javax.swing.JPanel();
        headerPanel.setLayout(new java.awt.BorderLayout());
        headerPanel.setBackground(new java.awt.Color(30, 35, 55)); // Matches the dark complete order button
        headerPanel.setPreferredSize(new java.awt.Dimension(350, 40));

        javax.swing.JLabel titleLabel = new javax.swing.JLabel("  Your Full Order");
        titleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        titleLabel.setForeground(java.awt.Color.WHITE);
        headerPanel.add(titleLabel, java.awt.BorderLayout.WEST);

        javax.swing.JButton closeBtn = new javax.swing.JButton("✕");
        closeBtn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        closeBtn.setForeground(java.awt.Color.WHITE);
        closeBtn.setBorderPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dialog.dispose());
        headerPanel.add(closeBtn, java.awt.BorderLayout.EAST);

        rootPanel.add(headerPanel, java.awt.BorderLayout.NORTH);

        // Content Panel (scrollable)
        javax.swing.JPanel listPanel = new javax.swing.JPanel();
        listPanel.setLayout(new javax.swing.BoxLayout(listPanel, javax.swing.BoxLayout.Y_AXIS));
        listPanel.setBackground(java.awt.Color.WHITE);

        for (OrderFoodModel item : currentOrder) {
            javax.swing.JPanel itemPanel = new javax.swing.JPanel();
            itemPanel.setLayout(new java.awt.BorderLayout());
            itemPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(220, 227, 236)),
                javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            itemPanel.setBackground(java.awt.Color.WHITE);

            // Left side: Name & details
            javax.swing.JPanel textPanel = new javax.swing.JPanel();
            textPanel.setLayout(new java.awt.GridLayout(2, 1));
            textPanel.setBackground(java.awt.Color.WHITE);

            javax.swing.JLabel nameLabel = new javax.swing.JLabel(item.getName());
            nameLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
            nameLabel.setForeground(new java.awt.Color(25, 30, 50));
            textPanel.add(nameLabel);

            javax.swing.JLabel descLabel = new javax.swing.JLabel("Qty: " + item.getQuantity() + " | Price: " + String.format("$%.2f", item.getPrice()));
            descLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 10));
            descLabel.setForeground(new java.awt.Color(130, 135, 155));
            textPanel.add(descLabel);

            itemPanel.add(textPanel, java.awt.BorderLayout.WEST);

            // Right side: Price & Remove button
            javax.swing.JPanel rightPanel = new javax.swing.JPanel();
            rightPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));
            rightPanel.setBackground(java.awt.Color.WHITE);

            javax.swing.JLabel priceLabel = new javax.swing.JLabel(String.format("$%.2f", item.getPrice() * item.getQuantity()));
            priceLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
            priceLabel.setForeground(new java.awt.Color(25, 30, 50));
            rightPanel.add(priceLabel);

            javax.swing.JButton removeBtn = new javax.swing.JButton("Remove");
            removeBtn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 10));
            removeBtn.setForeground(java.awt.Color.RED);
            removeBtn.setBorderPainted(false);
            removeBtn.setContentAreaFilled(false);
            removeBtn.setFocusPainted(false);
            removeBtn.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
            removeBtn.addActionListener(e -> {
                removeItemFromOrder(item.getId());
                updateOrderUI();
                dialog.dispose();
                // Reopen the dialog to refresh if there are items left
                if (!currentOrder.isEmpty()) {
                    showFullOrderDialog();
                }
            });
            rightPanel.add(removeBtn);

            itemPanel.add(rightPanel, java.awt.BorderLayout.EAST);
            listPanel.add(itemPanel);
        }

        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        rootPanel.add(scrollPane, java.awt.BorderLayout.CENTER);

        // Bottom Summary Panel
        javax.swing.JPanel bottomPanel = new javax.swing.JPanel();
        bottomPanel.setLayout(new java.awt.GridLayout(3, 2, 0, 5));
        bottomPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 15, 20));
        bottomPanel.setBackground(new java.awt.Color(235, 242, 255));

        javax.swing.JLabel subtotalLbl = new javax.swing.JLabel("Subtotal");
        subtotalLbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        subtotalLbl.setForeground(new java.awt.Color(100, 108, 130));
        bottomPanel.add(subtotalLbl);

        javax.swing.JLabel subtotalVal = new javax.swing.JLabel(String.format("$%.2f", getSubtotal()));
        subtotalVal.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        subtotalVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        subtotalVal.setForeground(new java.awt.Color(35, 40, 60));
        bottomPanel.add(subtotalVal);

        javax.swing.JLabel deliveryLbl = new javax.swing.JLabel("Delivery Fee");
        deliveryLbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        deliveryLbl.setForeground(new java.awt.Color(100, 108, 130));
        bottomPanel.add(deliveryLbl);

        javax.swing.JLabel deliveryVal = new javax.swing.JLabel(String.format("$%.2f", getDeliveryFee()));
        deliveryVal.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        deliveryVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        deliveryVal.setForeground(new java.awt.Color(35, 40, 60));
        bottomPanel.add(deliveryVal);

        javax.swing.JLabel totalLbl = new javax.swing.JLabel("Total");
        totalLbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        totalLbl.setForeground(new java.awt.Color(25, 30, 50));
        bottomPanel.add(totalLbl);

        javax.swing.JLabel totalVal = new javax.swing.JLabel(String.format("$%.2f", getTotal()));
        totalVal.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        totalVal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalVal.setForeground(new java.awt.Color(25, 30, 50));
        bottomPanel.add(totalVal);

        rootPanel.add(bottomPanel, java.awt.BorderLayout.SOUTH);

        dialog.add(rootPanel);
        dialog.setVisible(true);
    }

    private boolean isValidRoomNo(String enteredRoomNoStr) {
        String username = LoginController.loggedInUsername;
        if (username == null) {
            return false;
        }

        int enteredRoomNo;
        try {
            enteredRoomNo = Integer.parseInt(enteredRoomNoStr.trim());
        } catch (NumberFormatException e) {
            return false;
        }

        MySqlConnection mysql = new MySqlConnection();
        try (Connection conn = mysql.Openconnection()) {
            if (conn == null) return false;

            // 1. Get email and phone from users table
            String email = null;
            String phone = null;
            String userSql = "SELECT email, phone FROM users WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(userSql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        email = rs.getString("email");
                        phone = rs.getString("phone");
                    }
                }
            }

            if (email != null || phone != null) {
                // 2. Get room number from guest_details table
                String guestSql = "SELECT room_no FROM guest_details WHERE email_address = ? OR phone_number = ? ORDER BY guest_id DESC LIMIT 1";
                try (PreparedStatement ps = conn.prepareStatement(guestSql)) {
                    ps.setString(1, email);
                    ps.setString(2, phone);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            int bookedRoomNo = rs.getInt("room_no");
                            return enteredRoomNo == bookedRoomNo;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error validating room number in food order: " + e.getMessage());
        }
        return false;
    }
}
