import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuView {

    private JFrame frame;
    private JButton POSButton;
    private JButton InventoryButton;
    private JButton exitButton;
    private Image backgroundImage;
    private Image logoImage;

    public MenuView() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("TextArea.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("Table.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("Table.rowHeight", 30);
        UIManager.put("TableHeader.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("ComboBox.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("TitledBorder.font", new Font("Arial", Font.PLAIN, 16));

        frame = new JFrame("Inventory Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        // Load the background and logo images
        backgroundImage = new ImageIcon("resources/readyCoffeeBG.png").getImage();
        logoImage = new ImageIcon("resources/logo.png").getImage(); // Replace with the path to your logo

        // Resize the logo image
        int logoWidth = 300; // Set desired width
        int logoHeight = 100; // Set desired height
        Image resizedLogoImage = logoImage.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);

        // Create the header panel with the background image
        HeaderPanel headerPanel = new HeaderPanel();
        headerPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 10, 20));
        buttonPanel.setOpaque(false); // Make the panel transparent
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 4));

        POSButton = new JButton("POS");
        InventoryButton = new JButton("Inventory");
        exitButton = new JButton("Exit");

        JLabel headerLabel = new JLabel(new ImageIcon(resizedLogoImage), SwingConstants.CENTER);
        headerLabel.setOpaque(false); // Make the label transparent
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        POSButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        InventoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(headerLabel);
        buttonPanel.add(POSButton);
        buttonPanel.add(InventoryButton);
        buttonPanel.add(exitButton);

        headerPanel.add(buttonPanel, BorderLayout.CENTER);
        frame.setContentPane(headerPanel);
        frame.setLocationRelativeTo(null); // Center the frame

        frame.setVisible(true);
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

    // Custom JPanel class to draw the background image only in the header area
    class HeaderPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the background image in the header area
            if (backgroundImage != null) {
                int headerHeight = 135; // Height of the header area
                g.drawImage(backgroundImage, 0, 0, getWidth(), headerHeight, this);
            }
        }

    }
}
