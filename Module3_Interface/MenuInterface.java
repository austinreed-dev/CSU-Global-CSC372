import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.swing.*;

public class MenuInterface extends JFrame {
    private JTextArea textBox;
    private Color randomGreen;
    private final String PLACEHOLDER = "Select an option from the 'Options' menu above...";

    public MenuInterface() {
        setTitle("Menu Interface Application");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Generate the random green hue once
        Random rand = new Random();
        randomGreen = new Color(rand.nextInt(100), 160 + rand.nextInt(95), rand.nextInt(100));

        // Text Box Setup
        textBox = new JTextArea(PLACEHOLDER);
        textBox.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textBox.setBorder(BorderFactory.createTitledBorder("Log Output"));

        textBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textBox.getText().equals(PLACEHOLDER)) textBox.setText("");
            }
        });

        add(new JScrollPane(textBox), BorderLayout.CENTER);

        // Menu Setup
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");

        // Item 1: Date/Time (Inserts at top)
        JMenuItem item1 = new JMenuItem("1. Show Date/Time");
        item1.addActionListener(e -> {
            if (textBox.getText().equals(PLACEHOLDER)) textBox.setText("");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            textBox.insert("Timestamp: " + dtf.format(LocalDateTime.now()) + "\n", 0);
        });

        // Item 2: Write to log.txt
JMenuItem item2 = new JMenuItem("2. Save to log.txt");
item2.addActionListener(e -> {
    // try-with-resources ensures the file is CLOSED and SAVED properly
    try (FileWriter writer = new FileWriter("log.txt", true)) { 
        String content = textBox.getText();
        
        if (content.equals(PLACEHOLDER) || content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nothing to save!");
            return;
        }

        writer.write(content);
        writer.write("\n--- End of Entry ---\n"); // Adds a separator
        
        // Manual flush just to be 100% safe
        writer.flush(); 
        
        JOptionPane.showMessageDialog(this, "Success! Content pushed to log.txt");
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
});

        // Item 3: Change Color
        JMenuItem item3 = new JMenuItem("3. Change Background");
        item3.addActionListener(e -> textBox.setBackground(randomGreen));

        // Item 4: Exit
        JMenuItem item4 = new JMenuItem("4. Exit");
        item4.addActionListener(e -> System.exit(0));

        optionsMenu.add(item1); optionsMenu.add(item2); 
        optionsMenu.add(item3); optionsMenu.add(item4);
        menuBar.add(optionsMenu);
        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        // This will print the "Ghost Folder" path to your VS Code Terminal immediately
        System.out.println("Working Directory: " + System.getProperty("user.dir"));
        
        SwingUtilities.invokeLater(() -> new MenuInterface().setVisible(true));
    }
}