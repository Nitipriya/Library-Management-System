# Collection Selection Explanation

## Overview

This document explains **why** each Java Collection type was chosen in the Library Management System and what alternatives were considered.

---

## 1. ArrayList\<Book\> — Storing All Books

**Used in:** `Library.java` — `private ArrayList<Book> books;`

**Why ArrayList?**

| Factor               | ArrayList Advantage                                      |
|----------------------|----------------------------------------------------------|
| **Ordered**          | Maintains insertion order — books appear in the order added |
| **Indexed access**   | Can access books by index position — `books.get(i)`      |
| **Iteration**        | Efficient sequential iteration for listing all books      |
| **Sorting**          | Works directly with `Collections.sort()` and `Comparator` |
| **Frequency of ops** | Books are listed/searched more often than added/removed   |

**Alternatives Considered:**

| Alternative    | Why Not Used                                               |
|----------------|------------------------------------------------------------|
| `LinkedList`   | Slower random access. We don't insert/remove in the middle frequently |
| `HashSet`      | No duplicates allowed, no indexed access, no ordering      |
| `HashMap`      | Could work (bookId -> Book), but we need ordered iteration and sorting frequently |

**Where it's used:**
- `getAllBooks()` — returns full list
- `getAvailableBooks()` — filters and returns available books
- `getBooksSortedByTitle()` — sorts using `Collections.sort(sorted, Comparator.comparing(Book::getTitle))`
- `getBooksSortedByAuthor()` — sorts using `Comparator.comparing(Book::getAuthor)`
- `searchBook(String keyword)` — iterates through all books to find matches

---

## 2. HashMap\<Integer, Member\> — Storing Members

**Used in:** `Library.java` — `private HashMap<Integer, Member> members;`

**Why HashMap?**

| Factor               | HashMap Advantage                                       |
|----------------------|---------------------------------------------------------|
| **O(1) lookup**      | Get a member instantly by ID — `members.get(memberId)`  |
| **Key-value pairs**  | Natural fit: memberId (key) -> Member object (value)    |
| **Frequency of ops** | Members are looked up by ID on every issue/return operation |
| **Unique keys**      | Automatically prevents duplicate member IDs              |

**Alternatives Considered:**

| Alternative       | Why Not Used                                            |
|-------------------|---------------------------------------------------------|
| `ArrayList`       | Would require O(n) linear search to find by ID          |
| `TreeMap`         | Sorted order not needed for members — O(log n) is slower than O(1) |
| `LinkedHashMap`   | Could work if we needed insertion order, but not required here |

**Where it's used:**
- `addMember(Member)` — `members.put(member.getId(), member)`
- `getMember(int memberId)` — `members.get(memberId)` — O(1) lookup
- `issueBook(...)` — validates member exists via HashMap lookup
- `returnBook(...)` — validates member exists via HashMap lookup
- `memberIdExists(int)` — `members.containsKey(memberId)` — O(1) check
- `getAllMembers()` — `members.values()` to get all member objects

---

## 3. ArrayList\<int[]\> — Borrow Records

**Used in:** `Library.java` — `private ArrayList<int[]> borrowRecords;`

Each entry is `{bookId, memberId}` — recording which member borrowed which book.

**Why ArrayList of int arrays?**

| Factor               | Advantage                                               |
|----------------------|---------------------------------------------------------|
| **Multiple borrows** | Same book can appear multiple times (one per copy borrowed) |
| **Simple structure** | Each record is just two ints — lightweight               |
| **Iteration**        | Easy to iterate through to find/remove a specific record |
| **Flexible**         | Supports the copies model — Book ID 1 can have 3 borrow records |

**Alternatives Considered:**

| Alternative                  | Why Not Used                                      |
|------------------------------|---------------------------------------------------|
| `HashMap<Integer, Integer>`  | Only supports one borrow per book — breaks with multiple copies |
| `HashMap<Integer, List<Integer>>` | More complex, overkill for this use case    |
| `ArrayList<BorrowRecord>`    | Could create a dedicated class, but int[] is simpler for this scope |

**Where it's used:**
- `issueBook(...)` — adds `new int[]{bookId, memberId}`
- `returnBook(...)` — searches and removes matching record
- `getBorrowRecordsSummary()` — formats all records for display
- `getTotalBorrowed()` — `borrowRecords.size()`

---

## 4. ArrayList\<Book\> — Member's Borrowed Books

**Used in:** `Member.java` — `private List<Book> borrowedBooks;`

**Why ArrayList?**

| Factor               | Advantage                                               |
|----------------------|---------------------------------------------------------|
| **Ordered**          | Tracks books in the order they were borrowed             |
| **Size tracking**    | `borrowedBooks.size()` easily checks against `maxBooks`  |
| **Iteration**        | Can list what a member currently has                     |
| **Add/Remove**       | Simple `add()` and `remove()` for borrow/return          |

---

## Sorting — Collections.sort() with Comparator

**Used in:** `Library.java`

```java
// Sort by title
Collections.sort(sorted, Comparator.comparing(Book::getTitle));

// Sort by author
Collections.sort(sorted, Comparator.comparing(Book::getAuthor));
```

- `Collections.sort()` is the standard Java utility for sorting lists
- `Comparator.comparing()` provides a clean, functional way to define sort keys
- Method references (`Book::getTitle`) make the code readable
- Sorting creates a new list (doesn't mutate the original)

---

## Summary Table

| Collection                     | Used For           | Key Reason                    | File          |
|--------------------------------|--------------------|-------------------------------|---------------|
| `ArrayList<Book>`              | All books          | Ordered, sortable, iterable   | Library.java  |
| `HashMap<Integer, Member>`     | All members        | O(1) lookup by member ID      | Library.java  |
| `ArrayList<int[]>`             | Borrow records     | Supports multi-copy borrows   | Library.java  |
| `ArrayList<Book>`              | Member's borrowed  | Track per-member borrow list  | Member.java   |
