/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Dell
 */
public class GuestDetails {
    // guest id
    private int Guest_id ;
     
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
       
    
    // CHECK IN DATE 
    private String CHECK_IN_DATE ;
    public String getCHECK_IN_DATE() {
        return CHECK_IN_DATE;
    }

    public void setCHECK_IN_DATE(String CHECK_IN_DATE) {
        this.CHECK_IN_DATE = CHECK_IN_DATE;
        
       
    }
    
    
    // CHECK OUT DATE 
    private String CHECK_OUT_DATE ;
    public String getCHECK_OUT_DATE() {
        return CHECK_OUT_DATE;
    }

    public void setCHECK_OUT_DATE(String CHECK_OUT_DATE) {
        this.CHECK_OUT_DATE = CHECK_OUT_DATE;
        
       
    }
}
    
    


