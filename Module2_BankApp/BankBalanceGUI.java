import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BankBalanceGUI extends JFrame implements ActionListener {
    // GUI Components
    private JPanel panel;
    private JTextField amountField;
    private JLabel balanceLabel;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton viewBalanceButton;
    private JButton exitButton;

    // Logic Variable
    private double balance = 0.0;

    public BankBalanceGUI() {
        // Set up the Frame
        setTitle("CSU-Global Bank Interface");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize Panel and Layout
        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Initialize Components
        balanceLabel = new JLabel("Current Balance: $0.00", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));

        amountField = new JTextField();
        amountField.setBorder(BorderFactory.createTitledBorder("Enter Amount:"));

        depositButton = new JButton("Deposit Funds");
        withdrawButton = new JButton("Withdraw Funds");
        viewBalanceButton = new JButton("Refresh Balance View");
        exitButton = new JButton("Exit Program");

        // Add Listeners
        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);
        viewBalanceButton.addActionListener(this);
        exitButton.addActionListener(this);

        // Add to Panel
        panel.add(balanceLabel);
        panel.add(amountField);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(viewBalanceButton);
        panel.add(exitButton);

        // Add Panel to Frame
        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double amount = 0;
            if (!amountField.getText().isEmpty()) {
                amount = Double.parseDouble(amountField.getText());
            }

            if (e.getSource() == depositButton) {
                if (amount > 0) {
                    balance += amount;
                    updateBalanceDisplay();
                    JOptionPane.showMessageDialog(this, "Successfully deposited $" + amount);
                }
            } else if (e.getSource() == withdrawButton) {
                if (amount > 0 && amount <= balance) {
                    balance -= amount;
                    updateBalanceDisplay();
                    JOptionPane.showMessageDialog(this, "Successfully withdrew $" + amount);
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient funds or invalid amount!");
                }
            } else if (e.getSource() == viewBalanceButton) {
                updateBalanceDisplay();
                JOptionPane.showMessageDialog(this, "Your current balance is: $" + String.format("%.2f", balance));
            } else if (e.getSource() == exitButton) {
                JOptionPane.showMessageDialog(this, "Final Balance: $" + String.format("%.2f", balance) + "\nThank you for using the Bank App!");
                System.exit(0);
            }
            
            amountField.setText(""); // Clear field after action
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount.");
        }
    }

    private void updateBalanceDisplay() {
        balanceLabel.setText("Current Balance: $" + String.format("%.2f", balance));
    }

    public static void main(String[] args) {
        // Run GUI in the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> {
            new BankBalanceGUI().setVisible(true);
        });
    }
}