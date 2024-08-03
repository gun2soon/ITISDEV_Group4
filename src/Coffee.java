import java.util.HashMap;
import java.util.Map;

public class Coffee {
    private String name;
    private int quantity;
    private float cost;
    private boolean isIced;

    public Coffee(String name, int quantity, float cost, boolean isIced) {
        this.name = name;
        this.quantity = quantity;
        this.cost = cost;
        this.isIced = isIced;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getCost() {
        return cost;
    }

    public boolean isIced() {
        return isIced;
    }

    public void createCoffee(InventoryManagement inventory, int quantity) {
        if (isIced) {
            // Adjust inventory for iced coffee
            switch (name) {
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
            switch (name) {
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

    public float calculatePrice(int quantity) {
        return cost * quantity;
    }

     public static class CoffeeOption {
        String name;
        boolean isIced;

        CoffeeOption(String name, boolean isIced) {
            this.name = name;
            this.isIced = isIced;
        }
    }

    private static final Map<Integer, CoffeeOption> coffeeOptions = new HashMap<>();

    static {
        coffeeOptions.put(1, new CoffeeOption("Iced Cafe Americano", true));
        coffeeOptions.put(2, new CoffeeOption("Hot Cafe Americano", false));
        coffeeOptions.put(3, new CoffeeOption("Iced Cafe Mocha", true));
        coffeeOptions.put(4, new CoffeeOption("Hot Cafe Mocha", false));
        coffeeOptions.put(5, new CoffeeOption("Iced Vanilla Macchiato", true));
        coffeeOptions.put(6, new CoffeeOption("Hot Vanilla Macchiato", false));
        coffeeOptions.put(7, new CoffeeOption("Iced Caramel Macchiato", true));
        coffeeOptions.put(8, new CoffeeOption("Hot Caramel Macchiato", false));
    }

    public static Map<Integer, CoffeeOption> getCoffeeOptions() {
        return coffeeOptions;
    }

    public static float getCoffeePrice(String coffeeType) {
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