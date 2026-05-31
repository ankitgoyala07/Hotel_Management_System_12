package controller;

/**
 * Validator class to verify that a phone number contains exactly 10 digits.
 */
public class PhoneValidator implements ValidationStrategy {
    @Override
    public void validate(String value) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("Phone number is required.");
        }
        if (!value.matches("\\d{10}")) {
            throw new ValidationException("Phone number must be exactly 10 digits.");
        }
    }
}
