package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * [Test 10 of 12] JUnit test for `billings` table.
 * Exact record: GuestID="001", RoomID="101", StayPeriod="4 Nights", Nights=4, RoomRate=80.0, RoomService=20.0, FoodOrders=30.0, RoomType="Single", DiscountDeal="#1010"
 */
public class BillingsTableTest {

    @Test
    public void testBillingsTableRecordAndCalculations() {
        BillingModel bill = new BillingModel("001", "101", "Oct 14 - Oct 18 (4 Nights)", 4, 80.0, 20.0, 30.0, "Single", "#1010");

        assertEquals("Guest ID must match billings table record", "001", bill.getGuestId());
        assertEquals("Room ID must match billings table record", "101", bill.getRoomId());
        assertEquals("Nights must match billings table record", 4, bill.getNights());
        assertEquals("Stay amount calculation must be nights * roomRate", 320.0, bill.getStayAmount(), 0.001);
        assertEquals("Subtotal before discount must include room service and food", 370.0, bill.getSubtotalBeforeDiscount(), 0.001);
        assertTrue("Discount deal #1010 must activate discount", bill.hasDiscount());
        assertEquals("Discount amount (10%) must be correct", 37.0, bill.getDiscountAmount(), 0.001);
        assertEquals("Subtotal after discount must be correct", 333.0, bill.getSubtotal(), 0.001);
        assertEquals("Tax (8%) calculation must be correct", 26.64, bill.getTax(), 0.001);
        assertEquals("Grand total calculation must be correct", 359.64, bill.getGrandTotal(), 0.001);
    }
}
