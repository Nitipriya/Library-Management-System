# Class / Design Diagram

## Overview

The system follows a layered architecture:
- **Model Layer** — Data classes (Person, Member, Librarian, Book)
- **Operations Layer** — Interface defining library operations
- **Service Layer** — Library class implementing all business logic
- **Exception Layer** — Custom exceptions for error handling
- **UI Layer** — Swing GUI for user interaction
- **Main Layer** — Entry points (interactive + demo)

---

## Class Hierarchy Diagram

```
                    +---------------------+
                    |    <<abstract>>      |
                    |       Person         |
                    +---------------------+
                    | - id: int            |
                    | - name: String       |
                    | - contact: String    |
                    +---------------------+
                    | + Person(id, name,   |
                    |     contact)         |
                    | + getId(): int       |
                    | + getName(): String  |
                    | + getContact(): String|
                    | + abstract           |
                    |   displayInfo():     |
                    |   String             |
                    | + toString(): String |
                    +---------------------+
                         /           \
                        /             \
           +-----------+               +--------------+
           |  Member   |               |  Librarian   |
           +-----------+               +--------------+
           | - borrowedBooks:          | - employeeId:|
           |   ArrayList<Book>         |   String     |
           | - maxBooks: int           +--------------+
           +-----------+               | + Librarian( |
           | + Member(id, name,        |   id, name,  |
           |     contact)              |   contact,   |
           | + Member(id, name,        |   empId)     |
           |     contact, maxBooks)    | + displayInfo|
           |   [OVERLOADED]            |   (): String |
           | + displayInfo(): String   | + toString() |
           | + getBorrowedBooks()      |   : String   |
           | + hasReachedLimit()       +--------------+
           | + toString(): String      
           +-----------+
```

---

## Book Class

```
           +---------------------------+
           |          Book             |
           +---------------------------+
           | - bookId: int             |
           | - title: String           |
           | - author: String          |
           | - genre: String           |
           | - totalCopies: int        |
           | - availableCopies: int    |
           +---------------------------+
           | + Book(id, title, author, |
           |     genre)               |
           | + Book(id, title, author, |
           |     genre, copies)        |
           |   [OVERLOADED]            |
           | + isAvailable(): boolean  |
           | + borrowCopy(): void      |
           | + returnCopy(): void      |
           | + displayInfo(): String   |
           | + toString(): String      |
           +---------------------------+
```

---

## Interface

```
       +------------------------------------+
       |   <<interface>>                    |
       |   LibraryOperations               |
       +------------------------------------+
       | + addBook(Book): void              |
       | + removeBook(int): void            |
       | + searchBook(int): Book            |  <-- Method
       | + searchBook(String): List<Book>   |  <-- Overloading
       | + issueBook(int, int): void        |
       | + returnBook(int, int): void       |
       | + getAllBooks(): List<Book>         |
       | + getAvailableBooks(): List<Book>  |
       | + addMember(Member): void          |
       | + getMember(int): Member           |
       | + getAllMembers(): List<Member>     |
       +------------------------------------+
                      |
                      | implements
                      |
       +------------------------------------+
       |           Library                  |
       +------------------------------------+
       | - books: ArrayList<Book>           |
       | - members: HashMap<Integer, Member>|
       | - borrowRecords: ArrayList<int[]>  |
       | - librarian: Librarian             |
       +------------------------------------+
       | + addBook(Book): void              |
       | + searchBook(int): Book            |
       | + searchBook(String): List<Book>   |
       | + issueBook(int, int): void        |
       | + returnBook(int, int): void       |
       | + bookIdExists(int): boolean       |
       | + memberIdExists(int): boolean     |
       | + getBooksSortedByTitle(): List    |
       | + getBooksSortedByAuthor(): List   |
       | + getAllPeople(): List<Person>      |
       | + loadSampleData(): void           |
       +------------------------------------+
```

---

## Custom Exceptions

```
                  +-------------------+
                  |    Exception      |   (Java built-in)
                  +-------------------+
                   /       |         \
                  /        |          \
   +----------------+ +----------+ +----------------+
   | BookNotAvailable| | Invalid  | | InvalidBorrow  |
   | Exception      | | Member   | | Exception      |
   +----------------+ | Exception| +----------------+
   | Thrown when:    | +----------+ | Thrown when:    |
   | - Book not     | | Thrown    | | - Wrong member |
   |   found        | | when:    | |   returns book |
   | - All copies   | | - Member | | - Borrow limit |
   |   borrowed     | |   ID not | |   reached      |
   +----------------+ |   found  | +----------------+
                       +----------+
```

---

## Relationships Summary

| Relationship     | From              | To                   | Type                |
|------------------|-------------------|----------------------|---------------------|
| Inheritance      | Member            | Person               | extends (IS-A)      |
| Inheritance      | Librarian         | Person               | extends (IS-A)      |
| Implementation   | Library           | LibraryOperations    | implements          |
| Composition      | Member            | ArrayList\<Book\>    | HAS-A (borrowedBooks)|
| Composition      | Library           | ArrayList\<Book\>    | HAS-A (all books)   |
| Composition      | Library           | HashMap\<Int,Member\>| HAS-A (all members) |
| Association      | Library           | Librarian            | HAS-A               |
| Throws           | Library methods   | Custom Exceptions    | throws              |

---

## OOP Concepts Map

| Concept              | Where in Code                                          |
|----------------------|--------------------------------------------------------|
| **Abstract Class**   | `Person` — has abstract `displayInfo()`                |
| **Inheritance**      | `Member extends Person`, `Librarian extends Person`    |
| **Interface**        | `LibraryOperations` implemented by `Library`           |
| **Polymorphism**     | `Person` references calling `displayInfo()` at runtime |
| **Constructors**     | Parameterized constructors in all classes               |
| **Constructor Overloading** | `Member` (3-arg & 4-arg), `Book` (4-arg & 5-arg)|
| **Method Overloading**| `searchBook(int)` vs `searchBook(String)` in Library  |
| **Method Overriding**| `displayInfo()` and `toString()` in Member & Librarian |
| **Encapsulation**    | Private fields + public getters/setters everywhere     |
