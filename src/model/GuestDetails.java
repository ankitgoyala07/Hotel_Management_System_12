package model;

import java.sql.Date;

/**
 * Model class holding guest details and booking configurations.
 */
public class GuestDetails {
    private int Guest_id;
    private String FULL_NAME;
    private String PHONE_NUMBER;
    private String EMAIL_ADDRESS;
    private String homeAddress;
    private int ROOM_NO;
    private int GUEST_NO;
    private String Room_Type;
    private Date CHECK_IN_DATE;
    private Date CHECK_OUT_DATE;
    private String discountDeal;

    // Default Constructor
    public GuestDetails() {}

    // Constructor without ID (for insertion)
    public GuestDetails(String FULL_NAME, String PHONE_NUMBER, String EMAIL_ADDRESS, String homeAddress, 
                        int ROOM_NO, int GUEST_NO, String Room_Type, Date CHECK_IN_DATE, Date CHECK_OUT_DATE, 
                        String discountDeal) {
        this.FULL_NAME = FULL_NAME;
        this.PHONE_NUMBER = PHONE_NUMBER;
        this.EMAIL_ADDRESS = EMAIL_ADDRESS;
        this.homeAddress = homeAddress;
        this.ROOM_NO = ROOM_NO;
        this.GUEST_NO = GUEST_NO;
        this.Room_Type = Room_Type;
        this.CHECK_IN_DATE = CHECK_IN_DATE;
        this.CHECK_OUT_DATE = CHECK_OUT_DATE;
        this.discountDeal = discountDeal;
    }

    // Full Constructor
    public GuestDetails(int Guest_id, String FULL_NAME, String PHONE_NUMBER, String EMAIL_ADDRESS, String homeAddress, 
                        int ROOM_NO, int GUEST_NO, String Room_Type, Date CHECK_IN_DATE, Date CHECK_OUT_DATE, 
                        String discountDeal) {
        this.Guest_id = Guest_id;
        this.FULL_NAME = FULL_NAME;
        this.PHONE_NUMBER = PHONE_NUMBER;
        this.EMAIL_ADDRESS = EMAIL_ADDRESS;
        this.homeAddress = homeAddress;
        this.ROOM_NO = ROOM_NO;
        this.GUEST_NO = GUEST_NO;
        this.Room_Type = Room_Type;
        this.CHECK_IN_DATE = CHECK_IN_DATE;
        this.CHECK_OUT_DATE = CHECK_OUT_DATE;
        this.discountDeal = discountDeal;
    }

    public int getId() {
        return Guest_id;
    }

    public void setId(int Guest_id) {
        this.Guest_id = Guest_id;
    }

    public String getFULL_NAME() {
        return FULL_NAME;
    }

    public void setFULL_NAME(String FULL_NAME) {
        this.FULL_NAME = FULL_NAME;
    }

    public String getPHONE_NUMBER() {
        return PHONE_NUMBER;
    }

    public void setPHONE_NUMBER(String PHONE_NUMBER) {
        this.PHONE_NUMBER = PHONE_NUMBER;
    }

    public String getEMAIL_ADDRESS() {
        return EMAIL_ADDRESS;
    }

    public void setEMAIL_ADDRESS(String EMAIL_ADDRESS) {
        this.EMAIL_ADDRESS = EMAIL_ADDRESS;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public int getROOM_NO() {
        return ROOM_NO;
    }

    public void setROOM_NO(int ROOM_NO) {
        this.ROOM_NO = ROOM_NO;
    }

    public int getGUEST_NO() {
        return GUEST_NO;
    }

    public void setGUEST_NO(int GUEST_NO) {
        this.GUEST_NO = GUEST_NO;
    }

    public String getRoom_Type() {
        return Room_Type;
    }

    public void setRoom_Type(String Room_Type) {
        this.Room_Type = Room_Type;
    }

    public Date getCHECK_IN_DATE() {
        return CHECK_IN_DATE;
    }

    public void setCHECK_IN_DATE(Date CHECK_IN_DATE) {
        this.CHECK_IN_DATE = CHECK_IN_DATE;
    }

    public Date getCHECK_OUT_DATE() {
        return CHECK_OUT_DATE;
    }

    public void setCHECK_OUT_DATE(Date CHECK_OUT_DATE) {
        this.CHECK_OUT_DATE = CHECK_OUT_DATE;
    }

    public String getDiscountDeal() {
        return discountDeal;
    }

    public void setDiscountDeal(String discountDeal) {
        this.discountDeal = discountDeal;
    }
}
