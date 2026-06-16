package model;

/**
 * Model class for adding staff details. Extends StaffManagementModel to reuse the properties.
 *
 * @author i3
 */
public class AddStaffModel extends StaffManagementModel {
    // Default constructor
    public AddStaffModel() {
        super();
    }

    // Parameterized constructor
    public AddStaffModel(String staffId, String name, String phone, String email,
                         String address, String role, String shift) {
        super(staffId, name, phone, email, address, role, shift);
    }
}
