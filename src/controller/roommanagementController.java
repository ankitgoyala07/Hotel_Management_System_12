package controller;

/**
 * Controller class to coordinate room data between Room Management UI and room DAO
 */

import dao.roommanagementDAO;
import java.util.List;
import model.roommanagementModel;

public class roommanagementController {

    private final roommanagementDAO roomsDAO;

    public roommanagementController() {
        this.roomsDAO = new roommanagementDAO();
    }

    public List<roommanagementModel> getRooms() {
        return roomsDAO.getRooms();
    }
}
