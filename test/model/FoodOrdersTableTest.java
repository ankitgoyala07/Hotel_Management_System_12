package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * [Test 9 of 12] JUnit test for `food_orders` table.
 * Exact record: ID=1, Name="Artisan Pour-Over Coffee", Price=8.0, Category="Beverages", Quantity=1
 */
public class FoodOrdersTableTest {

    @Test
    public void testFoodOrdersTableRecord() {
        OrderFoodModel item = new OrderFoodModel(1, "Artisan Pour-Over Coffee", "Freshly brewed coffee", 8.0, "Beverages");
        item.setQuantity(3);

        assertEquals("ID must match food_orders item record", 1, item.getId());
        assertEquals("Name must match food_orders item record", "Artisan Pour-Over Coffee", item.getName());
        assertEquals("Price must match food_orders item record", 8.0, item.getPrice(), 0.001);
        assertEquals("Quantity must match test setting", 3, item.getQuantity());
        assertEquals("Subtotal calculation must be price * quantity", 24.0, item.getSubtotal(), 0.001);
    }
}
