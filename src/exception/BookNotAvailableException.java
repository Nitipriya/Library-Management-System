package exception;

/**
 * Custom exception thrown when a book is not available for borrowing.
 * Demonstrates: Custom Exception Handling.
 */
public class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String message) {
        super(message);
    }
}
