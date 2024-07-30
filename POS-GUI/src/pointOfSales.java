import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class pointOfSales {
    private InventoryManagement inventory;
    private float totalSales;
    private List<SaleItem> basket;
    private SaleItem SaleItem;
    private int itemIndex;
    private transactionSummary transactionSummary; // New field

    public pointOfSales(InventoryManagement inventory) {
        this.inventory = inventory;
        this.totalSales = 0.0f;
        this.basket = new ArrayList<>();
        this.SaleItem = SaleItem; 
        this.transactionSummary = new transactionSummary();
        
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
                    addToBasket(SaleItem);
                    break;
                case 2:
                    deleteFromBasket(itemIndex);
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
    }

    public void addToBasket(SaleItem item) {
        basket.add(item);
    }

    public void deleteFromBasket(int itemIndex) {
        basket.remove(itemIndex);
    }

    public List<SaleItem> getBasket() {
        return basket;
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
    
            Scanner sc = new Scanner(System.in);
            System.out.print("Do you have a discount? [Y/N]: ");
            String discountResponse = sc.next();
    
            if (discountResponse.equalsIgnoreCase("Y") && maxPriceIndex != -1) {
                float discountAmount = maxPrice * 0.2f;
                System.out.println("Applying a 20% discount on the most expensive item: Php -" + discountAmount);
                basketTotal -= discountAmount;
            }
    
            System.out.println("\nTotal Price: Php " + basketTotal);
    
            System.out.print("Enter amount paid: ");
            float amountPaid = sc.nextFloat();
    
            if (amountPaid < basketTotal) {
                System.out.println("Insufficient payment.");
                return;
            }
    
            float change = amountPaid - basketTotal;
            System.out.println("Change: Php " + change);
    
            System.out.print("Confirm checkout [Y/N]? ");
            String confirm = sc.next();
    
            if (confirm.equalsIgnoreCase("Y")) {
                for (SaleItem item : basket) {
                    float cost = getCoffeePrice(item.getCoffeeType()) * item.getQuantity();
                    float profit = cost * 0.2f; // Example profit calculation
                    transactionSummary.addTransaction(item.getCoffeeType(), cost, profit);
                    sellCoffee(item.getCoffeeType(), item.getQuantity(), item.isIced());
                }
                basket.clear();
            }
        }

        public void addTransaction(String coffeeType, float cost, float profit) {
            transactionSummary.addTransaction(coffeeType, cost, profit);
        }
    
        public void clearBasket() {
            basket.clear();
        }
    
        public float calculateInventoryCost(String coffeeType, int quantity) {
            float cost = 0.0f;
    
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
    


        public void sellCoffee(String coffeeType, int quantity, boolean isIced) {
            float price = getCoffeePrice(coffeeType) * quantity;
            totalSales += price;
    
            float cost = calculateInventoryCost(coffeeType, quantity);
            float profit = price - cost;
    
            Coffee coffee = new Coffee(coffeeType, quantity, price / quantity, isIced);
            coffee.createCoffee(inventory, quantity);
    
            transactionSummary.addTransaction(coffeeType, price, profit);
        }
    
        public float getCoffeePrice(String coffeeType) {
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
}


