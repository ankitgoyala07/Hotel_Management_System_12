package model;

/**
 * Model class holding hotel configuration details for system settings.
 *
 * @author i3
 */
public class systemModel {
    private String hotelName;
    private String hotelId;
    private String address;
    private String panNumber;
    private String owner;
    private String email;
    private String phone;
    private String website;

    // Default constructor
    public systemModel() {}

    // Parameterized constructor
    public systemModel(String hotelName, String hotelId, String address, String panNumber,
                       String owner, String email, String phone, String website) {
        this.hotelName = hotelName;
        this.hotelId = hotelId;
        this.address = address;
        this.panNumber = panNumber;
        this.owner = owner;
        this.email = email;
        this.phone = phone;
        this.website = website;
    }

    // Getters and Setters
    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
