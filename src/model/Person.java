package model;

/**
 * Abstract base class representing a person in the library system.
 * Demonstrates: Abstract class, Constructors, Method Overriding (toString),
 *               Abstract method (displayInfo) for polymorphism.
 */
public abstract class Person {
    private int id;
    private String name;
    private String contact;

    // Parameterized constructor
    public Person(int id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Abstract method — each subclass must provide its own implementation.
     * Demonstrates: Abstraction and Polymorphism.
     */
    public abstract String displayInfo();

    /**
     * Overriding Object's toString() method.
     * Demonstrates: Method Overriding.
     */
    @Override
    public String toString() {
        return "Person[id=" + id + ", name=" + name + ", contact=" + contact + "]";
    }
}
