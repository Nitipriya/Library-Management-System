package main;

import model.Book;
import model.Member;
import model.Librarian;
import model.Person;
import service.Library;
import exception.BookNotAvailableException;
import exception.InvalidMemberException;
import exception.InvalidBorrowException;

import java.util.List;

/**
 * Automated Demo Runner for Team Presentation.
 * 
 * This class runs through ALL features of the Library Management System
 * with pre-loaded data, printing clear output that demonstrates every
 * OOP concept, collection usage, and exception handling scenario.
 * 
 * Run this during the team demo to cover everything automatically.
 */
public class DemoRunner {

    private static Library library;

    public static void main(String[] args) {
        printBanner();

        library = new Library();

        // Run all demos in sequence
        demoConstructorsAndInheritance();
        demoAbstractClassAndPolymorphism();
        demoInterface();
        demoCollectionsAndAddBooks();
        demoAddMembers();
        demoMethodOverloading();
        demoSorting();
        demoIssueBooks();
        demoReturnBooks();
        demoAvailabilityCheck();
        demoExceptionHandling();
        demoMethodOverriding();

        printFooter();
    }

    // ==================== DEMO SECTIONS ====================

    private static void demoConstructorsAndInheritance() {
        printSection("1. CONSTRUCTORS & INHERITANCE");
        System.out.println("Creating a Librarian (extends Person) using parameterized constructor:");
        Librarian librarian = new Librarian(100, "Ms. Sarah Johnson", "sarah@library.com", "EMP001");
        library.setLibrarian(librarian);
        System.out.println("  -> " + librarian.displayInfo());

        System.out.println("\nCreating Members (extends Person) using parameterized constructors:");

        System.out.println("  Constructor 1 -- Member(id, name, contact) -- default max books = 3:");
        Member m1 = new Member(101, "Alice Brown", "9876543210");
        System.out.println("  -> " + m1.displayInfo());

        System.out.println("\n  Constructor 2 -- Member(id, name, contact, maxBooks) -- OVERLOADED constructor:");
        Member m2 = new Member(102, "Bob Smith", "8765432109");
        Member m3 = new Member(103, "Charlie Davis", "7654321098", 5);
        System.out.println("  -> " + m2.displayInfo());
        System.out.println("  -> " + m3.displayInfo() + "  (custom limit of 5)");

        library.addMember(m1);
        library.addMember(m2);
        library.addMember(m3);

        System.out.println("\n  * Inheritance: Both Member and Librarian extend abstract class Person.");
        System.out.println("  * Constructor Chaining: Member/Librarian call super(id, name, contact).");
        System.out.println("  * Constructor Overloading: Member has 2 constructors (3-arg and 4-arg).");
    }

    private static void demoAbstractClassAndPolymorphism() {
        printSection("2. ABSTRACT CLASS & POLYMORPHISM");
        System.out.println("Person is an ABSTRACT CLASS with abstract method displayInfo().");
        System.out.println("We store all people as Person references and call displayInfo():");
        System.out.println();

        List<Person> people = library.getAllPeople();
        for (Person person : people) {
            // Polymorphic call -- correct displayInfo() is invoked at runtime
            System.out.println("  [" + person.getClass().getSimpleName() + "] " + person.displayInfo());
        }

        System.out.println("\n  * Polymorphism: Same method call (displayInfo()) gives different results");
        System.out.println("    depending on the actual object type (Librarian vs Member).");
        System.out.println("  * Cannot instantiate Person directly -- it is abstract.");
    }

    private static void demoInterface() {
        printSection("3. INTERFACE -- LibraryOperations");
        System.out.println("Library class implements the LibraryOperations interface.");
        System.out.println("Interface methods: addBook, removeBook, searchBook (overloaded),");
        System.out.println("  issueBook, returnBook, getAllBooks, getAvailableBooks,");
        System.out.println("  addMember, getMember, getAllMembers.");
        System.out.println("\n  * The interface defines WHAT operations the library supports.");
        System.out.println("  * The Library class provides HOW they are implemented.");
    }

    private static void demoCollectionsAndAddBooks() {
        printSection("4. COLLECTIONS -- Adding Books (ArrayList)");
        System.out.println("Books are stored in an ArrayList<Book>.");
        System.out.println("ArrayList was chosen because:");
        System.out.println("  - Maintains insertion order");
        System.out.println("  - Allows indexed access and iteration");
        System.out.println("  - Easy to sort using Collections.sort()");
        System.out.println("  - Books are frequently listed and searched sequentially");
        System.out.println();
        System.out.println("Each book now tracks totalCopies and availableCopies:");
        System.out.println();

        Object[][] booksData = {
            {1, "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 3},
            {2, "To Kill a Mockingbird", "Harper Lee", "Fiction", 2},
            {3, "1984", "George Orwell", "Dystopian", 4},
            {4, "Pride and Prejudice", "Jane Austen", "Romance", 2},
            {5, "The Catcher in the Rye", "J.D. Salinger", "Fiction", 1},
            {6, "Data Structures in Java", "Robert Lafore", "Education", 5},
            {7, "Clean Code", "Robert C. Martin", "Technology", 3},
            {8, "The Hobbit", "J.R.R. Tolkien", "Fantasy", 2}
        };

        for (Object[] d : booksData) {
            Book book = new Book((int) d[0], (String) d[1], (String) d[2], (String) d[3], (int) d[4]);
            library.addBook(book);
            System.out.println("  Added: " + book.displayInfo());
        }
        System.out.println("\n  Total books in library: " + library.getTotalBooks());
    }

    private static void demoAddMembers() {
        printSection("5. COLLECTIONS -- Members (HashMap)");
        System.out.println("Members are stored in a HashMap<Integer, Member>.");
        System.out.println("HashMap was chosen because:");
        System.out.println("  - O(1) lookup by member ID");
        System.out.println("  - Members are frequently looked up by ID during issue/return");
        System.out.println("  - Key-value pairs are ideal: memberId -> Member object");
        System.out.println();

        System.out.println("  Current members (already added):");
        for (Member m : library.getAllMembers()) {
            System.out.println("  -> " + m.displayInfo());
        }
        System.out.println("\n  Borrow records use ArrayList<int[]> where each entry is {bookId, memberId}.");
        System.out.println("  This supports multiple copies of the same book being borrowed by different members.");
    }

    private static void demoMethodOverloading() {
        printSection("6. METHOD OVERLOADING -- searchBook()");
        System.out.println("searchBook is OVERLOADED -- two versions with different parameters:\n");

        System.out.println("  Version 1: searchBook(int bookId) -- Search by ID:");
        try {
            Book result = library.searchBook(3);
            System.out.println("  -> searchBook(3) found: " + result.displayInfo());
        } catch (Exception e) {
            System.out.println("  -> Error: " + e.getMessage());
        }

        System.out.println("\n  Version 2: searchBook(String keyword) -- Search by title/author keyword:");
        List<Book> results = library.searchBook("Java");
        System.out.println("  -> searchBook(\"Java\") found " + results.size() + " result(s):");
        for (Book b : results) {
            System.out.println("     " + b.displayInfo());
        }

        results = library.searchBook("Orwell");
        System.out.println("  -> searchBook(\"Orwell\") found " + results.size() + " result(s):");
        for (Book b : results) {
            System.out.println("     " + b.displayInfo());
        }
    }

    private static void demoSorting() {
        printSection("7. SORTING with Collections.sort() & Comparator");
        System.out.println("Sorting books by TITLE (alphabetical):");
        List<Book> sorted = library.getBooksSortedByTitle();
        for (Book b : sorted) {
            System.out.println("  " + b.getTitle() + " -- " + b.getAuthor());
        }

        System.out.println("\nSorting books by AUTHOR (alphabetical):");
        sorted = library.getBooksSortedByAuthor();
        for (Book b : sorted) {
            System.out.println("  " + b.getAuthor() + " -- " + b.getTitle());
        }
    }

    private static void demoIssueBooks() {
        printSection("8. ISSUING BOOKS (with copy tracking)");
        System.out.println("Issuing books to members -- available copies decrease on issue...\n");

        issueAndPrint(1, 101, "Alice borrows 'The Great Gatsby'");
        printCopyStatus(1);
        issueAndPrint(1, 102, "Bob also borrows 'The Great Gatsby' (another copy)");
        printCopyStatus(1);
        issueAndPrint(3, 101, "Alice borrows '1984'");
        issueAndPrint(5, 102, "Bob borrows 'The Catcher in the Rye'");
        printCopyStatus(5);
        issueAndPrint(7, 103, "Charlie borrows 'Clean Code'");

        System.out.println("\n  Current borrow records: " + library.getBorrowRecordsSummary());
    }

    private static void demoReturnBooks() {
        printSection("9. RETURNING BOOKS (copies increase on return)");
        System.out.println("Returning a book -- available copies increase...\n");

        try {
            System.out.println("  'The Great Gatsby' before return:");
            printCopyStatus(1);

            System.out.println("  Returning Book ID 1 from Member ID 101 (Alice returns 'The Great Gatsby'):");
            library.returnBook(1, 101);
            System.out.println("  -> SUCCESS! One copy returned.");
            printCopyStatus(1);
        } catch (Exception e) {
            System.out.println("  -> Error: " + e.getMessage());
        }
    }

    private static void demoAvailabilityCheck() {
        printSection("10. CHECKING AVAILABILITY");
        System.out.println("All books with their copy counts:\n");

        for (Book b : library.getAllBooks()) {
            System.out.println("  " + b.displayInfo());
        }

        List<Book> available = library.getAvailableBooks();
        System.out.println("\n  Books with copies available: " + available.size() + " / " + library.getTotalBooks() + " total titles");
        System.out.println("  Total borrow records: " + library.getTotalBorrowed());
    }

    private static void demoExceptionHandling() {
        printSection("11. CUSTOM EXCEPTION HANDLING");

        System.out.println("Testing BookNotAvailableException (all copies borrowed):");
        System.out.println("  'The Catcher in the Rye' has only 1 copy, already borrowed by Bob.");
        System.out.println("  Attempting to issue it to Charlie...");
        try {
            library.issueBook(5, 103);
        } catch (BookNotAvailableException e) {
            System.out.println("  -> CAUGHT BookNotAvailableException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  -> Error: " + e.getMessage());
        }

        System.out.println("\nTesting InvalidMemberException:");
        System.out.println("  Attempting to issue to Member ID 999 (does not exist)...");
        try {
            library.issueBook(2, 999);
        } catch (InvalidMemberException e) {
            System.out.println("  -> CAUGHT InvalidMemberException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  -> Error: " + e.getMessage());
        }

        System.out.println("\nTesting InvalidBorrowException (wrong member returning):");
        System.out.println("  Attempting to return Book ID 3 from Member ID 102 (Bob didn't borrow it)...");
        try {
            library.returnBook(3, 102);
        } catch (InvalidBorrowException e) {
            System.out.println("  -> CAUGHT InvalidBorrowException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  -> Error: " + e.getMessage());
        }

        System.out.println("\nTesting BookNotAvailableException (search non-existent):");
        System.out.println("  Searching for Book ID 999 (does not exist)...");
        try {
            library.searchBook(999);
        } catch (BookNotAvailableException e) {
            System.out.println("  -> CAUGHT BookNotAvailableException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  -> Error: " + e.getMessage());
        }

        System.out.println("\nTesting InvalidBorrowException (borrow limit):");
        System.out.println("  Alice has 1 book. Issuing 2 more to reach her limit of 3...");
        try {
            library.issueBook(2, 101);  // 2nd book for Alice
            System.out.println("  -> Issued Book ID 2 to Alice (2/3)");
            library.issueBook(4, 101);  // 3rd book -- at limit now
            System.out.println("  -> Issued Book ID 4 to Alice (3/3 -- at limit!)");
            System.out.println("  Now attempting to issue a 4th book...");
            library.issueBook(6, 101);  // Should fail
        } catch (InvalidBorrowException e) {
            System.out.println("  -> CAUGHT InvalidBorrowException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  -> Error: " + e.getMessage());
        }
    }

    private static void demoMethodOverriding() {
        printSection("12. METHOD OVERRIDING -- toString() and displayInfo()");
        System.out.println("Both toString() and displayInfo() are OVERRIDDEN in subclasses:\n");

        System.out.println("  Librarian.toString(): " + library.getLibrarian().toString());
        System.out.println("  Librarian.displayInfo(): " + library.getLibrarian().displayInfo());

        System.out.println();
        for (Member m : library.getAllMembers()) {
            System.out.println("  Member.toString(): " + m.toString());
            System.out.println("  Member.displayInfo(): " + m.displayInfo());
            System.out.println();
        }

        System.out.println("  Book.toString() example: " + library.getAllBooks().get(0).toString());
        System.out.println("  Book.displayInfo() example: " + library.getAllBooks().get(0).displayInfo());

        System.out.println("\n  * toString() overrides Object.toString()");
        System.out.println("  * displayInfo() overrides the abstract method in Person");
    }

    // ==================== HELPER METHODS ====================

    private static void issueAndPrint(int bookId, int memberId, String description) {
        try {
            library.issueBook(bookId, memberId);
            System.out.println("  -> " + description + " -- SUCCESS");
        } catch (Exception e) {
            System.out.println("  -> " + description + " -- FAILED: " + e.getMessage());
        }
    }

    private static void printCopyStatus(int bookId) {
        try {
            Book b = library.searchBook(bookId);
            System.out.println("     Copies: " + b.getAvailableCopies() + "/" + b.getTotalCopies() + " available");
        } catch (Exception e) {
            System.out.println("     Could not get status: " + e.getMessage());
        }
    }

    private static void printBanner() {
        System.out.println("+==================================================================+");
        System.out.println("|           LIBRARY MANAGEMENT SYSTEM -- TEAM DEMO                 |");
        System.out.println("|         Automated Demonstration of All Features                  |");
        System.out.println("+==================================================================+");
        System.out.println("|  Concepts covered:                                               |");
        System.out.println("|  - Inheritance, Constructors, Method Overloading/Overriding      |");
        System.out.println("|  - Abstract Classes, Interfaces, Polymorphism                    |");
        System.out.println("|  - Collections (ArrayList, HashMap), Sorting (Comparator)        |");
        System.out.println("|  - Custom Exception Handling                                     |");
        System.out.println("|  - Copy Tracking (available/total copies per book)               |");
        System.out.println("+==================================================================+");
    }

    private static void printSection(String title) {
        System.out.println();
        System.out.println("--------------------------------------------------------------------");
        System.out.println("  " + title);
        System.out.println("--------------------------------------------------------------------");
        System.out.println();
    }

    private static void printFooter() {
        System.out.println();
        System.out.println("+==================================================================+");
        System.out.println("|                    DEMO COMPLETE!                                |");
        System.out.println("|  All features demonstrated successfully.                         |");
        System.out.println("+==================================================================+");
    }
}
