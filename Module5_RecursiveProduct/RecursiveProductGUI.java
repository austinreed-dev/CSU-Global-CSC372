package Module5_RecursiveProduct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecursiveProductGUI extends JFrame implements ActionListener {
    // GUI Components
    private JPanel panel;
    private JTextField[] inputFields;
    private JLabel resultLabel;
    private JButton calculateButton;
    private JButton clearButton;

    public RecursiveProductGUI() {
        // Frame Setup
        setTitle("Recursive Product Calculator");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel Layout (8 rows, 1 column with gaps)
        panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Initialize 5 text fields for the numbers
        inputFields = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            inputFields[i] = new JTextField();
            inputFields[i].setBorder(BorderFactory.createTitledBorder("Number " + (i + 1) + ":"));
            panel.add(inputFields[i]);
        }

        // Action Buttons
        calculateButton = new JButton("Calculate Product (Recursion)");
        clearButton = new JButton("Clear Fields");

        calculateButton.addActionListener(this);
        clearButton.addActionListener(this);

        // Result Label
        resultLabel = new JLabel("Product: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Add remaining components to panel
        panel.add(calculateButton);
        panel.add(clearButton);
        panel.add(resultLabel);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            try {
                double[] numbers = new double[5];
                
                // Gather inputs from the text fields
                for (int i = 0; i < 5; i++) {
                    String text = inputFields[i].getText().trim();
                    if (text.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please fill in all 5 numbers.");
                        return;
                    }
                    numbers[i] = Double.parseDouble(text);
                }

                // Call the recursive method starting at index 0
                double product = calculateProduct(numbers, 0);
                
                // Display the result
                resultLabel.setText("Product: " + String.format("%.2f", product));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter numbers only.");
            }
        } else if (e.getSource() == clearButton) {
            // Clear all text fields and reset label
            for (int i = 0; i < 5; i++) {
                inputFields[i].setText("");
            }
            resultLabel.setText("Product: ");
            inputFields[0].requestFocusInWindow();
        }
    }

    /**
     * The core recursive algorithm required by the prompt
     */
    public static double calculateProduct(double[] arr, int index) {
        // Base case: out of bounds, return 1 to preserve product balance
        if (index == arr.length) {
            return 1;
        }
        // Recursive step
        return arr[index] * calculateProduct(arr, index + 1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RecursiveProductGUI().setVisible(true);
        });
    }
}