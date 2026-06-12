/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.OrderFoodDao;
import model.OrderFoodModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for Food Ordering.
 * Handles business logic between the View and DAO layers.
 *
 * @author Dell
 */
public class OrderFoodController {

    private final OrderFoodDao orderFoodDao;
    private final List<OrderFoodModel> currentOrder;
    private static final double DELIVERY_FEE = 5.00;

    public OrderFoodController() {
        this.orderFoodDao = new OrderFoodDao();
        this.currentOrder = new ArrayList<>();
    }

    /**
     * Returns all available menu items from DAO.
     *
     * @return List of all menu items
     */
    public List<OrderFoodModel> getAllMenuItems() {
        return orderFoodDao.getAllMenuItems();
    }

    /**
     * Returns menu items by category.
     *
     * @param category Category name
     * @return Filtered list
     */
    public List<OrderFoodModel> getMenuItemsByCategory(String category) {
        return orderFoodDao.getMenuItemsByCategory(category);
    }

    /**
     * Searches menu items by keyword.
     *
     * @param keyword Search text
     * @return Matching items
     */
    public List<OrderFoodModel> searchMenu(String keyword) {
        return orderFoodDao.searchMenuItems(keyword);
    }

    /**
     * Adds a menu item to the current order (or increments quantity if exists).
     *
     * @param item The menu item to add
     */
    public void addItemToOrder(OrderFoodModel item) {
        for (OrderFoodModel ordered : currentOrder) {
            if (ordered.getId() == item.getId()) {
                ordered.setQuantity(ordered.getQuantity() + 1);
                return;
            }
        }
        // Item not yet in order — add as new with quantity 1
        OrderFoodModel newEntry = new OrderFoodModel(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getCategory()
        );
        newEntry.setQuantity(1);
        currentOrder.add(newEntry);
    }

    /**
     * Removes an item completely from the current order.
     *
     * @param itemId ID of the item to remove
     */
    public void removeItemFromOrder(int itemId) {
        currentOrder.removeIf(item -> item.getId() == itemId);
    }

    /**
     * Increases quantity of an ordered item by 1.
     *
     * @param itemId ID of the item
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
     *
     * @param itemId ID of the item
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
     *
     * @return List of ordered items
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
     * Calculates the subtotal of current order (before delivery fee).
     *
     * @return Subtotal amount
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
     *
     * @return Delivery fee
     */
    public double getDeliveryFee() {
        return currentOrder.isEmpty() ? 0.0 : DELIVERY_FEE;
    }

    /**
     * Calculates the total amount including delivery fee.
     *
     * @return Total amount
     */
    public double getTotal() {
        return getSubtotal() + getDeliveryFee();
    }

    /**
     * Returns the total number of items in the order.
     *
     * @return Item count
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
     *
     * @param roomId Room ID for delivery
     * @return true if order placed successfully
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
}
