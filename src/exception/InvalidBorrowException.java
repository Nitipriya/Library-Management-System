package exception;

/**
 * Custom exception thrown for incorrect borrowing/returning operations.
 * For example: returning a book that was not borrowed by that member,
 * or borrowing when the member has reached their limit.
 * Demonstrates: Custom Exception Handling.
 */
public class InvalidBorrowException extends Exception {
    public InvalidBorrowException(String message) {
        super(message);
    }
}
