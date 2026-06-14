/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// rajip
package model;
import java.sql.Date;
/**
 *
 * @author Dell
 */
public class GuestDetails {
    // guest id
    private int Guest_id ;
     
    // Default Constructor
    public GuestDetails() {}

    // Constructor without ID (for insertion)
    public GuestDetails(String FULL_NAME, int PHONE_NUMBER, String EMAIL_ADDRESS, int ROOM_NO, int GUEST_NO, String Room_Type, Date CHECK_IN_DATE, Date CHECK_OUT_DATE) {
        this.FULL_NAME = FULL_NAME;
        this.PHONE_NUMBER = PHONE_NUMBER;
        this.EMAIL_ADDRESS = EMAIL_ADDRESS;
        this.ROOM_NO = ROOM_NO;
        this.GUEST_NO = GUEST_NO;
        this.Room_Type = Room_Type;
        this.CHECK_IN_DATE = CHECK_IN_DATE;
        this.CHECK_OUT_DATE = CHECK_OUT_DATE;
    }

    // Full Constructor
    public GuestDetails(int Guest_id, String FULL_NAME, int PHONE_NUMBER, String EMAIL_ADDRESS, int ROOM_NO, int GUEST_NO, String Room_Type, Date CHECK_IN_DATE, Date CHECK_OUT_DATE) {
        this.Guest_id = Guest_id;
        this.FULL_NAME = FULL_NAME;
        this.PHONE_NUMBER = PHONE_NUMBER;
        this.EMAIL_ADDRESS = EMAIL_ADDRESS;
        this.ROOM_NO = ROOM_NO;
        this.GUEST_NO = GUEST_NO;
        this.Room_Type = Room_Type;
        this.CHECK_IN_DATE = CHECK_IN_DATE;
        this.CHECK_OUT_DATE = CHECK_OUT_DATE;
    }

    public int getId() {
        return Guest_id;
    }
   
    public void setId(int Guest_id) {
        this.Guest_id = Guest_id;
    }
    
    // full name
    private String FULL_NAME;
    public String getFULL_NAME() {
        return FULL_NAME;
    }

    public void setFULL_NAME(String FULL_NAME) {
        this.FULL_NAME = FULL_NAME;
       
    }
    
    // phone number 
    private int PHONE_NUMBER;
    public  int getPHONE_NUMBER() {
        return PHONE_NUMBER;
    }

    public void setPHONE_NUMBER(int PHONE_NUMBER) {
        this.PHONE_NUMBER = PHONE_NUMBER;
       
    }
    
    // email 
    
    private String EMAIL_ADDRESS;
    public String getEMAIL_ADDRESS() {
        return EMAIL_ADDRESS;
    }

    public void setEMAIL_ADDRESS(String EMAIL_ADDRESS) {
        this.EMAIL_ADDRESS = EMAIL_ADDRESS;
        
       
    }
    
    // ROOM NOMBER
    
    private int ROOM_NO;
    public  int getROOM_NO() {
        return ROOM_NO;
    }

    public void setROOM_NO(int ROOM_NO) {
        this.ROOM_NO = ROOM_NO;
    }
    // GUEST NUMBER 
    private int GUEST_NO;
    public  int getGUEST_NO() {
        return GUEST_NO;
    }

    public void setGUEST_NO(int GUEST_NO) {
        this.GUEST_NO = GUEST_NO;
    }
    // Room Type 
    private String Room_Type ;
    public String getRoom_Type() {
        return Room_Type;
    }

    public void setRoom_Type(String Room_Type) {
        this.Room_Type = Room_Type;
       
    }
    
    // CHECK IN DATE 
    private Date CHECK_IN_DATE ;
    public Date getCHECK_IN_DATE() {
        return CHECK_IN_DATE;
    }

    public void setCHECK_IN_DATE(Date CHECK_IN_DATE) {
        this.CHECK_IN_DATE = CHECK_IN_DATE;
        
       
    }
    
    
    // CHECK OUT DATE 
    private Date CHECK_OUT_DATE ;
    public Date getCHECK_OUT_DATE() {
        return CHECK_OUT_DATE;
    }

    public void setCHECK_OUT_DATE(Date CHECK_OUT_DATE) {
        this.CHECK_OUT_DATE = CHECK_OUT_DATE;
        
       
    }
}
    
    
// rajip

