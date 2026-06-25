package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * [Test 12 of 12] JUnit test for `feedback` table.
 * Exact record: ID=1, ServiceRating=5, CleanlinessRating=5, FoodRating=4, ReviewText="Great service and clean rooms"
 */
public class FeedbackTableTest {

    @Test
    public void testFeedbackTableRecord() {
        FeedbackModel feedback = new FeedbackModel(1, 5, 5, 4, "Great service and clean rooms");

        assertEquals("ID must match feedback table record", 1, feedback.getId());
        assertEquals("Service rating must match feedback table record", 5, feedback.getServiceRating());
        assertEquals("Cleanliness rating must match feedback table record", 5, feedback.getCleanlinessRating());
        assertEquals("Food rating must match feedback table record", 4, feedback.getFoodRating());
        assertEquals("Review text must match feedback table record", "Great service and clean rooms", feedback.getReviewText());
    }
}
