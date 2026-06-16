package model;

/**
 * Model class representing a room in the hotel management system.
 * Holds room number, type, and occupancy status.
 *
 * @author i3
 */
public class FrontdeskDeshboardModel {
    private String roomNumber;
    private String roomType;
    private String status;

    // Default constructor
    public FrontdeskDeshboardModel() {}

    // Parameterized constructor
    public FrontdeskDeshboardModel(String roomNumber, String roomType, String status) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.status = status;
    }

    // Getters and Setters
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
