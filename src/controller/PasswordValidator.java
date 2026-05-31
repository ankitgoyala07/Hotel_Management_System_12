package controller;

/**
 * Validator class to verify that a password meets minimum length constraints.
 */
public class PasswordValidator implements ValidationStrategy {
    @Override
    public void validate(String value) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("Password is required.");
        }
        if (value.length() < 4) {
            throw new ValidationException("Password must be at least 4 characters long.");
        }
    }
}
