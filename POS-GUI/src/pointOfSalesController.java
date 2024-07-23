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

    public float calculateInventoryCost(String coffeeType, int quantity) {
        // Define cost based on coffee type and quantity
        float cost = 0.0f;
    
        // Define the inventory cost requirements for each coffee type
        switch (coffeeType) {
            case "Iced Cafe Americano":
                cost += inventory.getItemCost("Coffee Beans") * 22 * quantity;
                cost += inventory.getItemCost("16 oz Cold Cups") * 1 * quantity;
                cost += inventory.getItemCost("Lid: Cold Lids") * 1 * quantity;
                cost += inventory.getItemCost("Straws") * 1 * quantity;
                break;
            case "Iced Cafe Mocha":
                cost += inventory.getItemCost("Coffee Beans") * 22 * quantity;
                cost += inventory.getItemCost("Chocolate Syrup") * 15 * quantity;
                cost += inventory.getItemCost("Simple Syrup") * 10 * quantity;
                cost += inventory.getItemCost("Milk") * 250 * quantity;
                cost += inventory.getItemCost("16 oz Cold Cups") * 1 * quantity;
                cost += inventory.getItemCost("Lid: Cold Lids") * 1 * quantity;
                cost += inventory.getItemCost("Straws") * 1 * quantity;
                break;
            case "Iced Vanilla Macchiato":
                cost += inventory.getItemCost("Coffee Beans") * 12 * quantity;
                cost += inventory.getItemCost("Vanilla Syrup") * 15 * quantity;
                cost += inventory.getItemCost("Simple Syrup") * 10 * quantity;
                cost += inventory.getItemCost("Milk") * 250 * quantity;
                cost += inventory.getItemCost("16 oz Cold Cups") * 1 * quantity;
                cost += inventory.getItemCost("Lid: Cold Lids") * 1 * quantity;
                cost += inventory.getItemCost("Straws") * 1 * quantity;
                break;
            case "Iced Caramel Macchiato":
                cost += inventory.getItemCost("Coffee Beans") * 12 * quantity;
                cost += inventory.getItemCost("Caramel Syrup") * 15 * quantity;
                cost += inventory.getItemCost("Simple Syrup") * 10 * quantity;
                cost += inventory.getItemCost("Milk") * 250 * quantity;
                cost += inventory.getItemCost("16 oz Cold Cups") * 1 * quantity;
                cost += inventory.getItemCost("Lid: Cold Lids") * 1 * quantity;
                cost += inventory.getItemCost("Straws") * 1 * quantity;
                break;
            case "Hot Cafe Americano":
                cost += inventory.getItemCost("Coffee Beans") * 20 * quantity;
                cost += inventory.getItemCost("16 oz Hot Cups") * 1 * quantity;
                cost += inventory.getItemCost("Lid: Hot Lids") * 1 * quantity;
                break;
            case "Hot Cafe Mocha":
                cost += inventory.getItemCost("Coffee Beans") * 20 * quantity;
                cost += inventory.getItemCost("Chocolate Syrup") * 15 * quantity;
                cost += inventory.getItemCost("Simple Syrup") * 10 * quantity;
                cost += inventory.getItemCost("Milk") * 250 * quantity;
                cost += inventory.getItemCost("16 oz Hot Cups") * 1 * quantity;
                cost += inventory.getItemCost("Lid: Hot Lids") * 1 * quantity;
                break;
            case "Hot Vanilla Macchiato":
                cost += inventory.getItemCost("Coffee Beans") * 10 * quantity;
                cost += inventory.getItemCost("Vanilla Syrup") * 15 * quantity;
                cost += inventory.getItemCost("Simple Syrup") * 10 * quantity;
                cost += inventory.getItemCost("Milk") * 250 * quantity;
                cost += inventory.getItemCost("16 oz Hot Cups") * 1 * quantity;
                cost += inventory.getItemCost("Lid: Hot Lids") * 1 * quantity;
                break;
            case "Hot Caramel Macchiato":
                cost += inventory.getItemCost("Coffee Beans") * 10 * quantity;
                cost += inventory.getItemCost("Caramel Syrup") * 15 * quantity;
                cost += inventory.getItemCost("Simple Syrup") * 10 * quantity;
                cost += inventory.getItemCost("Milk") * 250 * quantity;
                cost += inventory.getItemCost("16 oz Hot Cups") * 1 * quantity;
                cost += inventory.getItemCost("Lid: Hot Lids") * 1 * quantity;
                break;
            default:
                System.out.println("Invalid coffee type.");
                break;
        }
        return cost;
    }
    
    class CheckoutButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        // Get all items in the basket
        List<SaleItem> basket = model.getBasket();
        if (basket.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Basket is empty.");
            return;
        }

        // Calculate total price
        float totalPrice = 0;
        //float totalCost = 0;

        for (SaleItem item : basket) {
            totalPrice += item.getQuantity() * item.getPrice();

            // Calculate cost from inventory
            float profit = calculateInventoryCost(item.getCoffeeType(), item.getQuantity());
            //totalCost += cost;

            // Calculate profit
            float cost = item.getPrice();

            model.addTransaction(item.getCoffeeType(), cost, profit);
        }

        // Ask if the user has a discount
        int discountOption = JOptionPane.showConfirmDialog(view, 
                "Do you have a discount? (20% off)", 
                "Discount", 
                JOptionPane.YES_NO_OPTION);

        // Apply discount if applicable
        float discountAmount = 0;
        if (discountOption == JOptionPane.YES_OPTION) {
            discountAmount = totalPrice * 0.20f; // Calculate 20% discount
            totalPrice -= discountAmount;
            String formattedDiscountAmount = String.format("%.2f", -discountAmount);
            view.addToBasketTable("Discount (20%)", formattedDiscountAmount);
        }

        view.addBlankLine();

        String formattedTotalPrice = String.format("%.2f", totalPrice);
        view.addToBasketTable("Total Price", formattedTotalPrice);

        // Ask for the amount paid
        String amountPaidStr = JOptionPane.showInputDialog(view, 
                "Enter amount paid by customer:");
        if (amountPaidStr != null) {
            try {
                float amountPaid = Float.parseFloat(amountPaidStr);

                if (amountPaid < totalPrice) {
                    JOptionPane.showMessageDialog(view, 
                            "Insufficient amount. Total price is: " + formattedTotalPrice);
                } else {
                    float change = amountPaid - totalPrice;
                    String formattedChange = String.format("%.2f", change);
                    JOptionPane.showMessageDialog(view, 
                            "Total Price: " + formattedTotalPrice + "\nAmount Paid: " + amountPaid + 
                            "\nChange: " + formattedChange);
                    
                    // Clear the basket after checkout
                    model.clearBasket();
                    view.getBasketTableModel().setRowCount(0);
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
