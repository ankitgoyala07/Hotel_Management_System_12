package dao;

import database.MySqlConnection;
import model.StaffAttendenceModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for staff attendance operations.
 */
public class StaffAttendenceDao {
    private final MySqlConnection mysql = new MySqlConnection();

    /**
     * Loads all staff with attendance status for the given date and overall percentage.
     */
    public List<StaffAttendenceModel> getAttendanceByDate(Date attendanceDate) {
        List<StaffAttendenceModel> list = new ArrayList<>();
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            System.out.println("Connection failed.");
            return list;
        }

        String sql = "SELECT s.staff_id, s.name, "
                + "COALESCE(day_att.is_present, 0) AS present_today, "
                + "COALESCE(ROUND(SUM(CASE WHEN all_att.is_present = 1 THEN 1 ELSE 0 END) * 100.0 "
                + "/ NULLIF(COUNT(all_att.id), 0), 1), 0) AS total_percentage "
                + "FROM staff s "
                + "LEFT JOIN staff_attendance day_att "
                + "ON s.staff_id = day_att.staff_id AND day_att.attendance_date = ? "
                + "LEFT JOIN staff_attendance all_att ON s.staff_id = all_att.staff_id "
                + "GROUP BY s.staff_id, s.name, day_att.is_present "
                + "ORDER BY s.staff_id";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setDate(1, attendanceDate);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    StaffAttendenceModel model = new StaffAttendenceModel();
                    model.setStaffId(rs.getString("staff_id"));
                    model.setName(rs.getString("name"));
                    model.setTotalPercentage(rs.getDouble("total_percentage"));
                    model.setPresentToday(rs.getBoolean("present_today"));
                    model.setAttendanceDate(attendanceDate);
                    list.add(model);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading attendance: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return list;
    }

    /**
     * Saves or updates attendance for one staff member on a specific date.
     */
    public boolean saveAttendance(String staffId, Date attendanceDate, boolean isPresent) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            System.out.println("Connection failed.");
            return false;
        }

        String sql = "INSERT INTO staff_attendance (staff_id, attendance_date, is_present) "
                + "VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE is_present = VALUES(is_present)";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, staffId);
            pstm.setDate(2, attendanceDate);
            pstm.setBoolean(3, isPresent);
            return pstm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error saving attendance: " + e.getMessage());
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }
}
