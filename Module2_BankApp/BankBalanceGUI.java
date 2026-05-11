import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BankBalanceGUI extends JFrame implements ActionListener {
    private JPanel panel;
    private JTextField amountField;
    private JLabel balanceLabel;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton viewBalanceButton;
    private JButton exitButton;

    private double balance = 0.0;

    public BankBalanceGUI() {
        setTitle("CSU-Global Bank Interface");
        // Increased height from 250 to 350 to prevent squishing
        setSize(400, 350); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        // 6 rows, 1 column, with 10px vertical/horizontal gaps
        panel.setLayout(new GridLayout(6, 1, 10, 10)); 
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        balanceLabel = new JLabel("Current Balance: $0.00", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Setting a preferred size for the text field helps VS Code's layout engine
        amountField = new JTextField();
        amountField.setPreferredSize(new Dimension(200, 30));
        amountField.setBorder(BorderFactory.createTitledBorder("Enter Amount:"));

        depositButton = new JButton("Deposit Funds");
        withdrawButton = new JButton("Withdraw Funds");
        viewBalanceButton = new JButton("View Balance Popup");
        exitButton = new JButton("Exit");

        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);
        viewBalanceButton.addActionListener(this);
        exitButton.addActionListener(this);

        panel.add(balanceLabel);
        panel.add(amountField);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(viewBalanceButton);
        panel.add(exitButton);

        add(panel);
        
        // This ensures you can type immediately without clicking first
        amountField.requestFocusInWindow(); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String inputText = amountField.getText().trim();
            double amount = 0;
            
            if (!inputText.isEmpty()) {
                amount = Double.parseDouble(inputText);
            }

            if (e.getSource() == depositButton) {
                if (amount > 0) {
                    balance += amount;
                    updateBalanceDisplay();
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a positive amount to deposit.");
                }
            } else if (e.getSource() == withdrawButton) {
                if (amount > 0 && amount <= balance) {
                    balance -= amount;
                    updateBalanceDisplay();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid amount or Insufficient Funds!");
                }
            } else if (e.getSource() == viewBalanceButton) {
                JOptionPane.showMessageDialog(this, "Current Balance: $" + String.format("%.2f", balance));
            } else if (e.getSource() == exitButton) {
                JOptionPane.showMessageDialog(this, "Final Balance: $" + String.format("%.2f", balance));
                System.exit(0);
            }
            
            amountField.setText("");
            amountField.requestFocusInWindow(); // Put cursor back after clicking button

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Please enter numbers only (e.g. 10.50)");
        }
    }

    private void updateBalanceDisplay() {
        balanceLabel.setText("Current Balance: $" + String.format("%.2f", balance));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BankBalanceGUI().setVisible(true);
        });
    }
}