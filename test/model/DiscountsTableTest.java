package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * [Test 6 of 12] JUnit test for `discounts` table.
 * Exact record: DealCode="#1010", DealName="Summer_Offer", ReservationsLeft=5, EndDate="2026-06-23", Status="ongoing"
 */
public class DiscountsTableTest {

    @Test
    public void testDiscountsTableRecord() {
        DiscountModel discount = new DiscountModel("#1010", "Summer_Offer", 5, "2026-06-23", "ongoing");

        assertEquals("Deal code must match discounts table record", "#1010", discount.getDealCode());
        assertEquals("Deal name must match discounts table record", "Summer_Offer", discount.getDealName());
        assertEquals("Reservations left must match discounts table record", 5, discount.getReservationsLeft());
        assertEquals("End date must match discounts table record", "2026-06-23", discount.getEndDate());
        assertEquals("Status must match discounts table record", "ongoing", discount.getStatus());
    }
}
