import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class pointOfSalesController {
    private pointOfSales model;
    private pointOfSalesView view;
    private InventoryManagement inventory;
    private MenuView menuView;
    private Boolean isMaxPriceDiscount = false;

    public pointOfSalesController(pointOfSales model, pointOfSalesView view, InventoryManagement inventory, MenuView menuView) {
        this.model = model;
        this.view = view;
        this.inventory = inventory;
        this.menuView = menuView;

        this.view.setMenuLabelListener(new MenuLabelListener());
        // this.view.setComboBoxListener(new ComboBoxListener());
        this.view.setDiscountedButtonListener(new DiscountChangeListener());
        this.view.setDeleteButtonListener(new DeleteButtonListener());
        this.view.setTransactionSummaryButtonListener (new TransactionSummaryListener());
        this.view.setcheckoutButtonListener(new CheckoutButtonListener());
        this.view.setExitButtonListener(new ExitButtonListener());
        this.view.setQuantityListener(
            new ComboBoxListener() {
                @Override
                public void editingStopped(ChangeEvent e) {
                    DefaultCellEditor def = (DefaultCellEditor) e.getSource();
                    int selectedIndex = view.getSelectedItemIndex();
                    if (selectedIndex != -1) {
                        String quantityStr = (String) def.getCellEditorValue();
                        SaleItem saleItem = model.getBasket().get(selectedIndex);

                        int quantity = 0;
                        try {
                            quantity = Integer.parseInt(quantityStr);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid quantity. Please enter a number.");
                            // reset the value to the previous quantity
                            view.getBasketTableModel().setValueAt(saleItem.getQuantity(), selectedIndex, 0);
                            return;
                        }
                        saleItem.setQuantity(quantity);
                        view.resetSelection();
                        view.getBasketTableModel().setValueAt(saleItem.getPrice(), selectedIndex, 3);
                        System.out.println("Editing stopped: " + quantityStr + " at index " + selectedIndex);
                    }

                    
                    updateTransactionTotal();
                }
            }
        );
    }



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

    class DiscountChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            JToggleButton button = (JToggleButton) e.getSource();
            isMaxPriceDiscount = button.isSelected();
            model.setMaxPriceDiscount(button.isSelected());
            updateTransactionTotal();
        }
    }


    class MenuLabelListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel clickedLabel = (JLabel) e.getSource();
            String coffeeType = clickedLabel.getText().split("<br>")[0].replaceAll("<html>|</html>|<div style='text-align: center;'>|</div>", "").trim();
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
        double totalBasketPrice = model.getTotalBasketPrice();
        double discountedPrice = model.getDiscountedPrice();
        double totalToPay = totalBasketPrice - discountedPrice;
    
        view.setTransactionTotal(String.format("₱ %.2f", totalToPay));
        view.setTransactionDiscount(String.format("₱ %.2f", discountedPrice));
    
        if (isMaxPriceDiscount) {
            view.setDiscountButtonText("<html>20% discount will be applied to:<p style='font-size: 10px'><i><b>" + model.getDiscountedItem() + "</b></i></p></html>");
        } else {
            view.setDiscountButtonText("Do you have 20% discount?");
        }
    }
    

    
    class CheckoutButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Get all items in the basket
            List<SaleItem> basket = model.getBasket();
    
            if (basket.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Basket is empty.");
                return;
            }
    
            // Print current basket
            System.out.println("Current Basket:");
            double basketTotal = model.getTotalBasketPrice();
            double discountedPrice = model.getDiscountedPrice();
            double totalToPay = basketTotal - discountedPrice;
    
            String formattedTotalPrice = String.format("%.2f", totalToPay);
    
            System.out.println("\nTotal Price: Php " + totalToPay);
    
            // Ask for the amount paid
            String amountPaidStr = JOptionPane.showInputDialog(view, "Enter amount paid by customer:");
            if (amountPaidStr != null) {
                try {
                    double amountPaid = Double.parseDouble(amountPaidStr);
    
                    if (amountPaid < totalToPay) {
                        JOptionPane.showMessageDialog(view, "Insufficient amount. Total price is: " + formattedTotalPrice);
                        model.clearBasket(); 
                        view.getBasketTableModel().setRowCount(0);
                        updateTransactionTotal();
                    } else {
                        // Calculate change
                        double change = amountPaid - totalToPay;
                        String formattedChange = String.format("%.2f", change);
                        JOptionPane.showMessageDialog(view, "Total Price: " + formattedTotalPrice + "\nAmount Paid: " + amountPaid + "\nChange: " + formattedChange);
    
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
    

    

    class TransactionSummaryListener implements ActionListener {
        List<transactionSummary.Transaction> transactions; 
        Object[][] data;
        public void actionPerformed(ActionEvent e) {
            transactions = model.getTransactions();

            // Column names for the table
            String[] columnNames = {"ID", "Cup Qty", "Coffee Type", "Cost", "Profit", "Date"};
            // Set column width
            int[] columnWidth = {100, 100, 300, 100, 100, 200};


            // Data for the table
            data = new Object[transactions.size()][6];
            for (int i = 0; i < transactions.size(); i++) {
                transactionSummary.Transaction transaction = transactions.get(i);
                data[i][0] = transaction.getIdTransaction();
                data[i][1] = transaction.getcupQuantity();
                data[i][2] = transaction.getCoffeeType();
                data[i][3] = transaction.getCost();
                data[i][4] = transaction.getProfit();
                data[i][5] = transaction.getDate();
            }

            // Create a table model and set it to the JTable
            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(tableModel);
            for (int i = 0; i < columnNames.length; i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);
            }

            // Create a scroll pane and add the table to it
            JScrollPane scrollPane = new JScrollPane(table);


            JPanel panel = new JPanel(new BorderLayout());
            panel.add(scrollPane, BorderLayout.CENTER);

            JCheckBox dateFilter = new JCheckBox("Filter by Date");
            

            AbstractFormatter customFormatter = new AbstractFormatter() {
                private String datePattern = "yyyy-MM-dd";
                private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
                
                @Override
                public Object stringToValue(String text) throws ParseException {
                    return dateFormatter.parseObject(text);
                }
            
                @Override
                public String valueToString(Object value) throws ParseException {
                    if (value != null) {
                        Calendar cal = (Calendar) value;
                        return dateFormatter.format(cal.getTime());
                    }
                    
                    return "";
                }
            };

            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");

            UtilDateModel fromDateModel = new UtilDateModel();
            fromDateModel.setValue(Calendar.getInstance().getTime());
            JDatePanelImpl fromDatePanel = new JDatePanelImpl(fromDateModel, p);
            JDatePickerImpl fromDatePicker = new JDatePickerImpl(fromDatePanel, customFormatter);
            fromDatePicker.setFont(new Font("Arial", Font.PLAIN, 16));

            UtilDateModel toDateModel = new UtilDateModel();
            toDateModel.setValue(Calendar.getInstance().getTime());
            JDatePanelImpl toDatePanel = new JDatePanelImpl(toDateModel, p);
            JDatePickerImpl toDatePicker = new JDatePickerImpl(toDatePanel, customFormatter);
            toDatePicker.setFont(new Font("Arial", Font.PLAIN, 16));


            fromDatePicker.getComponent(1).setEnabled(false);
            toDatePicker.getComponent(1).setEnabled(false);

            dateFilter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean selected = dateFilter.isSelected();
                    fromDatePicker.getComponent(1).setEnabled(selected);
                    toDatePicker.getComponent(1).setEnabled(selected);

                    tableModel.setRowCount(0);
                    if (selected) {                        
                        transactions = model.getTransactions(fromDateModel.getValue(), toDateModel.getValue());
                    } else {
                        transactions = model.getTransactions();
                    }

                    data = new Object[transactions.size()][6];
                    for (int i = 0; i < transactions.size(); i++) {
                        transactionSummary.Transaction transaction = transactions.get(i);
                        data[i][0] = transaction.getIdTransaction();
                        data[i][1] = transaction.getcupQuantity();
                        data[i][2] = transaction.getCoffeeType();
                        data[i][3] = transaction.getCost();
                        data[i][4] = transaction.getProfit();
                        data[i][5] = transaction.getDate();
                    }
                    tableModel.setDataVector(data, columnNames);
                }
            });

            ChangeListener dateChangeListener = new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    boolean selected = dateFilter.isSelected();
                    fromDatePicker.getComponent(1).setEnabled(selected);
                    toDatePicker.getComponent(1).setEnabled(selected);

                    tableModel.setRowCount(0);
                    if (selected) {                        
                        transactions = model.getTransactions(fromDateModel.getValue(), toDateModel.getValue());
                    } else {
                        transactions = model.getTransactions();
                    }

                    data = new Object[transactions.size()][6];
                    for (int i = 0; i < transactions.size(); i++) {
                        transactionSummary.Transaction transaction = transactions.get(i);
                        data[i][0] = transaction.getIdTransaction();
                        data[i][1] = transaction.getcupQuantity();
                        data[i][2] = transaction.getCoffeeType();
                        data[i][3] = transaction.getCost();
                        data[i][4] = transaction.getProfit();
                        data[i][5] = transaction.getDate();
                    }
                    tableModel.setDataVector(data, columnNames);

                }
            };
            fromDateModel.addChangeListener(dateChangeListener);
            toDateModel.addChangeListener(dateChangeListener);

            JPanel datePickers = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets.set(5, 0, 5, 0);

            gbc.gridwidth = 2;
            datePickers.add(dateFilter, gbc);
            gbc.gridwidth = 1;
            gbc.gridy++;
            gbc.insets.set(2, 0, 5, 5);
            datePickers.add(new JLabel("From: "), gbc);
            gbc.gridy++;
            datePickers.add(fromDatePicker, gbc);
            gbc.gridy = 1;
            gbc.gridx++;
            gbc.insets.set(2, 5, 5, 0);
            datePickers.add(new JLabel("To: "), gbc);
            gbc.gridy++;
            datePickers.add(toDatePicker, gbc);
            





            panel.add(datePickers, BorderLayout.SOUTH);
            

            // Show the table inside a MessageDialog
            JOptionPane.showMessageDialog(null, panel, "Transaction Summary", JOptionPane.PLAIN_MESSAGE);
        
        }
    }    

    
    class ExitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            menuView.getFrame().setVisible(true); // Show the menu view frame
            inventory.saveInventoryToDatabase();
        }
    }

}
