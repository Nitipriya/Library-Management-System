package ui;

import model.Book;
import model.Member;
import model.Person;
import service.Library;
import exception.BookNotAvailableException;
import exception.InvalidMemberException;
import exception.InvalidBorrowException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Swing-based GUI for the Library Management System.
 * Clean, minimal design with a sidebar navigation and content panel.
 */
public class LibraryGUI extends JFrame {

    private Library library;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JLabel statusLabel;

    // Color scheme
    private static final Color SIDEBAR_BG = new Color(44, 62, 80);
    private static final Color SIDEBAR_BTN = new Color(52, 73, 94);
    private static final Color SIDEBAR_BTN_HOVER = new Color(41, 128, 185);
    private static final Color CONTENT_BG = new Color(236, 240, 241);
    private static final Color HEADER_BG = new Color(41, 128, 185);
    private static final Color WHITE = Color.WHITE;
    private static final Color TEXT_DARK = new Color(44, 62, 80);
    private static final Color SUCCESS_GREEN = new Color(39, 174, 96);
    private static final Color ERROR_RED = new Color(192, 57, 43);
    private static final Color TABLE_HEADER_BG = new Color(41, 128, 185);
    private static final Color TABLE_HEADER_FG = Color.WHITE;

    public LibraryGUI(Library library) {
        this.library = library;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 680);
        setMinimumSize(new Dimension(950, 580));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        add(createHeader(), BorderLayout.NORTH);

        // Sidebar
        add(createSidebar(), BorderLayout.WEST);

        // Main content area with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(CONTENT_BG);

        // Add all panels
        contentPanel.add(createDashboardPanel(), "dashboard");
        contentPanel.add(createAllBooksPanel(), "allBooks");
        contentPanel.add(createSearchPanel(), "search");
        contentPanel.add(createAddBookPanel(), "addBook");
        contentPanel.add(createMembersPanel(), "members");
        contentPanel.add(createAddMemberPanel(), "addMember");
        contentPanel.add(createIssueBookPanel(), "issueBook");
        contentPanel.add(createReturnBookPanel(), "returnBook");

        add(contentPanel, BorderLayout.CENTER);

        // Status bar
        statusLabel = new JLabel("  Ready");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(44, 62, 80));
        statusLabel.setForeground(WHITE);
        statusLabel.setPreferredSize(new Dimension(0, 25));
        add(statusLabel, BorderLayout.SOUTH);

        cardLayout.show(contentPanel, "dashboard");
    }

    // ==================== HEADER ====================

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(HEADER_BG);
        header.setPreferredSize(new Dimension(0, 55));
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("Library Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(WHITE);
        header.add(title, BorderLayout.WEST);

        String libName = library.getLibrarian() != null ? library.getLibrarian().getName() : "Admin";
        JLabel libLabel = new JLabel("Librarian: " + libName + "  ");
        libLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        libLabel.setForeground(new Color(189, 195, 199));
        header.add(libLabel, BorderLayout.EAST);

        return header;
    }

    // ==================== SIDEBAR ====================

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(190, 0));
        sidebar.setBorder(new EmptyBorder(10, 0, 10, 0));

        sidebar.add(createSidebarButton("Dashboard", e -> showPanel("dashboard")));
        sidebar.add(createSidebarButton("All Books", e -> refreshAndShow("allBooks")));
        sidebar.add(createSidebarButton("Search Books", e -> showPanel("search")));
        sidebar.add(createSidebarButton("Add Book", e -> showPanel("addBook")));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarSeparator());
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarButton("All Members", e -> refreshAndShow("members")));
        sidebar.add(createSidebarButton("Add Member", e -> showPanel("addMember")));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarSeparator());
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarButton("Issue Book", e -> refreshAndShow("issueBook")));
        sidebar.add(createSidebarButton("Return Book", e -> showPanel("returnBook")));
        sidebar.add(Box.createVerticalGlue());

        return sidebar;
    }

    private JButton createSidebarButton(String text, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(170, 38));
        btn.setPreferredSize(new Dimension(170, 38));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(WHITE);
        btn.setBackground(SIDEBAR_BTN);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(SIDEBAR_BTN_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(SIDEBAR_BTN);
            }
        });

        btn.addActionListener(action);
        return btn;
    }

    private JSeparator createSidebarSeparator() {
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setMaximumSize(new Dimension(160, 1));
        sep.setForeground(new Color(100, 120, 140));
        return sep;
    }

    // ==================== DASHBOARD PANEL ====================

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel heading = createHeading("Dashboard");
        panel.add(heading, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(1, 3, 20, 20));
        cards.setBackground(CONTENT_BG);
        cards.setBorder(new EmptyBorder(30, 0, 0, 0));

        cards.add(createDashCard("Total Books", String.valueOf(library.getTotalBooks()), new Color(41, 128, 185)));
        cards.add(createDashCard("Total Members", String.valueOf(library.getTotalMembers()), new Color(39, 174, 96)));
        cards.add(createDashCard("Books Borrowed", String.valueOf(library.getTotalBorrowed()), new Color(192, 57, 43)));

        panel.add(cards, BorderLayout.CENTER);

        // Polymorphism demo section
        JPanel polyPanel = new JPanel(new BorderLayout());
        polyPanel.setBackground(CONTENT_BG);
        polyPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        JLabel polyHeading = new JLabel("People in System (Polymorphism Demo)");
        polyHeading.setFont(new Font("Segoe UI", Font.BOLD, 14));
        polyHeading.setForeground(TEXT_DARK);
        polyPanel.add(polyHeading, BorderLayout.NORTH);

        DefaultTableModel polyModel = new DefaultTableModel(new String[]{"Type", "Info"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        for (Person person : library.getAllPeople()) {
            String type = person instanceof model.Librarian ? "Librarian" : "Member";
            polyModel.addRow(new Object[]{type, person.displayInfo()});
        }

        JTable polyTable = createStyledTable(polyModel);
        JScrollPane polyScroll = new JScrollPane(polyTable);
        polyScroll.setPreferredSize(new Dimension(0, 150));
        polyScroll.setBorder(BorderFactory.createEmptyBorder());
        polyPanel.add(polyScroll, BorderLayout.CENTER);

        panel.add(polyPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createDashCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                new EmptyBorder(20, 20, 20, 20)));

        JLabel valLabel = new JLabel(value, SwingConstants.CENTER);
        valLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        valLabel.setForeground(color);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(TEXT_DARK);

        card.add(valLabel, BorderLayout.CENTER);
        card.add(titleLabel, BorderLayout.SOUTH);
        return card;
    }

    // ==================== ALL BOOKS PANEL ====================

    private JPanel allBooksTablePanel;

    private JPanel createAllBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(CONTENT_BG);
        topBar.add(createHeading("All Books"), BorderLayout.WEST);

        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sortPanel.setBackground(CONTENT_BG);
        JButton sortTitle = createActionButton("Sort by Title", new Color(41, 128, 185));
        JButton sortAuthor = createActionButton("Sort by Author", new Color(41, 128, 185));
        sortTitle.addActionListener(e -> refreshBooksTable(library.getBooksSortedByTitle()));
        sortAuthor.addActionListener(e -> refreshBooksTable(library.getBooksSortedByAuthor()));
        sortPanel.add(sortTitle);
        sortPanel.add(sortAuthor);
        topBar.add(sortPanel, BorderLayout.EAST);

        panel.add(topBar, BorderLayout.NORTH);

        allBooksTablePanel = new JPanel(new BorderLayout());
        allBooksTablePanel.setBackground(CONTENT_BG);
        refreshBooksTable(library.getAllBooks());
        panel.add(allBooksTablePanel, BorderLayout.CENTER);

        return panel;
    }

    private void refreshBooksTable(List<Book> bookList) {
        allBooksTablePanel.removeAll();
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Title", "Author", "Genre", "Available", "Total", "Status"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Book b : bookList) {
            String status = b.isAvailable() ? "Available" : "All Borrowed";
            model.addRow(new Object[]{b.getBookId(), b.getTitle(), b.getAuthor(),
                    b.getGenre(), b.getAvailableCopies(), b.getTotalCopies(), status});
        }
        JTable table = createStyledTable(model);
        allBooksTablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        allBooksTablePanel.revalidate();
        allBooksTablePanel.repaint();
    }

    // ==================== SEARCH PANEL ====================

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(createHeading("Search Books"), BorderLayout.NORTH);

        JPanel searchArea = new JPanel(new BorderLayout(10, 10));
        searchArea.setBackground(CONTENT_BG);
        searchArea.setBorder(new EmptyBorder(15, 0, 10, 0));

        JPanel inputRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        inputRow.setBackground(CONTENT_BG);

        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(250, 32));

        JTextField idField = new JTextField(6);
        idField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        idField.setPreferredSize(new Dimension(80, 32));

        JButton searchByName = createActionButton("Search by Title/Author", SIDEBAR_BTN_HOVER);
        JButton searchById = createActionButton("Search by ID", SIDEBAR_BTN_HOVER);

        inputRow.add(new JLabel("Keyword: "));
        inputRow.add(searchField);
        inputRow.add(searchByName);
        inputRow.add(Box.createHorizontalStrut(20));
        inputRow.add(new JLabel("Book ID: "));
        inputRow.add(idField);
        inputRow.add(searchById);

        searchArea.add(inputRow, BorderLayout.NORTH);

        // Results table
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBackground(CONTENT_BG);

        DefaultTableModel resultModel = new DefaultTableModel(
                new String[]{"ID", "Title", "Author", "Genre", "Available", "Total", "Status"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable resultTable = createStyledTable(resultModel);
        resultsPanel.add(new JScrollPane(resultTable), BorderLayout.CENTER);
        searchArea.add(resultsPanel, BorderLayout.CENTER);

        searchByName.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (keyword.isEmpty()) {
                setStatus("Please enter a search keyword.", false);
                return;
            }
            List<Book> results = library.searchBook(keyword);
            resultModel.setRowCount(0);
            for (Book b : results) {
                String status = b.isAvailable() ? "Available" : "All Borrowed";
                resultModel.addRow(new Object[]{b.getBookId(), b.getTitle(), b.getAuthor(),
                        b.getGenre(), b.getAvailableCopies(), b.getTotalCopies(), status});
            }
            setStatus("Found " + results.size() + " result(s) for '" + keyword + "'.", true);
        });

        searchById.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                Book b = library.searchBook(id);
                resultModel.setRowCount(0);
                String status = b.isAvailable() ? "Available" : "All Borrowed";
                resultModel.addRow(new Object[]{b.getBookId(), b.getTitle(), b.getAuthor(),
                        b.getGenre(), b.getAvailableCopies(), b.getTotalCopies(), status});
                setStatus("Found book: " + b.getTitle(), true);
            } catch (NumberFormatException ex) {
                setStatus("Please enter a valid numeric Book ID.", false);
            } catch (BookNotAvailableException ex) {
                resultModel.setRowCount(0);
                setStatus(ex.getMessage(), false);
            } catch (Exception ex) {
                setStatus("Error: " + ex.getMessage(), false);
            }
        });

        panel.add(searchArea, BorderLayout.CENTER);
        return panel;
    }

    // ==================== ADD BOOK PANEL ====================

    private JPanel createAddBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(createHeading("Add New Book"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                new EmptyBorder(25, 30, 25, 30)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField idField = addFormField(form, gbc, "Book ID:", 0);
        JTextField titleField = addFormField(form, gbc, "Title:", 1);
        JTextField authorField = addFormField(form, gbc, "Author:", 2);
        JTextField genreField = addFormField(form, gbc, "Genre:", 3);
        JTextField copiesField = addFormField(form, gbc, "No. of Copies:", 4);
        copiesField.setText("1");

        JButton addBtn = createActionButton("Add Book", SUCCESS_GREEN);
        gbc.gridx = 1; gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        form.add(addBtn, gbc);

        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String genre = genreField.getText().trim();
                int copies = Integer.parseInt(copiesField.getText().trim());

                if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
                    setStatus("All fields are required.", false);
                    return;
                }
                if (copies < 1) {
                    setStatus("Number of copies must be at least 1.", false);
                    return;
                }

                // Enforce unique book ID
                if (library.bookIdExists(id)) {
                    setStatus("Book ID " + id + " already exists. Please use a unique ID.", false);
                    return;
                }

                library.addBook(new Book(id, title, author, genre, copies));
                setStatus("Book '" + title + "' added successfully with " + copies + " copy/copies!", true);
                idField.setText(""); titleField.setText(""); authorField.setText("");
                genreField.setText(""); copiesField.setText("1");
            } catch (NumberFormatException ex) {
                setStatus("Book ID and Copies must be valid numbers.", false);
            }
        });

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapper.setBackground(CONTENT_BG);
        wrapper.setBorder(new EmptyBorder(15, 0, 0, 0));
        wrapper.add(form);
        panel.add(wrapper, BorderLayout.CENTER);
        return panel;
    }

    // ==================== MEMBERS PANEL ====================

    private JPanel membersTablePanel;

    private JPanel createMembersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(createHeading("All Members"), BorderLayout.NORTH);

        membersTablePanel = new JPanel(new BorderLayout());
        membersTablePanel.setBackground(CONTENT_BG);
        membersTablePanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        refreshMembersTable();

        panel.add(membersTablePanel, BorderLayout.CENTER);
        return panel;
    }

    private void refreshMembersTable() {
        membersTablePanel.removeAll();
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Name", "Phone", "Books Borrowed", "Limit"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Member m : library.getAllMembers()) {
            model.addRow(new Object[]{m.getId(), m.getName(), m.getContact(),
                    m.getBorrowedBooks().size(), m.getMaxBooks()});
        }
        JTable table = createStyledTable(model);
        membersTablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        membersTablePanel.revalidate();
        membersTablePanel.repaint();
    }

    // ==================== ADD MEMBER PANEL ====================

    private JPanel createAddMemberPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(createHeading("Add New Member"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                new EmptyBorder(25, 30, 25, 30)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField idField = addFormField(form, gbc, "Member ID:", 0);
        JTextField nameField = addFormField(form, gbc, "Name:", 1);
        JTextField contactField = addFormField(form, gbc, "Phone Number:", 2);

        JButton addBtn = createActionButton("Add Member", SUCCESS_GREEN);
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        form.add(addBtn, gbc);

        addBtn.addActionListener(e -> {
            try {
                String idText = idField.getText().trim();
                String name = nameField.getText().trim();
                String contact = contactField.getText().trim();

                // Validate ID: must be at least 3 characters long
                if (idText.length() < 3) {
                    setStatus("Member ID must be at least 3 digits long.", false);
                    return;
                }
                int id = Integer.parseInt(idText);

                // Validate name: cannot be empty
                if (name.isEmpty()) {
                    setStatus("Name cannot be empty.", false);
                    return;
                }

                // Validate phone: exactly 10 digits, cannot start with 0
                if (!contact.matches("[1-9][0-9]{9}")) {
                    setStatus("Phone number must be exactly 10 digits and cannot start with 0.", false);
                    return;
                }

                // Enforce unique member ID
                if (library.memberIdExists(id)) {
                    setStatus("Member ID " + id + " already exists. Please use a unique ID.", false);
                    return;
                }

                library.addMember(new Member(id, name, contact));
                setStatus("Member '" + name + "' added successfully!", true);
                idField.setText(""); nameField.setText(""); contactField.setText("");
            } catch (NumberFormatException ex) {
                setStatus("Member ID must be a valid number.", false);
            }
        });

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapper.setBackground(CONTENT_BG);
        wrapper.setBorder(new EmptyBorder(15, 0, 0, 0));
        wrapper.add(form);
        panel.add(wrapper, BorderLayout.CENTER);
        return panel;
    }

    // ==================== ISSUE BOOK PANEL ====================

    private DefaultComboBoxModel<String> issueBookComboModel;
    private JComboBox<String> issueBookCombo;
    private JTextField issueSearchField;
    private JTextField issueMemberIdField;
    private JTextArea issueResultArea;

    private JPanel createIssueBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(createHeading("Issue Book to Member"), BorderLayout.NORTH);

        JPanel mainArea = new JPanel(new BorderLayout(0, 15));
        mainArea.setBackground(CONTENT_BG);
        mainArea.setBorder(new EmptyBorder(15, 0, 0, 0));

        // Form area
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                new EmptyBorder(20, 25, 20, 25)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Search bar for books
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel searchLabel = new JLabel("Search Book:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(searchLabel, gbc);

        gbc.gridx = 1;
        issueSearchField = new JTextField(25);
        issueSearchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        issueSearchField.setPreferredSize(new Dimension(300, 30));
        issueSearchField.setToolTipText("Type to search books by title or author");
        form.add(issueSearchField, gbc);

        // Row 1: Dropdown of available books
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel bookLabel = new JLabel("Select Book:");
        bookLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(bookLabel, gbc);

        gbc.gridx = 1;
        issueBookComboModel = new DefaultComboBoxModel<>();
        issueBookCombo = new JComboBox<>(issueBookComboModel);
        issueBookCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        issueBookCombo.setPreferredSize(new Dimension(300, 30));
        form.add(issueBookCombo, gbc);

        // Row 2: Member ID
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel memberLabel = new JLabel("Member ID:");
        memberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(memberLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.NONE;
        issueMemberIdField = new JTextField(10);
        issueMemberIdField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        issueMemberIdField.setPreferredSize(new Dimension(120, 30));
        form.add(issueMemberIdField, gbc);

        // Row 3: Issue button
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        JButton issueBtn = createActionButton("Issue Book", new Color(41, 128, 185));
        form.add(issueBtn, gbc);

        // Search filter logic: as user types, filter the dropdown
        issueSearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = issueSearchField.getText().trim();
                refreshIssueBookDropdown(keyword);
            }
        });

        // Issue button logic
        issueBtn.addActionListener(e -> {
            try {
                // Get selected book from dropdown
                String selected = (String) issueBookCombo.getSelectedItem();
                if (selected == null || selected.startsWith("--")) {
                    issueResultArea.setText("ERROR: Please select a book from the dropdown.");
                    setStatus("No book selected.", false);
                    return;
                }

                // Parse book ID from selection string: "Title by Author (ID: X) [Y copies]"
                int idStart = selected.lastIndexOf("(ID: ") + 5;
                int idEnd = selected.indexOf(")", idStart);
                int bookId = Integer.parseInt(selected.substring(idStart, idEnd).trim());

                int memberId = Integer.parseInt(issueMemberIdField.getText().trim());

                library.issueBook(bookId, memberId);

                Book book = library.searchBook(bookId);
                Member member = library.getMember(memberId);
                issueResultArea.setText("SUCCESS: Book issued!\n"
                        + "Book: " + book.getTitle() + "\n"
                        + "Copies remaining: " + book.getAvailableCopies() + "/" + book.getTotalCopies() + "\n"
                        + "Issued to: " + member.getName() + "\n"
                        + "Books borrowed by member: " + member.getBorrowedBooks().size() + "/" + member.getMaxBooks());
                setStatus("Book issued successfully!", true);
                issueMemberIdField.setText("");
                issueSearchField.setText("");
                refreshIssueBookDropdown("");
            } catch (NumberFormatException ex) {
                issueResultArea.setText("ERROR: Please enter a valid Member ID.");
                setStatus("Invalid input.", false);
            } catch (BookNotAvailableException | InvalidMemberException | InvalidBorrowException ex) {
                issueResultArea.setText("ERROR: " + ex.getMessage());
                setStatus(ex.getMessage(), false);
            } catch (Exception ex) {
                issueResultArea.setText("ERROR: " + ex.getMessage());
                setStatus("Error: " + ex.getMessage(), false);
            }
        });

        // Result area
        issueResultArea = new JTextArea(5, 40);
        issueResultArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        issueResultArea.setEditable(false);
        issueResultArea.setBackground(new Color(245, 245, 245));
        issueResultArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel formWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formWrapper.setBackground(CONTENT_BG);
        formWrapper.add(form);
        mainArea.add(formWrapper, BorderLayout.NORTH);
        mainArea.add(new JScrollPane(issueResultArea), BorderLayout.CENTER);

        panel.add(mainArea, BorderLayout.CENTER);

        // Initial population of dropdown
        refreshIssueBookDropdown("");

        return panel;
    }

    /**
     * Refresh the issue book dropdown with available books, filtered by keyword.
     */
    private void refreshIssueBookDropdown(String keyword) {
        issueBookComboModel.removeAllElements();
        List<Book> available = library.getAvailableBooks();
        String lowerKeyword = keyword.toLowerCase();

        int count = 0;
        for (Book b : available) {
            if (keyword.isEmpty()
                    || b.getTitle().toLowerCase().contains(lowerKeyword)
                    || b.getAuthor().toLowerCase().contains(lowerKeyword)) {
                String entry = b.getTitle() + " by " + b.getAuthor()
                        + " (ID: " + b.getBookId() + ") [" + b.getAvailableCopies() + " copies]";
                issueBookComboModel.addElement(entry);
                count++;
            }
        }

        if (count == 0) {
            issueBookComboModel.addElement("-- No available books found --");
        }
    }

    // ==================== RETURN BOOK PANEL ====================

    private JPanel createReturnBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CONTENT_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(createHeading("Return Book"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                new EmptyBorder(25, 30, 25, 30)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField bookIdField = addFormField(form, gbc, "Book ID:", 0);
        JTextField memberIdField = addFormField(form, gbc, "Member ID:", 1);

        JButton returnBtn = createActionButton("Return Book", new Color(192, 57, 43));
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        form.add(returnBtn, gbc);

        JTextArea resultArea = new JTextArea(5, 40);
        resultArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(245, 245, 245));
        resultArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        returnBtn.addActionListener(e -> {
            try {
                int bookId = Integer.parseInt(bookIdField.getText().trim());
                int memberId = Integer.parseInt(memberIdField.getText().trim());

                library.returnBook(bookId, memberId);

                Book book = library.searchBook(bookId);
                resultArea.setText("SUCCESS: Book returned!\n"
                        + "Book: " + book.getTitle() + "\n"
                        + "Copies now available: " + book.getAvailableCopies() + "/" + book.getTotalCopies() + "\n"
                        + "Returned by Member ID: " + memberId);
                setStatus("Book returned successfully!", true);
                bookIdField.setText(""); memberIdField.setText("");
            } catch (NumberFormatException ex) {
                resultArea.setText("ERROR: Please enter valid numeric IDs.");
                setStatus("Invalid input.", false);
            } catch (BookNotAvailableException | InvalidMemberException | InvalidBorrowException ex) {
                resultArea.setText("ERROR: " + ex.getMessage());
                setStatus(ex.getMessage(), false);
            } catch (Exception ex) {
                resultArea.setText("ERROR: " + ex.getMessage());
                setStatus("Error: " + ex.getMessage(), false);
            }
        });

        JPanel wrapper = new JPanel(new BorderLayout(0, 15));
        wrapper.setBackground(CONTENT_BG);
        wrapper.setBorder(new EmptyBorder(15, 0, 0, 0));

        JPanel formWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formWrapper.setBackground(CONTENT_BG);
        formWrapper.add(form);
        wrapper.add(formWrapper, BorderLayout.NORTH);
        wrapper.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        panel.add(wrapper, BorderLayout.CENTER);
        return panel;
    }

    // ==================== UTILITY METHODS ====================

    private JLabel createHeading(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(TEXT_DARK);
        label.setBorder(new EmptyBorder(0, 0, 5, 0));
        return label;
    }

    private JButton createActionButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width + 20, 32));
        return btn;
    }

    private JTextField addFormField(JPanel form, GridBagConstraints gbc, String label, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(lbl, gbc);

        gbc.gridx = 1;
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setPreferredSize(new Dimension(250, 30));
        form.add(field, gbc);
        return field;
    }

    /**
     * Creates a styled JTable with a CUSTOM header renderer that guarantees
     * header text is always visible (white text on blue background),
     * regardless of the system Look and Feel.
     */
    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(new Color(41, 128, 185));
        table.setSelectionForeground(WHITE);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));

        // Custom header renderer to override system LAF and ensure visible headers
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(0, 34));
        header.setDefaultRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value != null ? value.toString() : "");
                label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                label.setForeground(TABLE_HEADER_FG);
                label.setBackground(TABLE_HEADER_BG);
                label.setOpaque(true);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createCompoundBorder(
                        new MatteBorder(0, 0, 1, 1, new Color(30, 100, 160)),
                        new EmptyBorder(4, 6, 4, 6)));
                return label;
            }
        });

        // Center the first column (ID)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        if (model.getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        }

        return table;
    }

    private void setStatus(String message, boolean success) {
        statusLabel.setText("  " + message);
        statusLabel.setBackground(success ? SUCCESS_GREEN : ERROR_RED);
    }

    private void showPanel(String name) {
        // Rebuild dashboard each time to refresh stats
        if (name.equals("dashboard")) {
            contentPanel.remove(0); // remove old dashboard
            contentPanel.add(createDashboardPanel(), "dashboard", 0);
        }
        cardLayout.show(contentPanel, name);
    }

    private void refreshAndShow(String name) {
        if (name.equals("allBooks")) {
            refreshBooksTable(library.getAllBooks());
        } else if (name.equals("members")) {
            refreshMembersTable();
        } else if (name.equals("issueBook")) {
            // Refresh the book dropdown when opening Issue Book panel
            if (issueSearchField != null) {
                issueSearchField.setText("");
            }
            refreshIssueBookDropdown("");
        }
        cardLayout.show(contentPanel, name);
    }
}
