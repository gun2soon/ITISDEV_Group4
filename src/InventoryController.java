import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InventoryController {
    private InventoryManagement imsmodel;
    private InventoryView imsview;
    private MenuView menuView;

    public InventoryController(InventoryManagement model, InventoryView view, MenuView menuView) {
        this.imsmodel = model;
        this.imsview = view;
        this.menuView = menuView;

        view.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdateInventoryDialog();
            }
        });

        view.getExitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getFrame().dispose();
                menuView.getFrame().setVisible(true);

            }
        });

        // Debugger lang if maaadd items sa table & model
        System.out.println("Populating table with items from the model.");
        populateTable();
    }

    private void populateTable() {
        DefaultTableModel tableModel = imsview.getTableModel();

        // Debug to check number of items
        System.out.println("Number of items in inventory: " + imsmodel.getInventory().size());

        for (InventoryItem item : imsmodel.getInventory()) {
            // Print each item (debug)
            System.out.println("Adding item to table: " + item.getName());
            tableModel.addRow(new Object[]{
                item.getName(),
                item.getQuantity() + " " + item.getUnit(),
                item.getSKU(),
                item.getCost()
            });
        }
    }

    private void showUpdateInventoryDialog() {
        JDialog dialog = new JDialog(imsview.getFrame(), "Update Inventory", true);
        dialog.setSize(500, 300);
        dialog.setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Update Inventory", SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(dialog.getWidth(), 40));
        // headerLabel.sestFont(new Font("Arial", Font.BOLD, 24));
        dialog.add(headerLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel skuLabel = new JLabel("Select an SKU:");
        JComboBox<String> skuComboBox = new JComboBox<>();
        for (int i = 0; i < imsview.getTableModel().getRowCount(); i++) {
            String sku = imsview.getTableModel().getValueAt(i, 2).toString();
            String name = imsview.getTableModel().getValueAt(i, 0).toString();
            skuComboBox.addItem(sku + " - " + name);
        }

        JLabel qtyLabel = new JLabel("How many units/QTY to add:");
        JTextField qtyField = new JTextField();
        JLabel costLabel = new JLabel("Enter the cost of the added quantity:");
        JTextField costField = new JTextField();

        inputPanel.add(skuLabel);
        inputPanel.add(skuComboBox);
        inputPanel.add(qtyLabel);
        inputPanel.add(qtyField);
        inputPanel.add(costLabel);
        inputPanel.add(costField);

        dialog.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = skuComboBox.getSelectedItem().toString();//get yung selected item ng combobox
                String[] parts = selectedItem.split(" - ");//seperate yung 2 parts sperated by - 
                if (parts.length > 0) {
                    String skuStr = parts[0];//get the 1st part which is the sku
                    int sku = Integer.parseInt(skuStr);//parse it here
                    String qtyStr = qtyField.getText();
                    String costStr = costField.getText();

                    if (!skuStr.isEmpty() && !qtyStr.isEmpty() && !costStr.isEmpty()) {
                        try {
                            int qty = Integer.parseInt(qtyStr);
                            float cost = Float.parseFloat(costStr);
                            updateInventory(sku, qty, cost);
                            imsmodel.saveInventoryToDatabase(); // Save updates to the database
                            updateTable();
                            dialog.dispose();
                        } catch (NumberFormatException ex) {
                            // If di valid number formats
                            JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for quantity and cost.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        // If may namiss....na field kasi
                        JOptionPane.showMessageDialog(dialog, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "Invalid SKU format.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        dialog.setLocationRelativeTo(null);  
        dialog.setVisible(true);
    }

    // Update the inventory based on the provided SKU, quantity, and cost
    private void updateInventory(int sku, int qty, float cost) {
        for (InventoryItem item : imsmodel.getInventory()) {
            if (item.getSKU() == sku) {
                item.addQuantity(qty);
                item.setCost(item.getCost() + cost);
                // Debugger pt.1234567
                System.out.println("\nSKU:" + item.getSKU() + "\nItem: " + item.getName() + "\nNew Quantity:" + item.getQuantity() + "\nNew Cost:" + item.getCost());
                JOptionPane.showMessageDialog(imsview.getFrame(), "Inventory updated successfully!");
                return;
            }
        }
        JOptionPane.showMessageDialog(imsview.getFrame(), "Item with SKU " + sku + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Update the table after the inventory update
    private void updateTable() {
        DefaultTableModel tableModel = imsview.getTableModel();
        tableModel.setRowCount(0); // Clear the table
        populateTable(); // Repopulate table with updated data
    }
}
