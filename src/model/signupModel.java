package model;

/**
 * Model class holding user registration details for the signup process.
 *
 * @author i3
 */
public class signupModel {
    private int user_id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String role;

    // Default constructor
    public signupModel() {}

    // Parameterized constructor
    public signupModel(String username, String email, String phone,
                       String password, String role) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    public int getUserId()                     { return user_id; }
    public void setUserId(int user_id)         { this.user_id = user_id; }

    public String getUsername()                { return username; }
    public void setUsername(String username)   { this.username = username; }

    public String getEmail()                   { return email; }
    public void setEmail(String email)         { this.email = email; }

    public String getPhone()                   { return phone; }
    public void setPhone(String phone)         { this.phone = phone; }

    public String getPassword()                { return password; }
    public void setPassword(String password)   { this.password = password; }

    public String getRole()                    { return role; }
    public void setRole(String role)           { this.role = role; }
}
