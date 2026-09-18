# Library Management System — Project Overview

## About

A Java-based Library Management System that demonstrates core Object-Oriented Programming concepts, Java Collections, abstraction, interfaces, and exception handling through a working Swing GUI application.

---

## Project Structure

```
Library Management System/
|
|-- src/
|   |-- model/
|   |   |-- Person.java              Abstract base class (id, name, contact)
|   |   |-- Member.java              Extends Person, tracks borrowed books
|   |   |-- Librarian.java           Extends Person, has employeeId
|   |   |-- Book.java                Book model with copy tracking
|   |
|   |-- operations/
|   |   |-- LibraryOperations.java   Interface for all library operations
|   |
|   |-- service/
|   |   |-- Library.java             Implements LibraryOperations, core logic
|   |
|   |-- exception/
|   |   |-- BookNotAvailableException.java
|   |   |-- InvalidMemberException.java
|   |   |-- InvalidBorrowException.java
|   |
|   |-- ui/
|   |   |-- LibraryGUI.java          Swing GUI with sidebar navigation
|   |
|   |-- main/
|       |-- LibraryManagementSystem.java   Interactive GUI entry point
|       |-- DemoRunner.java                Automated console demo
|
|-- docs/
|   |-- 00_Project_Overview.md       This file
|   |-- 01_Class_Diagram.md          Class hierarchy and relationships
|   |-- 02_Collection_Selection.md   Why each collection was chosen
|   |-- 03_Exception_Handling.md     Custom exceptions explained
|   |-- 04_Sample_Input_Output.md    Full demo output + GUI test cases
|
|-- out/                             Compiled .class files
```

---

## How to Compile and Run

### Compile all files:
```
javac -d out src/model/*.java src/operations/*.java src/exception/*.java src/service/*.java src/ui/*.java src/main/*.java
```

### Run the GUI (interactive — for showing individual features):
```
java -cp out main.LibraryManagementSystem
```

### Run the Demo (automated — for team presentation):
```
java -cp out main.DemoRunner
```

---

## Features

| Feature                    | Description                                      |
|----------------------------|--------------------------------------------------|
| Add Books                  | With unique ID enforcement and copy count         |
| Add Members                | With ID (3+ digits), name, phone (10 digits) validation |
| Search Books               | By title/author keyword OR by book ID             |
| Issue Books                | Searchable dropdown of available books, copy tracking |
| Return Books               | Returns copy, increases available count            |
| View All Books             | Table with sort by title / sort by author          |
| View All Members           | Table showing borrow count and limit               |
| Dashboard                  | Shows totals + polymorphism demo (People table)    |
| Unique ID enforcement      | Both book IDs and member IDs must be unique        |
| Copy tracking              | Each book has totalCopies and availableCopies      |

---

## OOP Concepts Demonstrated

| Concept                | Implementation                                       |
|------------------------|------------------------------------------------------|
| Abstract Class         | `Person` with abstract `displayInfo()`               |
| Inheritance            | `Member extends Person`, `Librarian extends Person`  |
| Interface              | `LibraryOperations` implemented by `Library`         |
| Polymorphism           | `Person` list calling `displayInfo()` — different output per type |
| Constructors           | Parameterized constructors in all classes             |
| Constructor Overloading| `Member` (3-arg, 4-arg), `Book` (4-arg, 5-arg)      |
| Method Overloading     | `searchBook(int)` vs `searchBook(String)`            |
| Method Overriding      | `displayInfo()`, `toString()` in all subclasses      |
| Encapsulation          | Private fields + public getters/setters              |

## Collections Used

| Collection                 | Purpose                | Why                          |
|----------------------------|------------------------|------------------------------|
| `ArrayList<Book>`          | Store all books        | Ordered, sortable, iterable  |
| `HashMap<Integer, Member>` | Store all members      | O(1) lookup by member ID     |
| `ArrayList<int[]>`         | Borrow records         | Supports multi-copy borrows  |
| `ArrayList<Book>`          | Per-member borrow list | Simple add/remove tracking   |

## Custom Exceptions

| Exception                   | Trigger                           |
|-----------------------------|-----------------------------------|
| `BookNotAvailableException` | Book not found / no copies left   |
| `InvalidMemberException`    | Member ID not found               |
| `InvalidBorrowException`    | Wrong return / limit reached      |

---

## Deliverables Checklist

| Deliverable                    | File / Location                  | Status |
|--------------------------------|----------------------------------|--------|
| Java source code               | `src/` (11 Java files)           | Done   |
| Class / design diagram         | `docs/01_Class_Diagram.md`       | Done   |
| Collection selection explanation| `docs/02_Collection_Selection.md`| Done   |
| Exception handling explanation | `docs/03_Exception_Handling.md`  | Done   |
| Sample input/output            | `docs/04_Sample_Input_Output.md` | Done   |
| Team demo                      | `DemoRunner.java` (automated)    | Done   |
| Interactive demo               | `LibraryManagementSystem.java`   | Done   |

---

## Team Demo Guide

1. **Start with DemoRunner** — run `java -cp out main.DemoRunner` to show all 12 concepts automatically with explanations
2. **Switch to GUI** — run `java -cp out main.LibraryManagementSystem` to show the interactive interface
3. **Walk through panels** — Dashboard, All Books, Search, Add Book, Members, Issue Book, Return Book
4. **Show validations** — try duplicate IDs, empty names, bad phone numbers, issuing borrowed books
5. **Reference docs** — use the markdown files in `docs/` for explanation slides if needed
