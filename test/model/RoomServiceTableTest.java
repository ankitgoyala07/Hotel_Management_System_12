package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * [Test 8 of 12] JUnit test for `room_service` table.
 * Exact record: ID=9, ServiceType="Extra Blanket", RoomNo=201, Instructions="Ordered via Room Service Menu"
 */
public class RoomServiceTableTest {

    @Test
    public void testRoomServiceTableRecord() {
        RoomServiceModel roomService = new RoomServiceModel(9, "Extra Blanket", 201, "Ordered via Room Service Menu");

        assertEquals("ID must match room_service table record", 9, roomService.getId());
        assertEquals("Service type must match room_service table record", "Extra Blanket", roomService.getServiceType());
        assertEquals("Room number must match room_service table record", 201, roomService.getRoomNo());
        assertEquals("Instructions must match room_service table record", "Ordered via Room Service Menu", roomService.getInstructions());
    }
}
