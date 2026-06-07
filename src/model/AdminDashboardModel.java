/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class AdminDashboardModel {

    private int todayCheckIn;
    private int todayCheckOut;
    private int totalRooms;
    private int availableRooms;
    private int occupiedRooms;

    private int totalBookings;
    private int totalGuests;
    private double totalRevenue;
    private int totalMealOrders;

    // Default constructor
    public AdminDashboardModel() {
    }

    // Constructor for AdminDashboardDAOImpl
    public AdminDashboardModel(int totalRooms, int availableRooms, int totalBookings, int totalGuests, double totalRevenue, int totalMealOrders) {
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.totalBookings = totalBookings;
        this.totalGuests = totalGuests;
        this.totalRevenue = totalRevenue;
        this.totalMealOrders = totalMealOrders;
    }

    // Getters
    public int getTodayCheckIn()   { return todayCheckIn; }
    public int getTodayCheckOut()  { return todayCheckOut; }
    public int getTotalRooms()     { return totalRooms; }
    public int getAvailableRooms() { return availableRooms; }
    public int getOccupiedRooms()  { return occupiedRooms; }
    public int getTotalBookings()  { return totalBookings; }
    public int getTotalGuests()    { return totalGuests; }
    public double getTotalRevenue() { return totalRevenue; }
    public int getTotalMealOrders() { return totalMealOrders; }

    // Setters
    public void setTodayCheckIn(int v)   { this.todayCheckIn = v; }
    public void setTodayCheckOut(int v)  { this.todayCheckOut = v; }
    public void setTotalRooms(int v)     { this.totalRooms = v; }
    public void setAvailableRooms(int v) { this.availableRooms = v; }
    public void setOccupiedRooms(int v)  { this.occupiedRooms = v; }
    public void setTotalBookings(int v)  { this.totalBookings = v; }
    public void setTotalGuests(int v)    { this.totalGuests = v; }
    public void setTotalRevenue(double v) { this.totalRevenue = v; }
    public void setTotalMealOrders(int v) { this.totalMealOrders = v; }
}