package service;

import model.Book;
import model.Member;
import model.Librarian;
import model.Person;
import operations.LibraryOperations;
import exception.BookNotAvailableException;
import exception.InvalidMemberException;
import exception.InvalidBorrowException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Core library service class that implements all library operations.
 * 
 * Demonstrates:
 * - Interface implementation (LibraryOperations)
 * - Collections: ArrayList (books), HashMap (members, borrow records)
 * - Sorting using Collections.sort() and Comparator
 * - Custom Exception Handling
 * - Polymorphism (Person references)
 * - Method Overloading (searchBook by ID vs keyword)
 * 
 * Collection Choices:
 * - ArrayList<Book> for books: maintains insertion order, allows indexed access,
 *   good for iteration and sorting. Books are frequently listed and searched sequentially.
 * - HashMap<Integer, Member> for members: O(1) lookup by member ID,
 *   members are frequently looked up by ID during issue/return operations.
 * - ArrayList<int[]> for borrow records: each entry is {bookId, memberId},
 *   supports multiple copies of a book being borrowed by different members.
 */
public class Library implements LibraryOperations {

    private ArrayList<Book> books;
    private HashMap<Integer, Member> members;
    private ArrayList<int[]> borrowRecords; // each entry: {bookId, memberId}
    private Librarian librarian;

    // Constructor
    public Library() {
        this.books = new ArrayList<>();
        this.members = new HashMap<>();
        this.borrowRecords = new ArrayList<>();
    }

    // Constructor with librarian
    public Library(Librarian librarian) {
        this();
        this.librarian = librarian;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    // ==================== BOOK MANAGEMENT ====================

    /**
     * Add a book. Enforces unique book ID.
     */
    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Check if a book ID already exists in the library.
     */
    public boolean bookIdExists(int bookId) {
        return findBookById(bookId) != null;
    }

    /**
     * Check if a member ID already exists in the library.
     */
    public boolean memberIdExists(int memberId) {
        return members.containsKey(memberId);
    }

    @Override
    public void removeBook(int bookId) throws BookNotAvailableException {
        Book book = findBookById(bookId);
        if (book == null) {
            throw new BookNotAvailableException("Book with ID " + bookId + " not found in the library.");
        }
        if (book.getAvailableCopies() < book.getTotalCopies()) {
            throw new BookNotAvailableException("Book '" + book.getTitle() + "' has borrowed copies and cannot be removed.");
        }
        books.remove(book);
    }

    // ==================== SEARCH OPERATIONS ====================
    // Demonstrates: Method Overloading -- same method name, different parameters

    /**
     * Search by book ID (exact match).
     * Demonstrates: Method Overloading (int parameter version).
     */
    @Override
    public Book searchBook(int bookId) throws BookNotAvailableException {
        Book book = findBookById(bookId);
        if (book == null) {
            throw new BookNotAvailableException("No book found with ID: " + bookId);
        }
        return book;
    }

    /**
     * Search by keyword in title or author (partial, case-insensitive match).
     * Demonstrates: Method Overloading (String parameter version).
     */
    @Override
    public List<Book> searchBook(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(lowerKeyword)
                    || book.getAuthor().toLowerCase().contains(lowerKeyword)
                    || book.getGenre().toLowerCase().contains(lowerKeyword)) {
                results.add(book);
            }
        }
        return results;
    }

    // ==================== ISSUE AND RETURN ====================

    /**
     * Issue a book to a member.
     * Demonstrates: Custom Exception Handling with multiple exception types.
     */
    @Override
    public void issueBook(int bookId, int memberId) throws BookNotAvailableException,
            InvalidMemberException, InvalidBorrowException {

        // Validate book exists
        Book book = findBookById(bookId);
        if (book == null) {
            throw new BookNotAvailableException("Book with ID " + bookId + " does not exist.");
        }

        // Check if copies are available
        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book '" + book.getTitle() + "' -- all copies are borrowed.");
        }

        // Validate member exists
        Member member = members.get(memberId);
        if (member == null) {
            throw new InvalidMemberException("Member with ID " + memberId + " does not exist.");
        }

        // Check if member has reached borrowing limit
        if (member.hasReachedLimit()) {
            throw new InvalidBorrowException("Member '" + member.getName()
                    + "' has reached the maximum borrowing limit of " + member.getMaxBooks() + " books.");
        }

        // Issue the book -- decrease available copies
        book.borrowCopy();
        member.addBorrowedBook(book);
        borrowRecords.add(new int[]{bookId, memberId});
    }

    /**
     * Return a book from a member.
     * Demonstrates: Custom Exception Handling.
     */
    @Override
    public void returnBook(int bookId, int memberId) throws BookNotAvailableException,
            InvalidMemberException, InvalidBorrowException {

        // Validate book exists
        Book book = findBookById(bookId);
        if (book == null) {
            throw new BookNotAvailableException("Book with ID " + bookId + " does not exist.");
        }

        // Validate member exists
        Member member = members.get(memberId);
        if (member == null) {
            throw new InvalidMemberException("Member with ID " + memberId + " does not exist.");
        }

        // Validate this member actually borrowed this book
        boolean found = false;
        for (int i = 0; i < borrowRecords.size(); i++) {
            int[] record = borrowRecords.get(i);
            if (record[0] == bookId && record[1] == memberId) {
                borrowRecords.remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new InvalidBorrowException("Book '" + book.getTitle()
                    + "' was not borrowed by member '" + member.getName() + "'.");
        }

        // Return the book -- increase available copies
        book.returnCopy();
        member.removeBorrowedBook(book);
    }

    // ==================== DISPLAY / LISTING ====================

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books); // return a copy
    }

    @Override
    public List<Book> getAvailableBooks() {
        List<Book> available = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable()) {
                available.add(book);
            }
        }
        return available;
    }

    /**
     * Get all books sorted by title.
     * Demonstrates: Sorting with Collections.sort() and Comparator.
     */
    public List<Book> getBooksSortedByTitle() {
        List<Book> sorted = new ArrayList<>(books);
        Collections.sort(sorted, Comparator.comparing(Book::getTitle));
        return sorted;
    }

    /**
     * Get all books sorted by author.
     * Demonstrates: Sorting with Comparator.
     */
    public List<Book> getBooksSortedByAuthor() {
        List<Book> sorted = new ArrayList<>(books);
        Collections.sort(sorted, Comparator.comparing(Book::getAuthor));
        return sorted;
    }

    // ==================== MEMBER MANAGEMENT ====================

    @Override
    public void addMember(Member member) {
        members.put(member.getId(), member);
    }

    @Override
    public Member getMember(int memberId) throws InvalidMemberException {
        Member member = members.get(memberId);
        if (member == null) {
            throw new InvalidMemberException("Member with ID " + memberId + " not found.");
        }
        return member;
    }

    @Override
    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }

    /**
     * Demonstrates Polymorphism -- returns Person references
     * that are actually Member or Librarian objects.
     */
    public List<Person> getAllPeople() {
        List<Person> people = new ArrayList<>();
        if (librarian != null) {
            people.add(librarian);  // Librarian stored as Person reference
        }
        people.addAll(members.values()); // Members stored as Person references
        return people;
    }

    /**
     * Demonstrates Polymorphism -- calling displayInfo() on Person references
     * invokes the correct overridden version at runtime.
     */
    public List<String> displayAllPeopleInfo() {
        List<String> info = new ArrayList<>();
        for (Person person : getAllPeople()) {
            info.add(person.displayInfo()); // Polymorphic call
        }
        return info;
    }

    // ==================== BORROW RECORDS ====================

    public List<int[]> getBorrowRecords() {
        return new ArrayList<>(borrowRecords);
    }

    public String getBorrowRecordsSummary() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < borrowRecords.size(); i++) {
            int[] r = borrowRecords.get(i);
            if (i > 0) sb.append(", ");
            sb.append("bookId=").append(r[0]).append("->memberId=").append(r[1]);
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Get all books borrowed by a specific member.
     */
    public List<Book> getBorrowedBooks(int memberId) throws InvalidMemberException {
        Member member = getMember(memberId);
        return new ArrayList<>(member.getBorrowedBooks());
    }

    // ==================== HELPER METHODS ====================

    private Book findBookById(int bookId) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                return book;
            }
        }
        return null;
    }

    /**
     * Loads sample data for demonstration purposes.
     */
    public void loadSampleData() {
        // Add librarian
        this.librarian = new Librarian(100, "Ms. Sarah Johnson", "sarah@library.com", "EMP001");

        // Add books (some with multiple copies)
        addBook(new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 3));
        addBook(new Book(2, "To Kill a Mockingbird", "Harper Lee", "Fiction", 2));
        addBook(new Book(3, "1984", "George Orwell", "Dystopian", 4));
        addBook(new Book(4, "Pride and Prejudice", "Jane Austen", "Romance", 2));
        addBook(new Book(5, "The Catcher in the Rye", "J.D. Salinger", "Fiction", 1));
        addBook(new Book(6, "Data Structures in Java", "Robert Lafore", "Education", 5));
        addBook(new Book(7, "Clean Code", "Robert C. Martin", "Technology", 3));
        addBook(new Book(8, "The Hobbit", "J.R.R. Tolkien", "Fantasy", 2));

        // Add members
        addMember(new Member(101, "Alice Brown", "9876543210"));
        addMember(new Member(102, "Bob Smith", "8765432109"));
        addMember(new Member(103, "Charlie Davis", "7654321098", 5)); // custom limit
    }

    public int getTotalBooks() {
        return books.size();
    }

    public int getTotalMembers() {
        return members.size();
    }

    public int getTotalBorrowed() {
        return borrowRecords.size();
    }
}
