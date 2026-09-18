package exception;

/**
 * Custom exception thrown when a member ID is invalid or not found.
 * Demonstrates: Custom Exception Handling.
 */
public class InvalidMemberException extends Exception {
    public InvalidMemberException(String message) {
        super(message);
    }
}
