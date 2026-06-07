package controller;

import dao.StaffManagementDao;
import model.StaffManagementModel;
import java.util.List;
import view.StaffManagement;

/**
 * Controller class coordinating interactions between StaffManagement View and StaffManagementModel/StaffManagementDao.
 *
 * @author i3
 */
public class StaffManagementController {
    private final StaffManagementDao dao = new StaffManagementDao();

    /**
     * Loads staff records from the database and updates the view table.
     *
     * @param view the StaffManagement frame view
     */
    public void loadStaffData(StaffManagement view) {
        if (view == null) return;
        List<StaffManagementModel> staffList = dao.getAllStaff();
        view.populateTable(staffList);
    }

    /**
     * Deletes a staff record by calling the DAO.
     *
     * @param staffId the unique identifier of the staff member
     * @return true if deletion is successful, false otherwise
     */
    public boolean deleteStaff(String staffId) {
        if (staffId == null || staffId.trim().isEmpty()) {
            return false;
        }
        return dao.deleteStaff(staffId);
    }
}
