package model;

/**
 * Model class holding user login credentials and session details.
 *
 * @author i3
 */
public class loginModel {
    private String username;
    private String password;
    private String role;

    // Default constructor
    public loginModel() {}

    // Parameterized constructor
    public loginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername()                { return username; }
    public void setUsername(String username)   { this.username = username; }

    public String getPassword()                { return password; }
    public void setPassword(String password)   { this.password = password; }

    public String getRole()                    { return role; }
    public void setRole(String role)           { this.role = role; }
}
