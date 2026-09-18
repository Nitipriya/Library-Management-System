# Library Management System

A Java-based Library Management System demonstrating OOP concepts, Java Collections, Interfaces, Abstraction, and Custom Exception Handling.

## How to Compile

```bash
javac -d out src/model/*.java src/operations/*.java src/exception/*.java src/service/*.java src/ui/*.java src/main/*.java
```

## How to Run

**Interactive GUI** (for showing individual features):
```bash
java -cp out main.LibraryManagementSystem
```

**Automated Team Demo** (covers all concepts with pre-loaded data):
```bash
java -cp out main.DemoRunner
```

## Features

- Add / Search / Sort Books (with copy tracking)
- Add Members (with validation: unique ID, 10-digit phone)
- Issue Books (searchable dropdown of available books)
- Return Books (copies auto-update)
- Dashboard with live stats
- Swing GUI with sidebar navigation

## OOP Concepts Covered

| Concept | Where |
|---|---|
| Abstract Class | `Person` with abstract `displayInfo()` |
| Inheritance | `Member` and `Librarian` extend `Person` |
| Interface | `LibraryOperations` implemented by `Library` |
| Polymorphism | `Person` references calling overridden methods |
| Constructor Overloading | `Member` (3-arg, 4-arg), `Book` (4-arg, 5-arg) |
| Method Overloading | `searchBook(int)` vs `searchBook(String)` |
| Method Overriding | `displayInfo()`, `toString()` in subclasses |
| Collections | `ArrayList`, `HashMap`, `Collections.sort()` |
| Custom Exceptions | `BookNotAvailableException`, `InvalidMemberException`, `InvalidBorrowException` |

## Documentation

Detailed docs are in the `docs/` folder:
- `01_Class_Diagram.md` — Class hierarchy and relationships
- `02_Collection_Selection.md` — Why each collection was chosen
- `03_Exception_Handling.md` — Custom exceptions explained
- `04_Sample_Input_Output.md` — Full demo output and test cases
