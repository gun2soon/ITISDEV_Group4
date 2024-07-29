import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;

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
        this.view.setDeleteButtonListener(new DeleteButtonListener());
        this.view.setTransactionSummaryButtonListener (new TransactionSummaryListener());
        this.view.setcheckoutButtonListener(new CheckoutButtonListener());
        this.view.setExitButtonListener(new ExitButtonListener());
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
    
                    SaleItem saleItem = new SaleItem(coffeeType, quantity, price, isIced);
                    model.addToBasket(saleItem);
                    view.addToBasketTable(quantity, coffeeType, price);
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
            } else {
                JOptionPane.showMessageDialog(view, "Please select an item to delete.");
            }
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
            float basketTotal = 0.0f;
            float maxPrice = 0.0f;
            int maxPriceIndex = -1;
    
            for (int i = 0; i < basket.size(); i++) {
                SaleItem item = basket.get(i);
                System.out.println("[" + (i + 1) + "] " + item);
                basketTotal += item.getPrice();
                if (item.getPrice() > maxPrice) {
                    maxPrice = item.getPrice();
                    maxPriceIndex = i;
                }
            }
    
            // Ask if the user has a discount
            int discountOption = JOptionPane.showConfirmDialog(view, 
                    "Do you have a discount? (20% off)", 
                    "Discount", 
                    JOptionPane.YES_NO_OPTION);
    
            // Apply discount if applicable
            float discountAmount = 0;
            if (discountOption == JOptionPane.YES_OPTION) {
                if (maxPriceIndex != -1) {
                    discountAmount = maxPrice * 0.2f;
                    if (basketTotal < discountAmount) {
                        discountAmount = basketTotal; // Ensure discount doesn't exceed total price
                    }
                    System.out.println("Applying a 20% discount on the most expensive item: Php -" + discountAmount);
                    basketTotal -= discountAmount;

                    String formattedDiscountAmount = String.format("%.2f", -discountAmount);
                    view.addToBasketTable("Discount (20%)", formattedDiscountAmount);
                }
            }

            view.addBlankLine();

            String formattedTotalPrice = String.format("%.2f", basketTotal);
            view.addToBasketTable("Total Price", formattedTotalPrice);
    
            System.out.println("\nTotal Price: Php " + basketTotal);
    
            // Ask for the amount paid
            String amountPaidStr = JOptionPane.showInputDialog(view, 
                    "Enter amount paid by customer:");
            if (amountPaidStr != null) {
                try {
                    float amountPaid = Float.parseFloat(amountPaidStr);
    
                    if (amountPaid < basketTotal) {
                        JOptionPane.showMessageDialog(view, 
                                "Insufficient amount. Total price is: " + formattedTotalPrice);
                    } else {
                        // Calculate change
                        float change = amountPaid - basketTotal;
                        String formattedChange = String.format("%.2f", change);
                        JOptionPane.showMessageDialog(view, 
                                "Total Price: " + formattedTotalPrice + "\nAmount Paid: " + amountPaid + 
                                "\nChange: " + formattedChange);
    
                        // Confirm checkout
                        /*int confirmOption = JOptionPane.showConfirmDialog(view, 
                                "Confirm checkout?", 
                                "Checkout", 
                                JOptionPane.YES_NO_OPTION);
                        if (confirmOption == JOptionPane.YES_OPTION) {
                            for (SaleItem item : basket) {
                                model.sellCoffee(item.getCoffeeType(), item.getQuantity(), item.isIced());
                            }
                            model.clearBasket();
                            view.getBasketTableModel().setRowCount(0);
                        }*/
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Invalid amount entered.");
                }
            }
        }
    }

    class TransactionSummaryListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Display the transaction summary in a dialog
            JOptionPane.showMessageDialog(view, 
                model.getTransactionSummary(), // Fetch summary from model
                "Transaction Summary",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }    

    
    class ExitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            menuView.getFrame().setVisible(true); // Show the menu view frame
        }
    }
}
