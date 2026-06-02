package dao;

import java.util.ArrayList;
import java.util.List;
import model.MealTimeModel;

/**
 * MealTimeDao manages meal time schedules in an in-memory data store.
 * Database connections are not required at this stage.
 */
public class MealTimeDao {
    // Static in-memory database representation
    private static final List<MealTimeModel> mealTimesList = new ArrayList<>();

    static {
        // Pre-populate with default data matching the UI mockup
        mealTimesList.add(new MealTimeModel("VIP PREFERRED SERVICE", "07:00 AM - 08:00 AM", "11:00 AM - 12:00 PM", "07:30 PM - 08:30 PM"));
        mealTimesList.add(new MealTimeModel("DOUBLE BED ACCOMMODATION", "08:00 AM - 09:00 AM", "12:00 PM - 01:00 PM", "08:30 PM - 09:30 PM"));
        mealTimesList.add(new MealTimeModel("SINGLE BED STANDARD", "09:00 AM - 10:00 AM", "01:00 PM - 02:00 PM", "09:30 PM - 10:30 PM"));
    }

    /**
     * Retrieves all meal schedule records.
     * @return List of MealTimeModel records
     */
    public List<MealTimeModel> getAllMealTimes() {
        return new ArrayList<>(mealTimesList);
    }

    /**
     * Adds a new meal schedule record.
     * @param mealTime The new record
     */
    public void addMealTime(MealTimeModel mealTime) {
        if (mealTime != null) {
            mealTimesList.add(mealTime);
        }
    }

    /**
     * Updates an existing meal schedule record matching the room type.
     * @param roomType Room type identifier
     * @param updatedMealTime The updated record properties
     * @return true if record was found and updated, false otherwise
     */
    public boolean updateMealTimeByRoom(String roomType, MealTimeModel updatedMealTime) {
        if (roomType == null || updatedMealTime == null) {
            return false;
        }
        for (int i = 0; i < mealTimesList.size(); i++) {
            MealTimeModel current = mealTimesList.get(i);
            if (current.getRoomType().equalsIgnoreCase(roomType)) {
                mealTimesList.set(i, updatedMealTime);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a meal schedule record.
     * @param roomType Room type identifier
     * @return true if matching record was found and deleted, false otherwise
     */
    public boolean deleteMealTimeByRoom(String roomType) {
        if (roomType == null) {
            return false;
        }
        return mealTimesList.removeIf(mealTime -> mealTime.getRoomType().equalsIgnoreCase(roomType));
    }
}
