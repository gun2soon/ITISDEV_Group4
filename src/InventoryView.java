import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InventoryView {
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton updateButton;
    private JButton exitButton;

    public InventoryView() {
        frame = new JFrame("Inventory Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Inventory", SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(frame.getWidth(), 40));
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(headerLabel, BorderLayout.NORTH);

        String[] columns = {"Items", "Qty/units", "SKU", "Cost"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        updateButton = new JButton("Update Inventory");
        exitButton = new JButton("Exit Inventory");

        buttonPanel.add(updateButton);
        buttonPanel.add(exitButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null); // Center the frame
    }

    

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public JFrame getFrame() {
        return frame;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTable() {
        return table;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }


}
