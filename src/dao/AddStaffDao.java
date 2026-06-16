package dao;

import model.AddStaffModel;

/**
 * Data Access Object for handling staff additions.
 * Delegates to StaffManagementDao to keep core code unified.
 *
 * @author i3
 */
public class AddStaffDao {
    private final StaffManagementDao staffDao = new StaffManagementDao();

    /**
     * Inserts a new staff record into the database.
     *
     * @param staff the AddStaffModel object
     * @return true if successful, false otherwise
     */
    public boolean insertStaff(AddStaffModel staff) {
        return staffDao.insertStaff(staff);
    }

    /**
     * Checks if a staff ID already exists in the database.
     *
     * @param staffId the staff identifier
     * @return true if exists, false otherwise
     */
    public boolean staffExists(String staffId) {
        return staffDao.staffExists(staffId);
    }
}
