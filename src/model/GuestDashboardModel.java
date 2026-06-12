package model;

import java.sql.Date;

public class GuestDashboardModel {
    private int id;
    private String name;
    private String email;
    private String roomType;
    private Date checkIn;
    private Date checkOut;
    private int guestsCount;
    private double expenses;

    // Default constructor
    public GuestDashboardModel() {}

    // Constructor with id
    public GuestDashboardModel(int id, String name, String email, String roomType, Date checkIn, Date checkOut, int guestsCount, double expenses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guestsCount = guestsCount;
        this.expenses = expenses;
    }

    // Constructor without id
    public GuestDashboardModel(String name, String email, String roomType, Date checkIn, Date checkOut, int guestsCount, double expenses) {
        this.name = name;
        this.email = email;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guestsCount = guestsCount;
        this.expenses = expenses;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public Date getCheckIn() { return checkIn; }
    public void setCheckIn(Date checkIn) { this.checkIn = checkIn; }

    public Date getCheckOut() { return checkOut; }
    public void setCheckOut(Date checkOut) { this.checkOut = checkOut; }

    public int getGuestsCount() { return guestsCount; }
    public void setGuestsCount(int guestsCount) { this.guestsCount = guestsCount; }

    public double getExpenses() { return expenses; }
    public void setExpenses(double expenses) { this.expenses = expenses; }
}
// git