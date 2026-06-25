package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * [Test 5 of 5] JUnit test for `room_categories` table.
 * Exact record: ID=1, RoomType="VIP", Price=300.00, RoomSize="85m²", BedType="King Size", Description="Our pinnacle of luxury featuring a private lounge..."
 */
public class RoomCategoriesTableTest {

    @Test
    public void testRoomCategoriesTableRecord() {
        BookRoomModel category = new BookRoomModel( 1,"VIP", 300.00, "85m²", "King Size",
            "Our pinnacle of luxury featuring a private lounge..."  );

        assertEquals("Category ID must match room_categories table record", 1, category.getId());
        assertEquals("Room Type must match room_categories table record", "VIP", category.getRoomType());
        assertEquals("Price must match room_categories table record", 300.00, category.getPrice(), 0.001);
        assertEquals("Room Size must match room_categories table record", "85m²", category.getRoomSize());
        assertEquals("Bed Type must match room_categories table record", "King Size", category.getBedType());
        assertTrue("Description must match room_categories table record", category.getDescription().startsWith("Our pinnacle of luxury"));
    }
}
