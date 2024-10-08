import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class pointOfSales {
    private InventoryManagement inventory;
    private float totalSales;
    private boolean applyDiscount;
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
        if (basket.contains(item)) {
            int index = basket.indexOf(item);
            System.out.println("Item already in basket.");
            SaleItem existingItem = basket.get(index);
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            basket.add(item);
        }
        updateDiscounts();
    }

    public void deleteFromBasket(int itemIndex) {
        basket.remove(itemIndex);
        updateDiscounts();
    }

    public List<SaleItem> getBasket() {
        return basket;
    }

    public double getTotalBasketPrice() {
        double total = 0.0;
        for (SaleItem item : basket) {
            total += item.getPrice();
        }
        return total;
    }

    public double getDiscountedPrice() {
        double discount = 0.0;
        for (SaleItem item : basket) {
            discount += item.getPiecePrice() * item.getDiscount();
        }
        return discount;
    }

    public String getDiscountedItem(){
        String discountedItem = "";
        for (SaleItem item : basket) {
            if (item.getDiscount() > 0.0) {
                discountedItem = item.getCoffeeType();
            }
        }
        return discountedItem;
    }
    

    public void setDiscount(int itemIndex, double discount) {
        SaleItem item = basket.get(itemIndex);
        item.setDiscount(discount);
    }

    public void updateDiscounts() {
        if (applyDiscount) {
            double maxPrice = 0.0;
            int maxPriceIndex = -1;
            for (int i = 0; i < basket.size(); i++) {
                SaleItem item = basket.get(i);
                if (item.getPiecePrice() > maxPrice) {
                    maxPrice = item.getPiecePrice();
                    maxPriceIndex = i;
                }
            }
            for (int i = 0; i < basket.size(); i++) {
                SaleItem item = basket.get(i);
                if (i == maxPriceIndex) {
                    item.setDiscount(0.2);
                } else {
                    item.setDiscount(0.0);
                }
            }
        } else {
            for (SaleItem item : basket) {
                item.setDiscount(0.0);
            }
        }
    }

    public void setMaxPriceDiscount(boolean applyDiscount) {
        this.applyDiscount = applyDiscount;
        updateDiscounts();
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
                transactionSummary.addTransaction(item.getQuantity(), item.getCoffeeType(), cost, profit);
                sellCoffee(item.getCoffeeType(), item.getQuantity(), item.isIced());
            }
            basket.clear();
        }
    }

    public void addTransaction(int quantity, String coffeeType, float cost, float profit) {
        transactionSummary.addTransaction(quantity, coffeeType, cost, profit);
    }

    public void clearBasket() {
        basket.clear();
    }

    public void sellCoffee(String coffeeType, int quantity, boolean isIced) {
        float price = getCoffeePrice(coffeeType) * quantity;
        totalSales += price;

        float profit = (float) (price * 0.7);

        Coffee coffee = new Coffee(coffeeType, quantity, price / quantity, isIced);
        coffee.createCoffee(inventory, quantity);

        transactionSummary.addTransaction(quantity, coffeeType, price, profit);
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

    public List<transactionSummary.Transaction> getTransactions() {
        return transactionSummary.getTransactions();
    }

    // get transactions based on given dates span
    public List<transactionSummary.Transaction> getTransactions(Date startDate, Date endDate) {
        return transactionSummary.getTransactions(startDate, endDate);
    }
}

