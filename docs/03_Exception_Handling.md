# Exception Handling Explanation

## Overview

The Library Management System uses **3 custom exception classes** that extend Java's `Exception` class. These are **checked exceptions** — the compiler forces callers to handle them with try-catch or declare them with `throws`.

---

## Custom Exception Classes

### 1. BookNotAvailableException

**File:** `src/exception/BookNotAvailableException.java`

**Thrown when:**
- A book ID is not found in the library (search, issue, remove)
- All copies of a book are already borrowed (issue)

**Code:**
```java
public class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String message) {
        super(message);
    }
}
```

**Where it's thrown in Library.java:**

| Method             | Condition                            | Message Example                                      |
|--------------------|--------------------------------------|------------------------------------------------------|
| `searchBook(int)`  | Book ID doesn't exist                | "No book found with ID: 999"                        |
| `issueBook(...)`   | Book ID doesn't exist                | "Book with ID 999 does not exist."                   |
| `issueBook(...)`   | All copies are borrowed              | "Book '1984' -- all copies are borrowed."            |
| `removeBook(int)`  | Book ID doesn't exist                | "Book with ID 999 not found in the library."         |
| `removeBook(int)`  | Book has borrowed copies             | "Book '1984' has borrowed copies and cannot be removed." |
| `returnBook(...)`  | Book ID doesn't exist                | "Book with ID 999 does not exist."                   |

---

### 2. InvalidMemberException

**File:** `src/exception/InvalidMemberException.java`

**Thrown when:**
- A member ID is not found in the system

**Code:**
```java
public class InvalidMemberException extends Exception {
    public InvalidMemberException(String message) {
        super(message);
    }
}
```

**Where it's thrown in Library.java:**

| Method             | Condition                    | Message Example                           |
|--------------------|------------------------------|-------------------------------------------|
| `issueBook(...)`   | Member ID doesn't exist      | "Member with ID 999 does not exist."      |
| `returnBook(...)`  | Member ID doesn't exist      | "Member with ID 999 does not exist."      |
| `getMember(int)`   | Member ID not found          | "Member with ID 999 not found."           |

---

### 3. InvalidBorrowException

**File:** `src/exception/InvalidBorrowException.java`

**Thrown when:**
- A member tries to borrow more books than their limit
- A member tries to return a book they didn't borrow

**Code:**
```java
public class InvalidBorrowException extends Exception {
    public InvalidBorrowException(String message) {
        super(message);
    }
}
```

**Where it's thrown in Library.java:**

| Method             | Condition                             | Message Example                                                    |
|--------------------|---------------------------------------|--------------------------------------------------------------------|
| `issueBook(...)`   | Member at borrow limit                | "Member 'Alice' has reached the maximum borrowing limit of 3 books." |
| `returnBook(...)`  | Book wasn't borrowed by this member   | "Book '1984' was not borrowed by member 'Bob Smith'."              |

---

## How Exceptions Are Caught

### In the GUI (LibraryGUI.java)

Every button action that calls Library methods wraps the call in try-catch:

```java
issueBtn.addActionListener(e -> {
    try {
        library.issueBook(bookId, memberId);
        // success message
    } catch (BookNotAvailableException ex) {
        resultArea.setText("ERROR: " + ex.getMessage());
        setStatus(ex.getMessage(), false);  // red status bar
    } catch (InvalidMemberException ex) {
        resultArea.setText("ERROR: " + ex.getMessage());
        setStatus(ex.getMessage(), false);
    } catch (InvalidBorrowException ex) {
        resultArea.setText("ERROR: " + ex.getMessage());
        setStatus(ex.getMessage(), false);
    }
});
```

The user sees:
- Error text in the result area on the panel
- Red status bar at the bottom with the error message

### In the DemoRunner (DemoRunner.java)

Each exception scenario is explicitly tested:

```java
System.out.println("Attempting to issue Book ID 5 (all copies borrowed)...");
try {
    library.issueBook(5, 103);
} catch (BookNotAvailableException e) {
    System.out.println("CAUGHT BookNotAvailableException: " + e.getMessage());
}
```

---

## Exception Flow Diagram

```
User clicks "Issue Book"
        |
        v
  issueBook(bookId, memberId)
        |
        +---> Book not found? -----> throw BookNotAvailableException
        |
        +---> All copies out? -----> throw BookNotAvailableException
        |
        +---> Member not found? ---> throw InvalidMemberException
        |
        +---> Member at limit? ----> throw InvalidBorrowException
        |
        v
   SUCCESS: book.borrowCopy()
            member.addBorrowedBook()
            borrowRecords.add(...)
```

```
User clicks "Return Book"
        |
        v
  returnBook(bookId, memberId)
        |
        +---> Book not found? -----> throw BookNotAvailableException
        |
        +---> Member not found? ---> throw InvalidMemberException
        |
        +---> No matching borrow     throw InvalidBorrowException
        |     record for this
        |     member + book?
        |
        v
   SUCCESS: book.returnCopy()
            member.removeBorrowedBook()
            borrowRecords.remove(...)
```

---

## Why Custom Exceptions Instead of Generic Ones?

| Approach                | Problem                                                |
|-------------------------|--------------------------------------------------------|
| `throw new Exception()` | Too generic — caller can't distinguish error types     |
| Return `null` / `-1`    | Easy to forget to check, leads to NullPointerException |
| Print error + continue  | Caller doesn't know something failed                   |

**Custom exceptions give us:**
1. **Specific error types** — caller can catch and handle each differently
2. **Descriptive messages** — user sees exactly what went wrong
3. **Compiler safety** — checked exceptions force callers to handle them
4. **Clean separation** — error logic stays in the service layer, display logic stays in UI

---

## Summary

| Exception                    | Extends   | # of throw sites | Primary Trigger            |
|------------------------------|-----------|-------------------|----------------------------|
| `BookNotAvailableException`  | Exception | 6                 | Book not found / no copies |
| `InvalidMemberException`     | Exception | 3                 | Member not found           |
| `InvalidBorrowException`     | Exception | 2                 | Bad borrow/return logic    |
