/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.OrderFoodModel;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for food ordering - handles data operations.
 * Database integration can be added later.
 *
 * @author Dell
 */
public class OrderFoodDao {

    /**
     * Returns the full menu as a list of OrderFoodModel objects.
     * In the future this will fetch from the database.
     *
     * @return List of all menu items
     */
    public List<OrderFoodModel> getAllMenuItems() {
        List<OrderFoodModel> menuItems = new ArrayList<>();

        // BREAKFAST
        menuItems.add(new OrderFoodModel(1, "Classic Continental",
                "Croissants, fruit, Greek yogurt, and jam.", 18.00, "BREAKFAST"));
        menuItems.add(new OrderFoodModel(2, "Avocado Sourdough Toast",
                "Avocado, poached eggs, chili, sourdough.", 22.00, "BREAKFAST"));

        // MAIN COURSE
        menuItems.add(new OrderFoodModel(3, "Pan-Seared Atlantic Salmon",
                "Quinoa, asparagus, citrus beurre blanc.", 34.00, "MAIN COURSE"));
        menuItems.add(new OrderFoodModel(4, "Truffle Wild Mushroom Risotto",
                "Mushrooms, Parmesan, black truffle.", 28.00, "MAIN COURSE"));
        menuItems.add(new OrderFoodModel(5, "Wagyu Beef Burger",
                "Gruyere, truffle aioli, brioche, fries.", 29.00, "MAIN COURSE"));

        // BEVERAGES
        menuItems.add(new OrderFoodModel(6, "Fresh Pressed Green Juice",
                "Kale, apple, cucumber, ginger.", 12.00, "BEVERAGES"));
        menuItems.add(new OrderFoodModel(7, "Artisan Pour-Over Coffee",
                "Ethiopian beans, light roast.", 8.00, "BEVERAGES"));

        return menuItems;
    }

    /**
     * Returns menu items filtered by category.
     *
     * @param category The category to filter by
     * @return Filtered list of menu items
     */
    public List<OrderFoodModel> getMenuItemsByCategory(String category) {
        List<OrderFoodModel> all = getAllMenuItems();
        List<OrderFoodModel> filtered = new ArrayList<>();
        for (OrderFoodModel item : all) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    /**
     * Searches menu items by name.
     *
     * @param keyword Search keyword
     * @return List of matching menu items
     */
    public List<OrderFoodModel> searchMenuItems(String keyword) {
        List<OrderFoodModel> all = getAllMenuItems();
        List<OrderFoodModel> results = new ArrayList<>();
        String lower = keyword.toLowerCase();
        for (OrderFoodModel item : all) {
            if (item.getName().toLowerCase().contains(lower)
                    || item.getDescription().toLowerCase().contains(lower)) {
                results.add(item);
            }
        }
        return results;
    }

    /**
     * Placeholder for saving an order to the database.
     * To be implemented when database is connected.
     *
     * @param orderedItems List of items in the order
     * @param roomId       The room ID for delivery
     * @param total        Total order amount
     * @return true if save was successful
     */
    public boolean saveOrder(List<OrderFoodModel> orderedItems, String roomId, double total) {
        // TODO: Implement database save when DB is ready
        // For now, just return true as a placeholder
        System.out.println("Order saved for Room: " + roomId + " | Total: $" + total);
        for (OrderFoodModel item : orderedItems) {
            System.out.println("  - " + item.getName()
                    + " x" + item.getQuantity()
                    + " = $" + item.getSubtotal());
        }
        return true;
    }
}
