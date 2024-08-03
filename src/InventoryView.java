import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class InventoryView {
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton updateButton;
    private JButton exitButton;

    @SuppressWarnings("unlikely-arg-type")
    public InventoryView() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {}


        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("Table.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("Table.rowHeight", 30);
        UIManager.put("TableHeader.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("ComboBox.font", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("TitledBorder.font", new Font("Arial", Font.PLAIN, 16));
        
        frame = new JFrame("Inventory Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        if (UIManager.getLookAndFeel().equals(UIManager.getSystemLookAndFeelClassName())) {
            frame.setBackground(UIManager.getColor("Panel.background"));
        }
        
        
       

        String[] columns = {"Items", "Qty/units", "SKU", "Cost"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(
            BorderFactory.createCompoundBorder(
                
            BorderFactory.createEmptyBorder(5, 5, 0, 5), 
                BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY),
                        "  Inventory  ", 
                        TitledBorder.CENTER, 
                        TitledBorder.TOP),
                    BorderFactory.createEmptyBorder(10, 5, 0, 0))
                ));
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        updateButton = new JButton("Update Inventory");
        exitButton = new JButton("Exit Inventory");

        buttonPanel.add(updateButton);
        buttonPanel.add(exitButton);

        frame.setLocationRelativeTo(null);
        frame.add(buttonPanel, BorderLayout.SOUTH);
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
