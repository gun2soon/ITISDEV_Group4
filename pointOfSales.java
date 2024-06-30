import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class pointOfSales {
    private InventoryManagement inventory;
    private float totalSales;
    private List<SaleItem> basket;

    public pointOfSales(InventoryManagement inventory) {
        this.inventory = inventory;
        this.totalSales = 0.0f;
        this.basket = new ArrayList<>();
    }

    private void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void POSMenu() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while (choice != 4) {
            cls();
            System.out.println("\nReady Coffee PH - Point of Sales (POS)\n");
            System.out.println("Available Menu: \n");

            System.out.println("[AVAILABLE] Iced Cafe Americano");
            System.out.println("[AVAILABLE] Hot Cafe Americano");
            System.out.println("[AVAILABLE] Iced Cafe Mocha");
            System.out.println("[AVAILABLE] Hot Cafe Mocha");
            System.out.println("[AVAILABLE] Iced Vanilla Macchiato");
            System.out.println("[AVAILABLE] Hot Vanilla Macchiato");
            System.out.println("[AVAILABLE] Iced Caramel Macchiato");
            System.out.println("[AVAILABLE] Hot Caramel Macchiato\n\n");

            displayBasket();

            System.out.println("\n\n[1] Add Item to Basket");
            System.out.println("[2] Delete Item from Basket");
            System.out.println("[3] View Basket and Checkout");
            System.out.println("[4] Exit POS\n");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

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
                    cls();
                    System.out.println("Exiting POS...\n");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void addToBasket() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select coffee to add:");
        System.out.println("[1] Iced Cafe Americano");
        System.out.println("[2] Hot Cafe Americano");
        System.out.println("[3] Iced Cafe Mocha");
        System.out.println("[4] Hot Cafe Mocha");
        System.out.println("[5] Iced Vanilla Macchiato");
        System.out.println("[6] Hot Vanilla Macchiato");
        System.out.println("[7] Iced Caramel Macchiato");
        System.out.println("[8] Hot Caramel Macchiato");
        System.out.println("[0] Cancel");
        System.out.print("Enter choice: ");
        int coffeeChoice = sc.nextInt();

        if (coffeeChoice == 0) {
            System.out.println("Transaction cancelled.");
            return;
        }
    

        String coffeeType = "";
        boolean isIced = false;
        switch (coffeeChoice) {
            case 1:
                coffeeType = "Iced Cafe Americano";
                isIced = true;
                break;
            case 2:
                coffeeType = "Hot Cafe Americano";
                isIced = false;
                break;
            case 3:
                coffeeType = "Iced Cafe Mocha";
                isIced = true;
                break;
            case 4:
                coffeeType = "Hot Cafe Mocha";
                isIced = false;
                break;
            case 5:
                coffeeType = "Iced Vanilla Macchiato";
                isIced = true;
                break;
            case 6:
                coffeeType = "Hot Vanilla Macchiato";
                isIced = false;
                break;
            case 7:
                coffeeType = "Iced Caramel Macchiato";
                isIced = true;
                break;
            case 8:
                coffeeType = "Hot Caramel Macchiato";
                isIced = false;
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        System.out.print("Enter quantity (0 to cancel): ");
        int quantity = sc.nextInt();

        // Cancel transaction if quantity is 0
        if (quantity == 0) {
        System.out.println("Transaction canceled.");
        return;
    }
        // Add to basket
        SaleItem item = new SaleItem(coffeeType, quantity, getCoffeePrice(coffeeType), isIced);
        basket.add(item);

        System.out.println(quantity + " " + coffeeType + "(s) added to the basket.");
        System.out.println("Press enter to continue...");
        sc.nextLine(); // Consume newline left-over
        sc.nextLine(); // Wait for user to press enter
    }

    private void displayBasket() {

        

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
       
        displayBasket();

        System.out.print("Enter item number to delete: ");
        int itemNumber = sc.nextInt();

        if (itemNumber < 1 || itemNumber > basket.size()) {
            System.out.println("Invalid item number.");
            return;
        }

        SaleItem removedItem = basket.remove(itemNumber - 1);
        System.out.println("Removed: " + removedItem.getQuantity() + " " + removedItem.getCoffeeType());
        System.out.println("Press enter to continue...");
        sc.nextLine(); // Consume newline left-over
        sc.nextLine(); // Wait for user to press enter
    }

    private void viewBasketAndCheckout() {
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
    }

    // Continuing from where we left off

    private void sellCoffee(String coffeeType, int quantity, boolean isIced) {
        // Update inventory and total sales for each item sold
        float price = getCoffeePrice(coffeeType) * quantity;
        totalSales += price;

        // Adjust inventory based on recipe
        if (isIced) {
            switch (coffeeType) {
                case "Iced Cafe Americano":
                    inventory.removeItem("Coffee Beans", 22 * quantity);
                    inventory.removeItem("16 oz Cold Cups", 1 * quantity);
                    inventory.removeItem("Lid: Cold Lids", 1 * quantity);
                    inventory.removeItem("Straws", 1 * quantity);
                    break;
                case "Iced Cafe Mocha":
                    inventory.removeItem("Coffee Beans", 22 * quantity);
                    inventory.removeItem("Chocolate Syrup", 15 * quantity);
                    inventory.removeItem("Simple Syrup", 10 * quantity);
                    inventory.removeItem("Milk", 250 * quantity);
                    inventory.removeItem("16 oz Cold Cups", 1 * quantity);
                    inventory.removeItem("Lid: Cold Lids", 1 * quantity);
                    inventory.removeItem("Straws", 1 * quantity);
                    break;
                case "Iced Vanilla Macchiato":
                    inventory.removeItem("Coffee Beans", 12 * quantity);
                    inventory.removeItem("Vanilla Syrup", 15 * quantity);
                    inventory.removeItem("Simple Syrup", 10 * quantity);
                    inventory.removeItem("Milk", 250 * quantity);
                    inventory.removeItem("16 oz Cold Cups", 1 * quantity);
                    inventory.removeItem("Lid: Cold Lids", 1 * quantity);
                    inventory.removeItem("Straws", 1 * quantity);
                    break;
                case "Iced Caramel Macchiato":
                    inventory.removeItem("Coffee Beans", 12 * quantity);
                    inventory.removeItem("Caramel Syrup", 15 * quantity);
                    inventory.removeItem("Simple Syrup", 10 * quantity);
                    inventory.removeItem("Milk", 250 * quantity);
                    inventory.removeItem("16 oz Cold Cups", 1 * quantity);
                    inventory.removeItem("Lid: Cold Lids", 1 * quantity);
                    inventory.removeItem("Straws", 1 * quantity);
                    break;
                default:
                    System.out.println("Invalid coffee type.");
                    break;
            }
        } else { // Hot variants
            switch (coffeeType) {
                case "Hot Cafe Americano":
                    inventory.removeItem("Coffee Beans", 20 * quantity);
                    inventory.removeItem("16 oz Hot Cups", 1 * quantity);
                    inventory.removeItem("Lid: Hot Lids", 1 * quantity);
                    break;
                case "Hot Cafe Mocha":
                    inventory.removeItem("Coffee Beans", 20 * quantity);
                    inventory.removeItem("Chocolate Syrup", 15 * quantity);
                    inventory.removeItem("Simple Syrup", 10 * quantity);
                    inventory.removeItem("Milk", 250 * quantity);
                    inventory.removeItem("16 oz Hot Cups", 1 * quantity);
                    inventory.removeItem("Lid: Hot Lids", 1 * quantity);
                    break;
                case "Hot Vanilla Macchiato":
                    inventory.removeItem("Coffee Beans", 10 * quantity);
                    inventory.removeItem("Vanilla Syrup", 15 * quantity);
                    inventory.removeItem("Simple Syrup", 10 * quantity);
                    inventory.removeItem("Milk", 250 * quantity);
                    inventory.removeItem("16 oz Hot Cups", 1 * quantity);
                    inventory.removeItem("Lid: Hot Lids", 1 * quantity);
                    break;
                case "Hot Caramel Macchiato":
                    inventory.removeItem("Coffee Beans", 10 * quantity);
                    inventory.removeItem("Caramel Syrup", 15 * quantity);
                    inventory.removeItem("Simple Syrup", 10 * quantity);
                    inventory.removeItem("Milk", 250 * quantity);
                    inventory.removeItem("16 oz Hot Cups", 1 * quantity);
                    inventory.removeItem("Lid: Hot Lids", 1 * quantity);
                    break;
                default:
                    System.out.println("Invalid coffee type.");
                    break;
            }
        }
    }

    private float getCoffeePrice(String coffeeType) {
        // Placeholder prices. Adjust these based on actual prices.
        switch (coffeeType) {
            case "Iced Cafe Americano":
                return 120.0f;
            case "Hot Cafe Americano":
                return 110.0f;
            case "Iced Cafe Mocha":
                return 170.0f;
            case "Hot Cafe Mocha":
                return 160.0f;
            case "Iced Vanilla Macchiato":
                return 170.0f;
            case "Hot Vanilla Macchiato":
                return 160.0f;
            case "Iced Caramel Macchiato":
                return 190.0f;
            case "Hot Caramel Macchiato":
                return 180.0f;
            default:
                return 0.0f;
        }
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
