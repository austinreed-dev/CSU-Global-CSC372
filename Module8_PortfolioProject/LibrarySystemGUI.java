package Module8_PortfolioProject;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// ==========================================
// 1. BOOK BLUEPRINT CLASS
// ==========================================
class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private int numPages;

    // Default Constructor
    public Book() {
        this.id = 0;
        this.title = "";
        this.author = "";
        this.isbn = "";
        this.numPages = 0;
    }

    // Parameterized Constructor (5 parameters required by prompt)
    public Book(int id, String title, String author, String isbn, int numPages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.numPages = numPages;
    }

    // Public Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getNumPages() { return numPages; }
    public void setNumPages(int numPages) { this.numPages = numPages; }

    // Required Member Method to format specific book metrics
    public String printBookInfo() {
        return String.format("ID: %d | Title: \"%s\" | Author: %s | ISBN: %s | Pages: %d", 
                id, title, author, isbn, numPages);
    }
}

// ==========================================
// 2. INVENTORY CONTROL CLASS
// ==========================================
class Inventory {
    // Handling databases with prompt specified ArrayList collections
    private ArrayList<Book> mainInventory;
    private ArrayList<Book> lendingInventory;

    public Inventory() {
        mainInventory = new ArrayList<>();
        lendingInventory = new ArrayList<>();
        seedInitialBooks(); // Helper populate method on startup
    }

    private void seedInitialBooks() {
        mainInventory.add(new Book(101, "The Java Tutorial", "Raymond Chia", "978-0132575669", 832));
        mainInventory.add(new Book(102, "Clean Code", "Robert C. Martin", "978-0132350884", 464));
        mainInventory.add(new Book(103, "Introduction to Algorithms", "Thomas H. Cormen", "978-0262046304", 1312));
    }

    public void addBook(Book book) {
        mainInventory.add(book);
    }

    public boolean borrowBook(int id) {
        for (int i = 0; i < mainInventory.size(); i++) {
            if (mainInventory.get(i).getId() == id) {
                Book bookToBorrow = mainInventory.remove(i);
                lendingInventory.add(bookToBorrow);
                return true;
            }
        }
        return false;
    }

    public boolean returnBook(int id) {
        for (int i = 0; i < lendingInventory.size(); i++) {
            if (lendingInventory.get(i).getId() == id) {
                Book bookToReturn = lendingInventory.remove(i);
                mainInventory.add(bookToReturn);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Book> searchByTitle(String title) {
        ArrayList<Book> matches = new ArrayList<>();
        String searchStr = title.toLowerCase().trim();
        for (Book b : mainInventory) {
            if (b.getTitle().toLowerCase().contains(searchStr)) {
                matches.add(b);
            }
        }
        return matches;
    }

    public ArrayList<Book> getMainInventory() { return mainInventory; }
    public ArrayList<Book> getLendingInventory() { return lendingInventory; }
    public int getMainInventoryCount() { return mainInventory.size(); }
}

// ==========================================
// 3. MAIN INTERACTIVE APPLICATION ENGINE
// ==========================================
public class LibrarySystemGUI extends JFrame {
    private Inventory inventory;
    private JTable mainInvTable;
    private JTable lendingTable;
    private DefaultTableModel mainTableModel;
    private DefaultTableModel lendingTableModel;
    private JTextArea consoleOutputArea;

    public LibrarySystemGUI() {
        inventory = new Inventory();

        // Window Initial Configurations
        setTitle("CSU-Global CSC372 Library Portfolio Project");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Visual Top Header Accent
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 73, 107));
        JLabel headerLabel = new JLabel("Library Automation and Ledger System");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Center Panel splits Main Inventory from Active Lending logs
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"Book ID", "Title", "Author", "ISBN", "Pages"};
        
        mainTableModel = new DefaultTableModel(columns, 0);
        mainInvTable = new JTable(mainTableModel);
        JScrollPane mainScroll = new JScrollPane(mainInvTable);
        mainScroll.setBorder(BorderFactory.createTitledBorder("1. Main Inventory Records (Available)"));
        centerPanel.add(mainScroll);

        lendingTableModel = new DefaultTableModel(columns, 0);
        lendingTable = new JTable(lendingTableModel);
        JScrollPane lendingScroll = new JScrollPane(lendingTable);
        lendingScroll.setBorder(BorderFactory.createTitledBorder("2. Active Lending Database (Borrowed Out)"));
        centerPanel.add(lendingScroll);

        add(centerPanel, BorderLayout.CENTER);

        // Right Operational Sidebar Layout
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setPreferredSize(new Dimension(320, 0));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

        consoleOutputArea = new JTextArea();
        consoleOutputArea.setEditable(false);
        consoleOutputArea.setBackground(new Color(242, 244, 247));
        consoleOutputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane consoleScroll = new JScrollPane(consoleOutputArea);
        consoleScroll.setBorder(BorderFactory.createTitledBorder("System Action Log"));
        rightPanel.add(consoleScroll, BorderLayout.CENTER);

        // Menu control suite matching layout menu from prompt precisely
        JPanel menuPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        menuPanel.setBorder(BorderFactory.createTitledBorder("Control Dashboard"));

        JButton btnAdd = new JButton("1. Add Book");
        JButton btnBorrow = new JButton("2. Borrow Book");
        JButton btnReturn = new JButton("3. Return Book");
        JButton btnSearch = new JButton("4. Search by Title");
        JButton btnPrintAll = new JButton("5. Print All Books");
        JButton btnExit = new JButton("6. Exit");

        menuPanel.add(btnAdd);
        menuPanel.add(btnBorrow);
        menuPanel.add(btnReturn);
        menuPanel.add(btnSearch);
        menuPanel.add(btnPrintAll);
        menuPanel.add(btnExit);
        rightPanel.add(menuPanel, BorderLayout.SOUTH);

        add(rightPanel, BorderLayout.EAST);

        // Map Event Triggers
        btnAdd.addActionListener(e -> executeAddBookOption());
        btnBorrow.addActionListener(e -> executeBorrowOption());
        btnReturn.addActionListener(e -> executeReturnOption());
        btnSearch.addActionListener(e -> executeSearchOption());
        btnPrintAll.addActionListener(e -> executePrintAllOption());
        btnExit.addActionListener(e -> {
            logToConsole("Exiting application ledger. State killed.");
            JOptionPane.showMessageDialog(this, "Exiting the program. Goodbye!", "Exit", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        });

        refreshTableViews();
        logToConsole("Library Systems operational. Seed records parsed.");
    }

    private void logToConsole(String message) {
        consoleOutputArea.append(">> " + message + "\n");
    }

    private void refreshTableViews() {
        mainTableModel.setRowCount(0);
        for (Book b : inventory.getMainInventory()) {
            mainTableModel.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getNumPages()});
        }

        lendingTableModel.setRowCount(0);
        for (Book b : inventory.getLendingInventory()) {
            lendingTableModel.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getNumPages()});
        }
    }

    // ==========================================================
    // MANDATORY PROMPT CRITERIA: EXCEPTION HANDLING & TRANSACTIONS
    // ==========================================================
    private void executeAddBookOption() {
        // Enforces full try...catch approach for user error protection
        try {
            String idStr = JOptionPane.showInputDialog(this, "Enter Unique Book ID:");
            if (idStr == null) return; 
            int id = Integer.parseInt(idStr.trim());

            // Check uniqueness constraints to prevent catalog breaks
            for (Book b : inventory.getMainInventory()) {
                if (b.getId() == id) {
                    JOptionPane.showMessageDialog(this, "Duplicate Error: ID is already registered!", "Database Collision", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            for (Book b : inventory.getLendingInventory()) {
                if (b.getId() == id) {
                    JOptionPane.showMessageDialog(this, "Duplicate Error: ID belongs to a borrowed resource!", "Database Collision", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String title = JOptionPane.showInputDialog(this, "Enter Title Header:");
            if (title == null || title.trim().isEmpty()) return;

            String author = JOptionPane.showInputDialog(this, "Enter Author Name:");
            if (author == null || author.trim().isEmpty()) return;

            String isbn = JOptionPane.showInputDialog(this, "Enter ISBN Number:");
            if (isbn == null || isbn.trim().isEmpty()) return;

            String pagesStr = JOptionPane.showInputDialog(this, "Enter Sheet Page Count:");
            if (pagesStr == null) return;
            int pages = Integer.parseInt(pagesStr.trim());

            // Build object profile using parameterized constructor
            Book newBook = new Book(id, title.trim(), author.trim(), isbn.trim(), pages);
            inventory.addBook(newBook);
            refreshTableViews();
            
            logToConsole("Book added to the library: " + title);
            JOptionPane.showMessageDialog(this, "Book added to the library.", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            logToConsole("Add failed: Non-numeric payload caught.");
            JOptionPane.showMessageDialog(this, "Input Validation Error! IDs and page lengths must be integers.", "Format Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void executeBorrowOption() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Enter unique ID of book to borrow:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr.trim());

            boolean success = inventory.borrowBook(id);
            if (success) {
                refreshTableViews();
                logToConsole("Book ID " + id + " successfully lent.");
                JOptionPane.showMessageDialog(this, "Book successfully borrowed.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                logToConsole("Transaction Failed: ID " + id + " unavailable.");
                JOptionPane.showMessageDialog(this, "Error: Book not found or already borrowed.", "Transaction Denied", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Numeric entries only.", "Format Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void executeReturnOption() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Enter unique ID of book to return:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr.trim());

            boolean success = inventory.returnBook(id);
            if (success) {
                refreshTableViews();
                logToConsole("Book ID " + id + " returned to main storage.");
                JOptionPane.showMessageDialog(this, "Book successfully returned.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                logToConsole("Transaction Failed: Return ID " + id + " out of context.");
                JOptionPane.showMessageDialog(this, "Error: This book is not in the lending list or no items are lent out.", "Return Fault", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Numeric entries only.", "Format Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void executeSearchOption() {
        String query = JOptionPane.showInputDialog(this, "Enter title key to search:");
        if (query == null || query.trim().isEmpty()) return;

        ArrayList<Book> matches = inventory.searchByTitle(query);
        if (matches.isEmpty()) {
            logToConsole("Search completed: No matching book found.");
            JOptionPane.showMessageDialog(this, "No matching book found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder results = new StringBuilder("Matching Query Results:\n\n");
            for (Book b : matches) {
                results.append(b.printBookInfo()).append("\n");
            }
            logToConsole("Search located " + matches.size() + " matches.");
            JOptionPane.showMessageDialog(this, results.toString(), "Search Grid", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void executePrintAllOption() {
        ArrayList<Book> activeInventory = inventory.getMainInventory();
        logToConsole("--- Running System Dump (printBookInfo) ---");
        
        if (activeInventory.isEmpty()) {
            logToConsole("Database Empty.");
            JOptionPane.showMessageDialog(this, "The main inventory is currently empty.", "Status Report", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder infoDump = new StringBuilder("Available Library Assets:\n\n");
        for (Book b : activeInventory) {
            String specLine = b.printBookInfo();
            infoDump.append(specLine).append("\n");
            logToConsole(specLine); // Outputs to the sidebar terminal interface log
        }
        
        JOptionPane.showMessageDialog(this, infoDump.toString(), "Global Print All Printout", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarySystemGUI().setVisible(true));
    }
}