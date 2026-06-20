package model;

/**
 * Model class holding data attributes for Admin Dashboard metrics (rooms, check-ins, check-outs, and staff counts)
 */
public class admindashboardModel {
    private final int todayCheckIn;
    private final int todayCheckOut;
    private final int totalRooms;
    private final int availableRooms;
    private final int occupiedRooms;

    private int totalStaffs;
    private int frontdeskStaff;
    private int chefStaff;
    private int helperStaff;
    private int cleanerStaff;

    public admindashboardModel(int todayCheckIn, int todayCheckOut, int totalRooms, int availableRooms, int occupiedRooms) {
        this.todayCheckIn = todayCheckIn;
        this.todayCheckOut = todayCheckOut;
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.occupiedRooms = occupiedRooms;
    }

    public admindashboardModel(int todayCheckIn, int todayCheckOut, int totalRooms, int availableRooms, int occupiedRooms,
                               int totalStaffs, int frontdeskStaff, int chefStaff, int helperStaff, int cleanerStaff) {
        this.todayCheckIn = todayCheckIn;
        this.todayCheckOut = todayCheckOut;
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.occupiedRooms = occupiedRooms;
        this.totalStaffs = totalStaffs;
        this.frontdeskStaff = frontdeskStaff;
        this.chefStaff = chefStaff;
        this.helperStaff = helperStaff;
        this.cleanerStaff = cleanerStaff;
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

    public int getTotalStaffs() {
        return totalStaffs;
    }

    public int getFrontdeskStaff() {
        return frontdeskStaff;
    }

    public int getChefStaff() {
        return chefStaff;
    }

    public int getHelperStaff() {
        return helperStaff;
    }

    public int getCleanerStaff() {
        return cleanerStaff;
    }
}
