package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * [Test 7 of 12] JUnit test for `mealtime` table.
 * Exact record: RoomType="Double Bedrooms", BreakfastTiming="08:00 AM - 09:00 AM", LunchTiming="12:00 PM - 01:00 PM", DinnerTiming="08:30 PM - 09:30 PM"
 */
public class MealtimeTableTest {

    @Test
    public void testMealtimeTableRecord() {
        MealTimeModel mealTime = new MealTimeModel(
            "Double Bedrooms",
            "08:00 AM - 09:00 AM",
            "12:00 PM - 01:00 PM",
            "08:30 PM - 09:30 PM"
        );

        assertEquals("Room type must match mealtime table record", "Double Bedrooms", mealTime.getRoomType());
        assertEquals("Breakfast timing must match mealtime table record", "08:00 AM - 09:00 AM", mealTime.getBreakfastTiming());
        assertEquals("Lunch timing must match mealtime table record", "12:00 PM - 01:00 PM", mealTime.getLunchTiming());
        assertEquals("Dinner timing must match mealtime table record", "08:30 PM - 09:30 PM", mealTime.getDinnerTiming());
    }
}
