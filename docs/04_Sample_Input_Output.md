# Sample Input / Output

This document shows the actual output from running `DemoRunner.java`, which exercises every feature of the system automatically.

**Command to run:** `java -cp out main.DemoRunner`

---

## Full Demo Output

```
+==================================================================+
|           LIBRARY MANAGEMENT SYSTEM -- TEAM DEMO                 |
|         Automated Demonstration of All Features                  |
+==================================================================+
|  Concepts covered:                                               |
|  - Inheritance, Constructors, Method Overloading/Overriding      |
|  - Abstract Classes, Interfaces, Polymorphism                    |
|  - Collections (ArrayList, HashMap), Sorting (Comparator)        |
|  - Custom Exception Handling                                     |
|  - Copy Tracking (available/total copies per book)               |
+==================================================================+

--------------------------------------------------------------------
  1. CONSTRUCTORS & INHERITANCE
--------------------------------------------------------------------

Creating a Librarian (extends Person) using parameterized constructor:
  -> Librarian | ID: 100 | Name: Ms. Sarah Johnson | Contact: sarah@library.com | Employee ID: EMP001

Creating Members (extends Person) using parameterized constructors:
  Constructor 1 -- Member(id, name, contact) -- default max books = 3:
  -> Member | ID: 101 | Name: Alice Brown | Contact: 9876543210 | Books Borrowed: 0/3

  Constructor 2 -- Member(id, name, contact, maxBooks) -- OVERLOADED constructor:
  -> Member | ID: 102 | Name: Bob Smith | Contact: 8765432109 | Books Borrowed: 0/3
  -> Member | ID: 103 | Name: Charlie Davis | Contact: 7654321098 | Books Borrowed: 0/5  (custom limit of 5)

  * Inheritance: Both Member and Librarian extend abstract class Person.
  * Constructor Chaining: Member/Librarian call super(id, name, contact).
  * Constructor Overloading: Member has 2 constructors (3-arg and 4-arg).

--------------------------------------------------------------------
  2. ABSTRACT CLASS & POLYMORPHISM
--------------------------------------------------------------------

Person is an ABSTRACT CLASS with abstract method displayInfo().
We store all people as Person references and call displayInfo():

  [Librarian] Librarian | ID: 100 | Name: Ms. Sarah Johnson | Contact: sarah@library.com | Employee ID: EMP001
  [Member] Member | ID: 101 | Name: Alice Brown | Contact: 9876543210 | Books Borrowed: 0/3
  [Member] Member | ID: 102 | Name: Bob Smith | Contact: 8765432109 | Books Borrowed: 0/3
  [Member] Member | ID: 103 | Name: Charlie Davis | Contact: 7654321098 | Books Borrowed: 0/5

  * Polymorphism: Same method call (displayInfo()) gives different results
    depending on the actual object type (Librarian vs Member).
  * Cannot instantiate Person directly -- it is abstract.

--------------------------------------------------------------------
  3. INTERFACE -- LibraryOperations
--------------------------------------------------------------------

Library class implements the LibraryOperations interface.
Interface methods: addBook, removeBook, searchBook (overloaded),
  issueBook, returnBook, getAllBooks, getAvailableBooks,
  addMember, getMember, getAllMembers.

  * The interface defines WHAT operations the library supports.
  * The Library class provides HOW they are implemented.

--------------------------------------------------------------------
  4. COLLECTIONS -- Adding Books (ArrayList)
--------------------------------------------------------------------

Books are stored in an ArrayList<Book>.
Each book now tracks totalCopies and availableCopies:

  Added: ID: 1 | The Great Gatsby by F. Scott Fitzgerald | Genre: Fiction | Copies: 3/3 | Status: Available
  Added: ID: 2 | To Kill a Mockingbird by Harper Lee | Genre: Fiction | Copies: 2/2 | Status: Available
  Added: ID: 3 | 1984 by George Orwell | Genre: Dystopian | Copies: 4/4 | Status: Available
  Added: ID: 4 | Pride and Prejudice by Jane Austen | Genre: Romance | Copies: 2/2 | Status: Available
  Added: ID: 5 | The Catcher in the Rye by J.D. Salinger | Genre: Fiction | Copies: 1/1 | Status: Available
  Added: ID: 6 | Data Structures in Java by Robert Lafore | Genre: Education | Copies: 5/5 | Status: Available
  Added: ID: 7 | Clean Code by Robert C. Martin | Genre: Technology | Copies: 3/3 | Status: Available
  Added: ID: 8 | The Hobbit by J.R.R. Tolkien | Genre: Fantasy | Copies: 2/2 | Status: Available

  Total books in library: 8

--------------------------------------------------------------------
  5. COLLECTIONS -- Members (HashMap)
--------------------------------------------------------------------

Members are stored in a HashMap<Integer, Member>.
  Current members (already added):
  -> Member | ID: 101 | Name: Alice Brown | Contact: 9876543210 | Books Borrowed: 0/3
  -> Member | ID: 102 | Name: Bob Smith | Contact: 8765432109 | Books Borrowed: 0/3
  -> Member | ID: 103 | Name: Charlie Davis | Contact: 7654321098 | Books Borrowed: 0/5

--------------------------------------------------------------------
  6. METHOD OVERLOADING -- searchBook()
--------------------------------------------------------------------

searchBook is OVERLOADED -- two versions with different parameters:

  Version 1: searchBook(int bookId) -- Search by ID:
  -> searchBook(3) found: ID: 3 | 1984 by George Orwell | Genre: Dystopian | Copies: 4/4 | Status: Available

  Version 2: searchBook(String keyword) -- Search by title/author keyword:
  -> searchBook("Java") found 1 result(s):
     ID: 6 | Data Structures in Java by Robert Lafore | Genre: Education | Copies: 5/5 | Status: Available
  -> searchBook("Orwell") found 1 result(s):
     ID: 3 | 1984 by George Orwell | Genre: Dystopian | Copies: 4/4 | Status: Available

--------------------------------------------------------------------
  7. SORTING with Collections.sort() & Comparator
--------------------------------------------------------------------

Sorting books by TITLE (alphabetical):
  1984 -- George Orwell
  Clean Code -- Robert C. Martin
  Data Structures in Java -- Robert Lafore
  Pride and Prejudice -- Jane Austen
  The Catcher in the Rye -- J.D. Salinger
  The Great Gatsby -- F. Scott Fitzgerald
  The Hobbit -- J.R.R. Tolkien
  To Kill a Mockingbird -- Harper Lee

Sorting books by AUTHOR (alphabetical):
  F. Scott Fitzgerald -- The Great Gatsby
  George Orwell -- 1984
  Harper Lee -- To Kill a Mockingbird
  J.D. Salinger -- The Catcher in the Rye
  J.R.R. Tolkien -- The Hobbit
  Jane Austen -- Pride and Prejudice
  Robert C. Martin -- Clean Code
  Robert Lafore -- Data Structures in Java

--------------------------------------------------------------------
  8. ISSUING BOOKS (with copy tracking)
--------------------------------------------------------------------

Issuing books to members -- available copies decrease on issue...

  -> Alice borrows 'The Great Gatsby' -- SUCCESS
     Copies: 2/3 available
  -> Bob also borrows 'The Great Gatsby' (another copy) -- SUCCESS
     Copies: 1/3 available
  -> Alice borrows '1984' -- SUCCESS
  -> Bob borrows 'The Catcher in the Rye' -- SUCCESS
     Copies: 0/1 available
  -> Charlie borrows 'Clean Code' -- SUCCESS

--------------------------------------------------------------------
  9. RETURNING BOOKS (copies increase on return)
--------------------------------------------------------------------

Returning a book -- available copies increase...

  'The Great Gatsby' before return:
     Copies: 1/3 available
  Returning Book ID 1 from Member ID 101 (Alice returns 'The Great Gatsby'):
  -> SUCCESS! One copy returned.
     Copies: 2/3 available

--------------------------------------------------------------------
  10. CHECKING AVAILABILITY
--------------------------------------------------------------------

All books with their copy counts:

  ID: 1 | The Great Gatsby by F. Scott Fitzgerald | Genre: Fiction | Copies: 2/3 | Status: Available
  ID: 2 | To Kill a Mockingbird by Harper Lee | Genre: Fiction | Copies: 2/2 | Status: Available
  ID: 3 | 1984 by George Orwell | Genre: Dystopian | Copies: 3/4 | Status: Available
  ID: 4 | Pride and Prejudice by Jane Austen | Genre: Romance | Copies: 2/2 | Status: Available
  ID: 5 | The Catcher in the Rye by J.D. Salinger | Genre: Fiction | Copies: 0/1 | Status: All Borrowed
  ID: 6 | Data Structures in Java by Robert Lafore | Genre: Education | Copies: 5/5 | Status: Available
  ID: 7 | Clean Code by Robert C. Martin | Genre: Technology | Copies: 2/3 | Status: Available
  ID: 8 | The Hobbit by J.R.R. Tolkien | Genre: Fantasy | Copies: 2/2 | Status: Available

  Books with copies available: 7 / 8 total titles
  Total borrow records: 4

--------------------------------------------------------------------
  11. CUSTOM EXCEPTION HANDLING
--------------------------------------------------------------------

Testing BookNotAvailableException (all copies borrowed):
  'The Catcher in the Rye' has only 1 copy, already borrowed by Bob.
  Attempting to issue it to Charlie...
  -> CAUGHT BookNotAvailableException: Book 'The Catcher in the Rye' -- all copies are borrowed.

Testing InvalidMemberException:
  Attempting to issue to Member ID 999 (does not exist)...
  -> CAUGHT InvalidMemberException: Member with ID 999 does not exist.

Testing InvalidBorrowException (wrong member returning):
  Attempting to return Book ID 3 from Member ID 102 (Bob didn't borrow it)...
  -> CAUGHT InvalidBorrowException: Book '1984' was not borrowed by member 'Bob Smith'.

Testing BookNotAvailableException (search non-existent):
  Searching for Book ID 999 (does not exist)...
  -> CAUGHT BookNotAvailableException: No book found with ID: 999

Testing InvalidBorrowException (borrow limit):
  Alice has 1 book. Issuing 2 more to reach her limit of 3...
  -> Issued Book ID 2 to Alice (2/3)
  -> Issued Book ID 4 to Alice (3/3 -- at limit!)
  Now attempting to issue a 4th book...
  -> CAUGHT InvalidBorrowException: Member 'Alice Brown' has reached the maximum borrowing limit of 3 books.

--------------------------------------------------------------------
  12. METHOD OVERRIDING -- toString() and displayInfo()
--------------------------------------------------------------------

Both toString() and displayInfo() are OVERRIDDEN in subclasses:

  Librarian.toString(): Librarian[id=100, name=Ms. Sarah Johnson, empId=EMP001]
  Librarian.displayInfo(): Librarian | ID: 100 | Name: Ms. Sarah Johnson | Contact: sarah@library.com | Employee ID: EMP001

  Member.toString(): Member[id=101, name=Alice Brown, borrowed=3]
  Member.displayInfo(): Member | ID: 101 | Name: Alice Brown | Contact: 9876543210 | Books Borrowed: 3/3

  Member.toString(): Member[id=102, name=Bob Smith, borrowed=2]
  Member.displayInfo(): Member | ID: 102 | Name: Bob Smith | Contact: 8765432109 | Books Borrowed: 2/3

  Member.toString(): Member[id=103, name=Charlie Davis, borrowed=1]
  Member.displayInfo(): Member | ID: 103 | Name: Charlie Davis | Contact: 7654321098 | Books Borrowed: 1/5

  Book.toString() example: Book[id=1, title=The Great Gatsby, author=F. Scott Fitzgerald, genre=Fiction, copies=2/3]
  Book.displayInfo() example: ID: 1 | The Great Gatsby by F. Scott Fitzgerald | Genre: Fiction | Copies: 2/3 | Status: Available

  * toString() overrides Object.toString()
  * displayInfo() overrides the abstract method in Person

+==================================================================+
|                    DEMO COMPLETE!                                |
|  All features demonstrated successfully.                         |
+==================================================================+
```

---

## GUI Interactions (Manual Testing Scenarios)

### Adding a Book (with validation)

| Input                | Expected Result                              |
|----------------------|----------------------------------------------|
| ID: 9, Title: "Java Basics", Author: "John", Genre: "Education", Copies: 2 | Book added successfully |
| ID: 1, Title: "Duplicate", Author: "X", Genre: "Y", Copies: 1 | Error: "Book ID 1 already exists" |
| ID: empty            | Error: "Book ID must be a number"            |

### Adding a Member (with validation)

| Input                        | Expected Result                                  |
|------------------------------|--------------------------------------------------|
| ID: 200, Name: "David", Phone: 9123456789 | Member added successfully          |
| ID: 101, Name: "X", Phone: 9123456789     | Error: "Member ID 101 already exists" |
| ID: 12, Name: "X", Phone: 9123456789      | Error: "Member ID must be at least 3 digits" |
| ID: 200, Name: "", Phone: 9123456789      | Error: "Name cannot be empty"        |
| ID: 200, Name: "X", Phone: 0123456789     | Error: "Phone cannot start with 0"  |
| ID: 200, Name: "X", Phone: 12345          | Error: "Phone must be exactly 10 digits" |

### Issuing a Book

| Action                       | Expected Result                                  |
|------------------------------|--------------------------------------------------|
| Search "gatsby" in search bar | Dropdown filters to show only The Great Gatsby   |
| Select book, enter member 101 | Book issued, copies decrease                     |
| Issue all copies of a book    | Error: "all copies are borrowed"                 |

### Returning a Book

| Input                        | Expected Result                                  |
|------------------------------|--------------------------------------------------|
| Book ID: 1, Member ID: 101  | Book returned, copies increase                   |
| Book ID: 2, Member ID: 103  | Error: "not borrowed by member"                  |
