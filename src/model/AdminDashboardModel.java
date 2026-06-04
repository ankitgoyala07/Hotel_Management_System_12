package model;

/**
 * Model class representing the dashboard statistics of the Hotel Management System.
 */
public class AdminDashboardModel {
    private int totalRooms;
    private int availableRooms;
    private int totalBookings;
    private int totalGuests;
    private double totalRevenue;
    private int totalMealOrders;

    public AdminDashboardModel() {
        // Default constructor
    }

    public AdminDashboardModel(int totalRooms, int availableRooms, int totalBookings, int totalGuests, double totalRevenue, int totalMealOrders) {
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.totalBookings = totalBookings;
        this.totalGuests = totalGuests;
        this.totalRevenue = totalRevenue;
        this.totalMealOrders = totalMealOrders;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    public int getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(int totalBookings) {
        this.totalBookings = totalBookings;
    }

    public int getTotalGuests() {
        return totalGuests;
    }

    public void setTotalGuests(int totalGuests) {
        this.totalGuests = totalGuests;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getTotalMealOrders() {
        return totalMealOrders;
    }

    public void setTotalMealOrders(int totalMealOrders) {
        this.totalMealOrders = totalMealOrders;
    }

    /**
     * Calculates the occupancy rate percentage.
     * 
     * @return Occupancy rate as a percentage (0-100).
     */
    public int getOccupancyRate() {
        if (totalRooms <= 0) {
            return 0;
        }
        int occupiedRooms = totalRooms - availableRooms;
        if (occupiedRooms < 0) {
            return 0;
        }
        return (int) Math.round(((double) occupiedRooms / totalRooms) * 100);
    }
}
