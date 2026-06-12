package model;

import java.sql.Date;

public class User {
    private int id;
    private String name;
    private String email;
    private String roomType;
    private Date checkIn;
    private Date checkOut;

    // Default constructor
    public User() {}

    // Constructor with id
    public User(int id, String name, String email, String roomType, Date checkIn, Date checkOut) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    // Constructor without id
    public User(String name, String email, String roomType, Date checkIn, Date checkOut) {
        this.name = name;
        this.email = email;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
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
}
