import java.util.Scanner;

public class driver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InventoryManagement inventory = new InventoryManagement();
        pointOfSales POS = new pointOfSales(inventory);
        InventoryView imsview = new InventoryView();
        MenuView menuView = new MenuView();
        
        inventory.List_inventory();

        MenuController menuController = new MenuController(menuView, inventory, POS, imsview);
        menuView.getFrame().setVisible(true);

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
