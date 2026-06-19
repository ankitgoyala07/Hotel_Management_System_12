package model;

/**
 * MealTimeModel represents the data structure for hotel meal schedules.
 */
public class MealTimeModel {
    private String roomType;
    private String breakfastTiming;
    private String lunchTiming;
    private String dinnerTiming;

    /**
     * Default constructor.
     */
    public MealTimeModel() {
    }

    /**
     * Parameterized constructor.
     * @param roomType Room category/type name
     * @param breakfastTiming Timing range for breakfast
     * @param lunchTiming Timing range for lunch
     * @param dinnerTiming Timing range for dinner
     */
    public MealTimeModel(String roomType, String breakfastTiming, String lunchTiming, String dinnerTiming) {
        this.roomType = roomType;
        this.breakfastTiming = breakfastTiming;
        this.lunchTiming = lunchTiming;
        this.dinnerTiming = dinnerTiming;
    }

    // Getters and Setters
    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getBreakfastTiming() {
        return breakfastTiming;
    }

    public void setBreakfastTiming(String breakfastTiming) {
        this.breakfastTiming = breakfastTiming;
    }

    public String getLunchTiming() {
        return lunchTiming;
    }

    public void setLunchTiming(String lunchTiming) {
        this.lunchTiming = lunchTiming;
    }

    public String getDinnerTiming() {
        return dinnerTiming;
    }

    public void setDinnerTiming(String dinnerTiming) {
        this.dinnerTiming = dinnerTiming;
    }
}
