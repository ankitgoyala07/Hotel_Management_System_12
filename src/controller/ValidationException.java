package controller;

/**
 * Exception thrown when validation strategy checks fail.
 */
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
