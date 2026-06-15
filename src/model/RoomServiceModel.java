package model;

/**
 * Model representing room service requests.
 * Follows the standard getters, setters, and constructors format.
 */
public class RoomServiceModel {
    private int id;
    private String serviceType;
    private int roomNo;
    private String instructions;

    // Default Constructor
    public RoomServiceModel() {
    }

    // Constructor without id (for insertion)
    public RoomServiceModel(String serviceType, int roomNo, String instructions) {
        this.serviceType = serviceType;
        this.roomNo = roomNo;
        this.instructions = instructions;
    }

    // Full Constructor
    public RoomServiceModel(int id, String serviceType, int roomNo, String instructions) {
        this.id = id;
        this.serviceType = serviceType;
        this.roomNo = roomNo;
        this.instructions = instructions;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
// git