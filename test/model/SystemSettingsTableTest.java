package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * [Test 2 of 5] JUnit test for `system_settings` table.
 * Exact record: HotelName="Hotel Redsun", HotelId="GH-001", Address="Baneshwor, Kathmandu", PanNumber="123456789", Owner="Ankit Goyala", Phone="+977-9843465098", Website="www.redsun.com"
 */
public class SystemSettingsTableTest {

    @Test
    public void testSystemSettingsTableRecord() {
        systemModel settings = new systemModel("Hotel Redsun", "GH-001", "Baneshwor, Kathmandu", "123456789", "Ankit Goyala",
            "We give the best service", "+977-9843465098", "www.redsun.com");

        assertEquals("Hotel Name must match system_settings table", "Hotel Redsun", settings.getHotelName());
        assertEquals("Hotel ID must match system_settings table", "GH-001", settings.getHotelId());
        assertEquals("Address must match system_settings table", "Baneshwor, Kathmandu", settings.getAddress());
        assertEquals("PAN Number must match system_settings table", "123456789", settings.getPanNumber());
        assertEquals("Owner must match system_settings table", "Ankit Goyala", settings.getOwner());
        assertEquals("Phone must match system_settings table", "+977-9843465098", settings.getPhone());
        assertEquals("Website must match system_settings table", "www.redsun.com", settings.getWebsite());
    }
}
