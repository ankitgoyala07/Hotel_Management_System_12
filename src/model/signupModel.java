package model;

/**
 * Model class holding user registration details for the signup process.
 * Inherits credentials fields from loginModel.
 *
 * @author i3
 */
public class signupModel extends loginModel {
    private int user_id;
    private String email;
    private String phone;
    private String securityQuestion;

    // Default constructor
    public signupModel() {
        super();
    }

    // Parameterized constructor (backward compatibility)
    public signupModel(String username, String email, String phone,
                       String password, String role) {
        this(username, email, phone, password, role, null);
    }

    // Parameterized constructor with security question
    public signupModel(String username, String email, String phone,
                       String password, String role, String securityQuestion) {
        super(username, password);
        this.setRole(role);
        this.email = email;
        this.phone = phone;
        this.securityQuestion = securityQuestion;
    }

    public int getUserId()                     { return user_id; }
    public void setUserId(int user_id)         { this.user_id = user_id; }

    public String getEmail()                   { return email; }
    public void setEmail(String email)         { this.email = email; }

    public String getPhone()                   { return phone; }
    public void setPhone(String phone)         { this.phone = phone; }

    public String getSecurityQuestion()        { return securityQuestion; }
    public void setSecurityQuestion(String sq) { this.securityQuestion = sq; }
}
