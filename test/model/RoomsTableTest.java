package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * [Test 4 of 5] JUnit test for `rooms` table.
 * Exact record: RoomNumber="101", RoomType="Single", RoomFloor="Floor - 1", Facilities="AC, shower, bed, TV", Status="Available", Price=200.00
 */
public class RoomsTableTest {

    @Test
    public void testRoomsTableRecord() {
        roommanagementModel room = new roommanagementModel( "101", "Single", "Floor - 1", "AC, shower, bed, TV",
            "Available", 200.00 );

        assertEquals("Formatted Room Number must be #101", "#101", room.getRoomNumber());
        assertEquals("Room Type mapped must be Single bed", "Single bed", room.getRoomType());
        assertEquals("Room Floor must match rooms table record", "Floor - 1", room.getRoomFloor());
        assertEquals("Facilities must match rooms table record", "AC, shower, bed, TV", room.getRoomFacility());
        assertEquals("Status must match rooms table record", "Available", room.getStatus());
        assertEquals("Price per night must match rooms table record", 200.00, room.getPricePerNight(), 0.001);
    }
}
