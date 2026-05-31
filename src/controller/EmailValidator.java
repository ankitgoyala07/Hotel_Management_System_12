package controller;

/**
 * Validator class to verify that an email matches standard formats.
 */
public class EmailValidator implements ValidationStrategy {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    @Override
    public void validate(String value) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("Email address is required.");
        }
        if (!value.matches(EMAIL_REGEX)) {
            throw new ValidationException("Please enter a valid email address.");
        }
    }
}
