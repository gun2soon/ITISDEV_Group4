import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuView {

    private JFrame frame;
    private JButton POSButton;
    private JButton InventoryButton;
    private JButton exitButton;

    
    @SuppressWarnings("unlikely-arg-type")
    public MenuView () {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {}


        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("TextArea.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("Table.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("Table.rowHeight", 30);
        UIManager.put("TableHeader.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("ComboBox.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("TitledBorder.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("CheckBox.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("FormattedTextField.font", new Font("Arial", Font.PLAIN, 16));

        
        if (UIManager.getLookAndFeel().equals(UIManager.getSystemLookAndFeelClassName())) {
            frame.setBackground(UIManager.getColor("Panel.background"));
        }
        
        frame = new JFrame("Inventory Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 4));

        POSButton = new JButton("POS");
        InventoryButton = new JButton("Inventory");
        exitButton = new JButton ("Exit");

        JLabel headerLabel = new JLabel("Ready Coffee System", SwingConstants.CENTER);

        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        POSButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        InventoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(headerLabel);
        buttonPanel.add(POSButton);
        buttonPanel.add(InventoryButton);
        buttonPanel.add(exitButton);

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null); // Center the frame

    }

    public void setActionListener(ActionListener listener) {
        POSButton.addActionListener(listener);
        InventoryButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }

    public JFrame getFrame() {
        return frame;
    }

    public JButton getPosButton() {
        return POSButton;
    }

    public JButton getInventoryButton() {
        return InventoryButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }
}

