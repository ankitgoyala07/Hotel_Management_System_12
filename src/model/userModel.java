package model;

/**
 *
 * @author i3
 */
public class userModel {
    private int user_id;
    private String username;
    private String password;
    private String email;
    
    // Default constructor
    public userModel() {
    }

    // Parameterized constructor
    public userModel(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    public int getID(){
        return user_id;
    }
    
    public void setUserid(int user_id){
        this.user_id = user_id;
    }
    
    public String getName(){
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
}
