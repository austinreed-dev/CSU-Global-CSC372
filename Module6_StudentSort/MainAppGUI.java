import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// --- 1. STUDENT BLUEPRINT ---
class Student {
    private int rollno;
    private String name;
    private String address;

    public Student(int rollno, String name, String address) {
        this.rollno = rollno;
        this.name = name;
        this.address = address;
    }

    public int getRollno() { return rollno; }
    public String getName() { return name; }
    public String getAddress() { return address; }
}

// --- 2. COMPARATORS ---
class NameComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.getName().compareTo(s2.getName());
    }
}

class RollNoComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return Integer.compare(s1.getRollno(), s2.getRollno());
    }
}

// --- 3. CUSTOM SELECTION SORT ---
class SelectionSorter {
    public static void selectionSort(ArrayList<Student> list, Comparator<Student> comparator) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (comparator.compare(list.get(j), list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Student temp = list.get(i);
                list.set(i, list.get(minIndex));
                list.set(minIndex, temp);
            }
        }
    }
}

// --- 4. MAIN GUI APPLICATION (The only public class) ---
public class MainAppGUI extends JFrame {
    private ArrayList<Student> students;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JButton sortNameButton;
    private JButton sortRollButton;
    private JButton resetLinesButton;

    public MainAppGUI() {
        // Frame Configurations
        setTitle("Student Selection Sorter");
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize and Populate Data
        initializeStudentData();

        // UI Layout Elements
        setLayout(new BorderLayout(10, 10));

        // Create Table to display students neatly
        String[] columns = {"Roll Number", "Name", "Address"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 13));
        studentTable.setRowHeight(22);
        
        // Load initial records into the UI view
        refreshTableDisplay();

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Student Database Records"));
        add(scrollPane, BorderLayout.CENTER);

        // Control Panel for Action Buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        sortNameButton = new JButton("Sort by Name");
        sortRollButton = new JButton("Sort by Roll No");
        resetLinesButton = new JButton("Restore Original Order");

        // Attach Action Logic
        sortNameButton.addActionListener(e -> {
            SelectionSorter.selectionSort(students, new NameComparator());
            refreshTableDisplay();
            JOptionPane.showMessageDialog(this, "Sorted alphabetically by Student Name using Custom Selection Sort!");
        });

        sortRollButton.addActionListener(e -> {
            SelectionSorter.selectionSort(students, new RollNoComparator());
            refreshTableDisplay();
            JOptionPane.showMessageDialog(this, "Sorted numerically by Roll Number using Custom Selection Sort!");
        });

        resetLinesButton.addActionListener(e -> {
            initializeStudentData();
            refreshTableDisplay();
        });

        controlPanel.add(sortNameButton);
        controlPanel.add(sortRollButton);
        controlPanel.add(resetLinesButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void initializeStudentData() {
        students = new ArrayList<>();
        students.add(new Student(105, "Zac", "Austin, TX"));
        students.add(new Student(101, "Alice", "Fort Collins, CO"));
        students.add(new Student(110, "Oliver", "Denver, CO"));
        students.add(new Student(103, "Charlie", "Dallas, TX"));
        students.add(new Student(102, "Bob", "Houston, TX"));
        students.add(new Student(107, "Fiona", "Kilgore, TX"));
        students.add(new Student(104, "David", "Lindale, TX"));
        students.add(new Student(109, "Natalie", "Colorado Springs, CO"));
        students.add(new Student(106, "Evan", "Greeley, CO"));
        students.add(new Student(108, "Grace", "Pueblo, CO"));
    }

    private void refreshTableDisplay() {
        tableModel.setRowCount(0); // Clear current UI rows
        for (Student s : students) {
            Object[] row = {s.getRollno(), s.getName(), s.getAddress()};
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainAppGUI().setVisible(true));
    }
}