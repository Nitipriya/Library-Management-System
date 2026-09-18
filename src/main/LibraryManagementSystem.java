package main;

import service.Library;
import ui.LibraryGUI;
import model.Librarian;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Interactive main entry point for the Library Management System.
 * Launches the Swing GUI with sample data pre-loaded.
 * Use this for individual feature demonstrations.
 */
public class LibraryManagementSystem {

    public static void main(String[] args) {
        // Set system look and feel for a cleaner appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fall back to default look and feel
        }

        // Create library and load sample data
        Library library = new Library();
        library.loadSampleData();

        // Launch GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LibraryGUI gui = new LibraryGUI(library);
            gui.setVisible(true);
        });
    }
}
