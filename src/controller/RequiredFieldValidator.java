package controller;

/**
 * Validator class to verify that a required field is populated.
 */
public class RequiredFieldValidator implements ValidationStrategy {
    private final String fieldName;

    public RequiredFieldValidator(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public void validate(String value) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " is required.");
        }
    }
}
