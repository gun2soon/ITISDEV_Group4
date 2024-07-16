import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class pointOfSales {
    private InventoryManagement inventory;
    private float totalSales;
    private List<SaleItem> basket;
    private transactionSummary transactionSummary; // New field

    public pointOfSales(InventoryManagement inventory) {
        this.inventory = inventory;
        this.totalSales = 0.0f;
        this.basket = new ArrayList<>();
        this.transactionSummary = new transactionSummary(); // Initialize TransactionSummary
    }

    private void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void displayTransactionSummary() {
        transactionSummary.displaySummary();
    }

    public void POSMenu() {
        cls();
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        while (choice != 5) { // Updated to include new menu option
            System.out.println("\nPoint of Sales Menu: Ready Coffee PH!\n");

            System.out.println("Available Coffee Options:");
            for (Coffee.CoffeeOption option : Coffee.getCoffeeOptions().values()) {
                System.out.println("[AVAILABLE] " + option.name + " Php " + Coffee.getCoffeePrice(option.name));
            }
            System.out.println();

            displayBasket();
            System.out.println();

            System.out.println("[1] Add to Basket");
            System.out.println("[2] Delete Items on Basket");
            System.out.println("[3] Checkout");
            System.out.println("[4] View Transaction Summary"); // New option
            System.out.println("[5] Exit POS Menu\n");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addToBasket();
                    break;
                case 2:
                    deleteFromBasket();
                    break;
                case 3:
                    viewBasketAndCheckout();
                    break;
                case 4:
                    displayTransactionSummary(); // Display summary
                    break;
                case 5:
                    System.out.print("\nExiting POS Menu\n");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
        sc.close();
    }

    private void addToBasket() {
        cls();
        Scanner sc = new Scanner(System.in);
        Map<Integer, Coffee.CoffeeOption> coffeeOptions = Coffee.getCoffeeOptions();

        System.out.println("Select coffee to add:");
        for (Map.Entry<Integer, Coffee.CoffeeOption> entry : coffeeOptions.entrySet()) {
            System.out.println("[" + entry.getKey() + "] " + entry.getValue().name);
        }
        System.out.println("[0] Cancel");
        System.out.print("Enter choice: ");
        int coffeeChoice = sc.nextInt();

        if (coffeeChoice == 0) {
            System.out.println("Transaction cancelled.");
            sc.close();
            return;
        }

        Coffee.CoffeeOption selectedOption = coffeeOptions.get(coffeeChoice);
        if (selectedOption == null) {
            System.out.println("Invalid choice.");
            sc.close();
            return;
        }

        System.out.print("Enter quantity (0 to cancel): ");
        int quantity = sc.nextInt();

        if (quantity == 0) {
            System.out.println("Transaction canceled.");
            sc.close();
            return;
        }

        // Add to basket
        SaleItem item = new SaleItem(selectedOption.name, quantity, Coffee.getCoffeePrice(selectedOption.name), selectedOption.isIced);
        basket.add(item);

        System.out.println(quantity + " " + selectedOption.name + "(s) added to the basket.");
        System.out.println("Press enter to continue...");
        sc.nextLine(); // Consume newline left-over
        sc.nextLine(); // Wait for user to press enter
        sc.close();
    }

    private void displayBasket() {
        if (basket.isEmpty()) {
            System.out.println("Basket is empty.");
            return;
        }

        System.out.println("Current Basket:");
        float basketTotal = 0.0f;
        for (int i = 0; i < basket.size(); i++) {
            SaleItem item = basket.get(i);
        System.out.println("[" + (i + 1) + "] " + item);
        basketTotal += item.getPrice();
        }
        System.out.println("Total: " + basketTotal);
    }

    private void deleteFromBasket() {
        Scanner sc = new Scanner(System.in);
        cls();
       
        displayBasket();

        System.out.print("Enter item number to delete: ");
        int itemNumber = sc.nextInt();

        if (itemNumber < 1 || itemNumber > basket.size()) {
            System.out.println("Invalid item number.");
            sc.close();
            return;
        }

        SaleItem removedItem = basket.remove(itemNumber - 1);
        System.out.println("Removed: " + removedItem.getQuantity() + " " + removedItem.getCoffeeType());
        System.out.println("Press enter to continue...");
        sc.nextLine(); // Consume newline left-over
        sc.nextLine(); // Wait for user to press enter
        sc.close();
    }

    private void viewBasketAndCheckout() {
        cls();
        if (basket.isEmpty()) {
            System.out.println("Basket is empty.");
            return;
        }
    
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
    
        // Ask if they have a discount
        Scanner sc = new Scanner(System.in);
        System.out.print("Do you have a discount? [Y/N]: ");
        String discountResponse = sc.next();
    
        if (discountResponse.equalsIgnoreCase("Y")) {
            if (maxPriceIndex != -1) {
                float discountAmount = maxPrice * 0.2f;
                if (basketTotal < discountAmount) {
                    discountAmount = basketTotal; // Ensure discount doesn't exceed total price
                }
                System.out.println("Applying a 20% discount on the most expensive item: Php -" + discountAmount);
                basketTotal -= discountAmount;
            }
        }
    
        System.out.println("\nTotal Price: Php " + basketTotal);
    
        // Payment handling
        System.out.print("Enter amount paid: ");
        float amountPaid = sc.nextFloat();
    
        if (amountPaid < basketTotal) {
            System.out.println("Insufficient payment.");
            sc.close();
            return;
        }
    
        // Calculate change
        float change = amountPaid - basketTotal;
        System.out.println("Change: Php " + change);
    
        // Confirm checkout
        System.out.print("Confirm checkout [Y/N]? ");
        String confirm = sc.next();
    
        if (confirm.equalsIgnoreCase("Y")) {
            for (SaleItem item : basket) {
                sellCoffee(item.getCoffeeType(), item.getQuantity(), item.isIced());
            }
            basket.clear();
        }
        sc.close();
    }

    public float calculateInventoryCost(String coffeeType, int quantity) {
        // Define cost based on coffee type and quantity
        float cost = 0.0f;
        System.out.println("name: " + coffeeType);
        System.out.println("qty: " + quantity);
        System.out.println("Cost start: " + cost);
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
        System.out.println("Cost end: " + cost);
        return cost;
    }
    

    public void sellCoffee(String coffeeType, int quantity, boolean isIced) {
        float price = Coffee.getCoffeePrice(coffeeType) * quantity;
        totalSales += price;

        // Calculate cost from inventory
        float cost = calculateInventoryCost(coffeeType, quantity);
    
        // Calculate profit
        float profit = price - cost;

        Coffee coffee = new Coffee(coffeeType, quantity, price / quantity, isIced);
        coffee.createCoffee(inventory, quantity);

        // Record the transaction
        transactionSummary.addTransaction(coffeeType, price, profit);
    }

    // Inner class to represent an item in the basket
    private class SaleItem {
        private String coffeeType;
        private int quantity;
        private float price;
        private boolean isIced;
    
        public SaleItem(String coffeeType, int quantity, float price, boolean isIced) {
            this.coffeeType = coffeeType;
            this.quantity = quantity;
            this.price = price;
            this.isIced = isIced;
        }
    
        public String getCoffeeType() {
            return coffeeType;
        }
    
        public int getQuantity() {
            return quantity;
        }
    
        public float getPrice() {
            return price * quantity;
        }
    
        public boolean isIced() {
            return isIced;
        }
    
        @Override
        public String toString() {
            return quantity + " " + coffeeType + "(s)";
        }
    }
}