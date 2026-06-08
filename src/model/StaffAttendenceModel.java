package model;

import java.sql.Date;

/**
 * Model class holding staff attendance details for a given date.
 */
public class StaffAttendenceModel {
    private String staffId;
    private String name;
    private double totalPercentage;
    private boolean presentToday;
    private Date attendanceDate;

    public StaffAttendenceModel() {}

    public StaffAttendenceModel(String staffId, String name, double totalPercentage,
                                boolean presentToday, Date attendanceDate) {
        this.staffId = staffId;
        this.name = name;
        this.totalPercentage = totalPercentage;
        this.presentToday = presentToday;
        this.attendanceDate = attendanceDate;
    }

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

    public double getTotalPercentage() {
        return totalPercentage;
    }

    public void setTotalPercentage(double totalPercentage) {
        this.totalPercentage = totalPercentage;
    }

    public boolean isPresentToday() {
        return presentToday;
    }

    public void setPresentToday(boolean presentToday) {
        this.presentToday = presentToday;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }
}
