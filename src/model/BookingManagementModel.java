package model;

import java.sql.Date;

/**
 * Model class holding active booking details for Booking Management screen.
 */
public class BookingManagementModel {
    private String fullName;
    private String phoneNumber;
    private String emailAddress;
    private String homeAddress;
    private String roomType;
    private Date checkInDate;
    private Date checkOutDate;
    private String discountDeal;

    public BookingManagementModel() {}

    public BookingManagementModel(String fullName, String phoneNumber, String emailAddress, String homeAddress,
                                  String roomType, Date checkInDate, Date checkOutDate, String discountDeal) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.homeAddress = homeAddress;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.discountDeal = discountDeal;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getHomeAddress() { return homeAddress; }
    public void setHomeAddress(String homeAddress) { this.homeAddress = homeAddress; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public Date getCheckInDate() { return checkInDate; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }

    public Date getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }

    public String getDiscountDeal() { return discountDeal; }
    public void setDiscountDeal(String discountDeal) { this.discountDeal = discountDeal; }
}
