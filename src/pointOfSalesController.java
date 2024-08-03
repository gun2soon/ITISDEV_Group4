import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;

public class pointOfSalesController {
    private pointOfSales model;
    private pointOfSalesView view;
    private InventoryManagement inventory;
    private MenuView menuView;

    public pointOfSalesController(pointOfSales model, pointOfSalesView view, InventoryManagement inventory, MenuView menuView) {
        this.model = model;
        this.view = view;
        this.inventory = inventory;
        this.menuView = menuView;

        this.view.setMenuLabelListener(new MenuLabelListener());
        this.view.setComboBoxListener(new ComboBoxListener());
        this.view.setDeleteButtonListener(new DeleteButtonListener());
        this.view.setTransactionSummaryButtonListener (new TransactionSummaryListener());
        this.view.setcheckoutButtonListener(new CheckoutButtonListener());
        this.view.setExitButtonListener(new ExitButtonListener());
    }

    //(Changes) Inner class for ComboBoxListener to handle the discount 
    class ComboBoxListener implements CellEditorListener {
        @Override
            public void editingStopped(ChangeEvent e) {
                DefaultCellEditor def = (DefaultCellEditor) e.getSource();


                
                int selectedIndex = view.getSelectedItemIndex();

                if (selectedIndex != -1) {
                    String discountStr = (String) def.getCellEditorValue();
                    float discount = 0.0f;
                    switch (discountStr) {
                        case "0%":
                            discount = 0.0f;
                            break;
                        case "5%":
                            discount = 0.05f;
                            break;
                        case "10%":
                            discount = 0.10f;
                            break;
                        case "15%":
                            discount = 0.15f;
                            break;
                        case "20%":
                            discount = 0.20f;
                            break;
                        default:
                            break;
                    }
                    model.setDiscount(selectedIndex, discount);
                    view.resetSelection();
                    updateTransactionTotal();


                    System.out.println("Editing stopped: " + discountStr + " at index " + selectedIndex);
                }
                
            }

        @Override
        public void editingCanceled(ChangeEvent e) {
            System.out.println("Editing canceled.");
        }
    }

    class MenuLabelListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel clickedLabel = (JLabel) e.getSource();
            String coffeeType = clickedLabel.getText();
    
            String quantityStr = JOptionPane.showInputDialog(view, "Enter quantity for " + coffeeType + ":");
            if (quantityStr != null) {
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    boolean isIced = coffeeType.startsWith("Iced");
                    float price = model.getCoffeePrice(coffeeType);
    
                    SaleItem saleItem = new SaleItem(coffeeType, quantity, price, isIced, 0);

                    model.addToBasket(saleItem);
                    view.addToBasketTable(quantity, coffeeType, price);

                    updateTransactionTotal();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Invalid quantity entered.");
                }
            }
        }
    }
    
    class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = view.getSelectedItemIndex();
            if (selectedIndex != -1) {
                model.deleteFromBasket(selectedIndex);
                view.deleteFromBasketTable(selectedIndex);

                updateTransactionTotal();
            } else {
                JOptionPane.showMessageDialog(view, "Please select an item to delete.");
            }
        }
    }

    public void updateTransactionTotal() {
        view.setTransactionTotal(String.format("₱ %.2f", model.getTotalBasketPrice() - model.getDiscountedPrice()));
        view.setTransactionDiscount(String.format("₱ %.2f", model.getDiscountedPrice()));
    }

    
    class CheckoutButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Get all items in the basket
            List<SaleItem> basket = model.getBasket();
            
            if (basket.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Basket is empty.");
                return;
            }
    
            // (With changes) Print current basket
            System.out.println("Current Basket:");
            double basketTotal = model.getTotalBasketPrice();
    

            String formattedTotalPrice = String.format("%.2f", basketTotal);
    
            System.out.println("\nTotal Price: Php " + basketTotal);
    
            // Ask for the amount paid
            String amountPaidStr = JOptionPane.showInputDialog(view, 
                    "Enter amount paid by customer:");
            if (amountPaidStr != null) {
                try {
                    double amountPaid = Double.parseDouble(amountPaidStr);
    
                    if (amountPaid < basketTotal) {
                        JOptionPane.showMessageDialog(view, 
                                "Insufficient amount. Total price is: " + formattedTotalPrice);
                    } else {
                        // Calculate change
                        double change = amountPaid - basketTotal;
                        String formattedChange = String.format("%.2f", change);
                        JOptionPane.showMessageDialog(view, 
                                "Total Price: " + formattedTotalPrice + "\nAmount Paid: " + amountPaid + 
                                "\nChange: " + formattedChange);
    
                 

                        for (SaleItem item : basket) {
                            model.sellCoffee(item.getCoffeeType(), item.getQuantity(), item.isIced());
                         }

                        model.clearBasket();
                        view.getBasketTableModel().setRowCount(0);
                        updateTransactionTotal();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Invalid amount entered.");
                }
            }
        }
    }

    
    // (Changes) Inner class for TransactionSummaryListener to handle the transaction summary
    class TransactionSummaryListener implements ActionListener {
 
        public void actionPerformed(ActionEvent e) {
            List<transactionSummary.Transaction> transactions = model.getTransactions(); 

            // Column names for the table
            String[] columnNames = {"ID", "Coffee Type", "Cost", "Profit", "Date"};

            // Data for the table
            Object[][] data = new Object[transactions.size()][5];
            for (int i = 0; i < transactions.size(); i++) {
                transactionSummary.Transaction transaction = transactions.get(i);
                data[i][0] = transaction.getIdTransaction();
                data[i][1] = transaction.getCoffeeType();
                data[i][2] = transaction.getCost();
                data[i][3] = transaction.getProfit();
                data[i][4] = transaction.getDate();
            }

            // Create a table model and set it to the JTable
            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(tableModel);

            // Create a scroll pane and add the table to it
            JScrollPane scrollPane = new JScrollPane(table);

            // Show the table inside a MessageDialog
            JOptionPane.showMessageDialog(null, scrollPane, "Transaction Summary", JOptionPane.PLAIN_MESSAGE);
        
        }
    }    

    
    class ExitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            menuView.getFrame().setVisible(true); // Show the menu view frame
        }
    }

}
