package model;

/**
 * Model representing room information.
 * Follows the standard getters, setters, and constructors format.
 */
public class BookRoomModel {
    private int id;
    private String roomType;
    private double price;
    private String roomSize;
    private String bedType;
    private String description;

    // Default Constructor
    public BookRoomModel() {
    }

    // Constructor without id (for creating new rooms)
    public BookRoomModel(String roomType, double price, String roomSize, String bedType, String description) {
        this.roomType = roomType;
        this.price = price;
        this.roomSize = roomSize;
        this.bedType = bedType;
        this.description = description;
    }

    // Full Constructor
    public BookRoomModel(int id, String roomType, double price, String roomSize, String bedType, String description) {
        this.id = id;
        this.roomType = roomType;
        this.price = price;
        this.roomSize = roomSize;
        this.bedType = bedType;
        this.description = description;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
