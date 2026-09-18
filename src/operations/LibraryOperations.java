package operations;

import model.Book;
import model.Member;

import java.util.List;

/**
 * Interface defining core library operations.
 * Demonstrates: Interface, Abstraction.
 * Any class implementing this must provide all these operations.
 */
public interface LibraryOperations {

    // Book management
    void addBook(Book book);
    void removeBook(int bookId) throws Exception;

    // Search operations — Method Overloading demonstrated here
    Book searchBook(int bookId) throws Exception;       // search by ID
    List<Book> searchBook(String keyword);               // search by title/author keyword

    // Borrowing and returning
    void issueBook(int bookId, int memberId) throws Exception;
    void returnBook(int bookId, int memberId) throws Exception;

    // Display operations
    List<Book> getAllBooks();
    List<Book> getAvailableBooks();

    // Member management
    void addMember(Member member);
    Member getMember(int memberId) throws Exception;
    List<Member> getAllMembers();
}
