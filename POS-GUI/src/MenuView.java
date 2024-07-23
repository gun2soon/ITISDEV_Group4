import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuView {

    private JFrame frame;
    private JButton POSButton;
    private JButton InventoryButton;
    private JButton exitButton;

    
    public MenuView () {
        frame = new JFrame("Inventory Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 200);
        frame.setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Ready Coffee System", SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(frame.getWidth(), 40));
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(headerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        //buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        POSButton = new JButton("POS");
        InventoryButton = new JButton("Inventory");
        exitButton = new JButton ("Exit");

        POSButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        InventoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

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

