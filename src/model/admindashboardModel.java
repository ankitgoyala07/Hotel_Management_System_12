package model;

/**
 * Model class holding data attributes for Admin Dashboard metrics (rooms, check-ins, check-outs)
 */

public class admindashboardModel {
    private final int todayCheckIn;
    private final int todayCheckOut;
    private final int totalRooms;
    private final int availableRooms;
    private final int occupiedRooms;

    public admindashboardModel(int todayCheckIn, int todayCheckOut, int totalRooms, int availableRooms, int occupiedRooms) {
        this.todayCheckIn = todayCheckIn;
        this.todayCheckOut = todayCheckOut;
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.occupiedRooms = occupiedRooms;
    }

    public int getTodayCheckIn() {
        return todayCheckIn;
    }

    public int getTodayCheckOut() {
        return todayCheckOut;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public int getOccupiedRooms() {
        return occupiedRooms;
    }
}
