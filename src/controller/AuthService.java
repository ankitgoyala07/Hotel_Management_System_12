package controller;

import model.loginModel;
import model.signupModel;

/**
 * Interface representing the high-level contract for authentication service operations.
 * Decouples controllers from concrete database Data Access Objects (DAOs).
 */
public interface AuthService {
    /**
     * Authenticates login credentials and resolves the user's role on success.
     *
     * @param credentials the login credentials model
     * @return true if credentials are valid, false otherwise
     */
    boolean login(loginModel credentials);

    /**
     * Registers a new user with their registration details.
     *
     * @param userDetails the registration details model
     * @return true if registration is successful, false otherwise
     */
    boolean register(signupModel userDetails);

    /**
     * Checks if a username already exists.
     *
     * @param username the username to verify
     * @return true if exists, false otherwise
     */
    boolean usernameExists(String username);

    /**
     * Checks if an email address is already registered.
     *
     * @param email the email address to verify
     * @return true if registered, false otherwise
     */
    boolean emailExists(String email);
}
