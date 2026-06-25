package model;

import org.junit.Test;
import java.sql.Date;
import static org.junit.Assert.*;

/**
 * [Test 11 of 12] JUnit test for `guest_details` table.
 * Exact record: GuestID=1, FullName="guest", Phone="9841006688", Email="guest@gmail.com", HomeAddress="Kathmandu", RoomNo=101, GuestNo=1, RoomType="Single", DiscountDeal="Summer_Offer"
 */
public class GuestDetailsTableTest {

    @Test
    public void testGuestDetailsTableRecord() {
        Date checkIn = Date.valueOf("2026-06-20");
        Date checkOut = Date.valueOf("2026-06-23");
        GuestDetails guest = new GuestDetails(1, "guest", "9841006688", "guest@gmail.com", "Kathmandu", 101, 1, "Single", checkIn, checkOut, "Summer_Offer");

        assertEquals("Guest ID must match guest_details table record", 1, guest.getId());
        assertEquals("Full Name must match guest_details table record", "guest", guest.getFULL_NAME());
        assertEquals("Phone Number must match guest_details table record", "9841006688", guest.getPHONE_NUMBER());
        assertEquals("Email Address must match guest_details table record", "guest@gmail.com", guest.getEMAIL_ADDRESS());
        assertEquals("Home Address must match guest_details table record", "Kathmandu", guest.getHomeAddress());
        assertEquals("Room Number must match guest_details table record", 101, guest.getROOM_NO());
        assertEquals("Guest Count must match guest_details table record", 1, guest.getGUEST_NO());
        assertEquals("Room Type must match guest_details table record", "Single", guest.getRoom_Type());
        assertEquals("Check-in Date must match guest_details table record", checkIn, guest.getCHECK_IN_DATE());
        assertEquals("Check-out Date must match guest_details table record", checkOut, guest.getCHECK_OUT_DATE());
        assertEquals("Discount deal must match guest_details table record", "Summer_Offer", guest.getDiscountDeal());
    }
}
