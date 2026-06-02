package controller;

import dao.MealTimeDao;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.MealTimeModel;
import view.MealTime;

/**
 * MealTimeController coordinates interactions between the MealTime JFrame View
 * and the MealTimeDao data layer.
 */
public class MealTimeController {
    private final MealTime view;
    private final MealTimeDao dao;

    /**
     * Controller constructor.
     * @param view The JFrame view class for MealTime
     */
    public MealTimeController(MealTime view) {
        this.view = view;
        this.dao = new MealTimeDao();
    }

    /**
     * Fetches all meal schedules from the DAO layer and populates the table in the view.
     */
    public void loadTableData() {
        if (view == null || view.getjTableSchedule() == null) {
            return;
        }

        DefaultTableModel model = (DefaultTableModel) view.getjTableSchedule().getModel();
        
        // Reset/Clear existing table rows
        model.setRowCount(0);

        // Fetch data
        List<MealTimeModel> list = dao.getAllMealTimes();

        // Populate table model
        for (MealTimeModel item : list) {
            model.addRow(new Object[]{
                item.getRoomType(),
                item.getBreakfastTiming(),
                item.getLunchTiming(),
                item.getDinnerTiming()
            });
        }
    }

    /**
     * Updates a meal time record and refreshes the view table.
     * @param roomType Room category identifier
     * @param breakfast Timing range for breakfast
     * @param lunch Timing range for lunch
     * @param dinner Timing range for dinner
     * @return true if successful, false otherwise
     */
    public boolean saveMealTime(String roomType, String breakfast, String lunch, String dinner) {
        MealTimeModel updated = new MealTimeModel(roomType, breakfast, lunch, dinner);
        boolean success = dao.updateMealTimeByRoom(roomType, updated);
        if (success) {
            loadTableData(); // Refresh table view on success
        }
        return success;
    }
}
