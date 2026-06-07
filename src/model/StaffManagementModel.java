package model;

/**
 * Model class holding staff details.
 *
 * @author i3
 */
public class StaffManagementModel {
    private String staffId;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String role;
    private String shift;

    // Default constructor
    public StaffManagementModel() {}

    // Parameterized constructor
    public StaffManagementModel(String staffId, String name, String phone, String email,
                                String address, String role, String shift) {
        this.staffId = staffId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
        this.shift = shift;
    }

    // Getters and Setters
    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
