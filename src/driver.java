import java.util.Scanner;
//import java.sql.*;

public class driver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InventoryManagement inventory = new InventoryManagement();
        pointOfSales POS = new pointOfSales(inventory);

        // Add items to inventory
        // inventory.addItem(new CoffeeBeans(1000, 2200));
        // inventory.addItem(new Syrup("Chocolate", 750, 550));
        // inventory.addItem(new Syrup("Caramel", 750, 550));
        // inventory.addItem(new Syrup("Vanilla", 750, 550));
        // inventory.addItem(new Syrup("Simple", 750, 550));
        // inventory.addItem(new Milk(1000, 110));
        // inventory.addItem(new Cups("16 oz Hot", 100, 860));
        // inventory.addItem(new Cups("16 oz Cold", 100, 350));
        // inventory.addItem(new Lids("Lid: Hot", 100, 100));
        // inventory.addItem(new Lids("Lid: Cold", 100, 140));
        // inventory.addItem(new Straws(100, 100));
        inventory.List_inventory();
        
        int choice = 0;
        while (choice != 3) {
            System.out.println("\nPoint of Sales & Inventory Management System: Ready Coffee PH! \n");
            
            System.out.println("Menu: \n");
            System.out.println("[1] Ready Coffee POS");
            System.out.println("[2] Access Inventory");
            System.out.println("[3] Exit Program \n");

            System.out.print("Enter choice: ");
            choice = sc.nextInt(); // Prompt the user first, then read the input
            switch (choice) {
                case 1:
                    POS.POSMenu();
                    break;
                case 2:
                    inventory.inventoryMenu();
                    break;
                case 3:
                    inventory.saveInventoryToDatabase();
                    System.out.print("\nExiting Program\n");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
        sc.close();
    }
    
}