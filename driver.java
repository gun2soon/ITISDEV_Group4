import java.util.Scanner;

public class driver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InventoryManagement inventory = new InventoryManagement();

        // Add items to inventory
        inventory.addItem(new CoffeeBeans(100));
        inventory.addItem(new Syrup("Chocolate", 50));
        inventory.addItem(new Syrup("Caramel", 50));
        inventory.addItem(new Syrup("Vanilla", 50));
        inventory.addItem(new Syrup("Simple", 50));
        inventory.addItem(new Milk(200));
        inventory.addItem(new Cups("Hot", 100));
        inventory.addItem(new Cups("Cold", 100));
        inventory.addItem(new Lids("Hot", 100));
        inventory.addItem(new Lids("Cold", 100));

        System.out.println("Menu: \n");
        System.out.println("[1] Display Inventory");
        System.out.println("[2] Update Inventory");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();  // Prompt the user first, then read the input

        switch (choice) {
            case 1:
                inventory.displayInventory();
                break;
            case 2:
                // You can implement the updateInventory method and call it here
                // inventory.updateInventory();
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }

        // Use some items
        inventory.removeItem("Coffee Beans", 10);
        inventory.removeItem("Milk", 20);
        inventory.removeItem("Chocolate Syrup", 10);
        inventory.removeItem("Caramel Syrup", 5);

        // Check specific item quantity
        System.out.println("Coffee Beans left: " + inventory.checkQuantity("Coffee Beans") + "g");
        System.out.println("Milk left: " + inventory.checkQuantity("Milk") + " mL");
        System.out.println("Chocolate Syrup left: " + inventory.checkQuantity("Chocolate Syrup") + " mL");
        System.out.println("Caramel Syrup left: " + inventory.checkQuantity("Caramel Syrup") + " mL");

        sc.close();
    }
}
