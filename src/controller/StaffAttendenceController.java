package controller;

import dao.StaffAttendenceDao;
import model.StaffAttendenceModel;
import view.StaffAttendence;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for staff attendance page.
 */
public class StaffAttendenceController {
    private final StaffAttendenceDao dao = new StaffAttendenceDao();

    public void loadAttendanceData(StaffAttendence view, Date selectedDate) {
        if (view == null || selectedDate == null) {
            return;
        }
        List<StaffAttendenceModel> attendanceList = dao.getAttendanceByDate(selectedDate);
        boolean isToday = isToday(selectedDate);
        view.populateTable(attendanceList, isToday);
        view.setAttendanceEditable(isToday);
    }

    public boolean handleSaveAttendance(Component parent, StaffAttendence view, Date selectedDate) {
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(parent,
                    "Please select a date.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isToday(selectedDate)) {
            JOptionPane.showMessageDialog(parent,
                    "Attendance can only be marked for today's date.",
                    "Not Allowed", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        List<StaffAttendenceModel> records = view.getAttendanceFromTable();
        if (records.isEmpty()) {
            JOptionPane.showMessageDialog(parent,
                    "No staff records found to save.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        boolean allSaved = true;
        for (StaffAttendenceModel record : records) {
            boolean saved = dao.saveAttendance(
                    record.getStaffId(),
                    selectedDate,
                    record.isPresentToday()
            );
            if (!saved) {
                allSaved = false;
            }
        }

        if (allSaved) {
            JOptionPane.showMessageDialog(parent,
                    "Attendance saved successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            loadAttendanceData((StaffAttendence) view, selectedDate);
            return true;
        }

        JOptionPane.showMessageDialog(parent,
                "Failed to save some attendance records. Please try again.",
                "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public List<StaffAttendenceModel> getAttendanceForDate(Date selectedDate) {
        if (selectedDate == null) {
            return new ArrayList<>();
        }
        return dao.getAttendanceByDate(selectedDate);
    }

    private boolean isToday(Date date) {
        LocalDate selected = date.toLocalDate();
        return selected.equals(LocalDate.now());
    }
}
