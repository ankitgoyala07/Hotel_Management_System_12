package model;

import java.sql.Date;

/**
 * Model representing a guest record in the Guest Management table.
 */
public class GuestManagementModel {
    private int guestId;
    private String name;
    private String room;
    private String status;
    private Date checkIn;
    private Date checkOut;

    public GuestManagementModel(int guestId, String name, String room, String status, Date checkIn, Date checkOut) {
        this.guestId = guestId;
        this.name = name;
        this.room = room;
        this.status = status;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }
}
