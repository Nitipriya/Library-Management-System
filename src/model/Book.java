package model;

/**
 * Represents a book in the library.
 * Tracks total copies and available copies instead of a simple boolean.
 * Demonstrates: Constructors, Method Overriding (toString),
 *               Encapsulation (private fields with getters/setters).
 */
public class Book {
    private int bookId;
    private String title;
    private String author;
    private String genre;
    private int totalCopies;
    private int availableCopies;

    // Parameterized constructor (default 1 copy)
    public Book(int bookId, String title, String author, String genre) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.totalCopies = 1;
        this.availableCopies = 1;
    }

    // Overloaded constructor with number of copies
    // Demonstrates: Constructor Overloading (Method Overloading concept)
    public Book(int bookId, String title, String author, String genre, int copies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.totalCopies = copies;
        this.availableCopies = copies;
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    /**
     * Returns true if at least one copy is available.
     */
    public boolean isAvailable() {
        return availableCopies > 0;
    }

    /**
     * Decrease available copies by 1 when issued.
     */
    public void borrowCopy() {
        if (availableCopies > 0) {
            availableCopies--;
        }
    }

    /**
     * Increase available copies by 1 when returned.
     */
    public void returnCopy() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }

    /**
     * Overriding Object's toString().
     * Demonstrates: Method Overriding.
     */
    @Override
    public String toString() {
        return "Book[id=" + bookId + ", title=" + title + ", author=" + author
                + ", genre=" + genre + ", copies=" + availableCopies + "/" + totalCopies + "]";
    }

    /**
     * Returns a formatted display string for the book.
     */
    public String displayInfo() {
        String status = availableCopies > 0 ? "Available" : "All Borrowed";
        return "ID: " + bookId + " | " + title + " by " + author
                + " | Genre: " + genre + " | Copies: " + availableCopies + "/" + totalCopies
                + " | Status: " + status;
    }
}
