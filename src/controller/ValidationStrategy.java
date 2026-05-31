package controller;

/**
 * Interface representing a strategy for validating single input values.
 */
public interface ValidationStrategy {
    /**
     * Validates the given value against a validation rule.
     *
     * @param value the string input value to validate
     * @throws ValidationException if the validation fails
     */
    void validate(String value) throws ValidationException;
}
