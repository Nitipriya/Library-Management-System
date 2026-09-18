package model;

/**
 * Represents a librarian who manages the library.
 * Demonstrates: Inheritance (extends Person), Constructor chaining (super),
 *               Method Overriding (displayInfo, toString).
 */
public class Librarian extends Person {
    private String employeeId;

    // Parameterized constructor
    public Librarian(int id, String name, String contact, String employeeId) {
        super(id, name, contact); // Constructor chaining
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Overriding abstract method from Person.
     * Demonstrates: Method Overriding and Polymorphism.
     */
    @Override
    public String displayInfo() {
        return "Librarian | ID: " + getId() + " | Name: " + getName()
                + " | Contact: " + getContact()
                + " | Employee ID: " + employeeId;
    }

    @Override
    public String toString() {
        return "Librarian[id=" + getId() + ", name=" + getName() + ", empId=" + employeeId + "]";
    }
}
