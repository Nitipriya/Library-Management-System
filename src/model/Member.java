package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a library member who can borrow and return books.
 * Demonstrates: Inheritance (extends Person), Constructor chaining (super),
 *               Method Overriding (displayInfo, toString), Collections (ArrayList).
 */
public class Member extends Person {
    private List<Book> borrowedBooks;
    private int maxBooks; // maximum books a member can borrow

    // Parameterized constructor
    public Member(int id, String name, String contact) {
        super(id, name, contact); // Constructor chaining
        this.borrowedBooks = new ArrayList<>();
        this.maxBooks = 3; // default limit
    }

    // Overloaded constructor with custom max books limit
    // Demonstrates: Method Overloading (constructor overloading)
    public Member(int id, String name, String contact, int maxBooks) {
        super(id, name, contact);
        this.borrowedBooks = new ArrayList<>();
        this.maxBooks = maxBooks;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public int getMaxBooks() {
        return maxBooks;
    }

    public void setMaxBooks(int maxBooks) {
        this.maxBooks = maxBooks;
    }

    public void addBorrowedBook(Book book) {
        borrowedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book);
    }

    public boolean hasReachedLimit() {
        return borrowedBooks.size() >= maxBooks;
    }

    /**
     * Overriding abstract method from Person.
     * Demonstrates: Method Overriding and Polymorphism.
     */
    @Override
    public String displayInfo() {
        return "Member | ID: " + getId() + " | Name: " + getName()
                + " | Contact: " + getContact()
                + " | Books Borrowed: " + borrowedBooks.size() + "/" + maxBooks;
    }

    @Override
    public String toString() {
        return "Member[id=" + getId() + ", name=" + getName() + ", borrowed=" + borrowedBooks.size() + "]";
    }
}
