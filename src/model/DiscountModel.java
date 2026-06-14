package model;

/**
 * Model class representing a discount / offer deal.
 */
public class DiscountModel {
    private String dealCode;
    private String dealName;
    private int reservationsLeft;
    private String endDate;
    private String status;

    public DiscountModel(String dealCode, String dealName, int reservationsLeft, String endDate, String status) {
        this.dealCode = dealCode;
        this.dealName = dealName;
        this.reservationsLeft = reservationsLeft;
        this.endDate = endDate;
        this.status = status;
    }

    public String getDealCode() {
        return dealCode;
    }

    public void setDealCode(String dealCode) {
        this.dealCode = dealCode;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public int getReservationsLeft() {
        return reservationsLeft;
    }

    public void setReservationsLeft(int reservationsLeft) {
        this.reservationsLeft = reservationsLeft;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
