/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class admindashboardmodel {

    private int todayCheckIn;
    private int todayCheckOut;
    private int totalRooms;
    private int availableRooms;
    private int occupiedRooms;

    // Getters
    public int getTodayCheckIn()   { return todayCheckIn; }
    public int getTodayCheckOut()  { return todayCheckOut; }
    public int getTotalRooms()     { return totalRooms; }
    public int getAvailableRooms() { return availableRooms; }
    public int getOccupiedRooms()  { return occupiedRooms; }

    // Setters
    public void setTodayCheckIn(int v)   { this.todayCheckIn = v; }
    public void setTodayCheckOut(int v)  { this.todayCheckOut = v; }
    public void setTotalRooms(int v)     { this.totalRooms = v; }
    public void setAvailableRooms(int v) { this.availableRooms = v; }
    public void setOccupiedRooms(int v)  { this.occupiedRooms = v; }
}