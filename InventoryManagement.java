import java.util.ArrayList;
import java.util.Scanner;

public class InventoryManagement {

    public void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private ArrayList<InventoryItem> inventory;
    private int nextSKU;

    Scanner sc = new Scanner(System.in);
    
    public InventoryManagement() {
        inventory = new ArrayList<>();
        nextSKU = 1;
    }

    public void addItem(InventoryItem item) {
        for (InventoryItem existingItem : inventory) {
            if (existingItem.getName().equals(item.getName())) {
                existingItem.addQuantity(item.getQuantity());
                return;
            }
        }
        item.setSKU(nextSKU); // Set the SKU for the new item
        nextSKU++; // Increment the SKU counter
        inventory.add(item);
    }

    public void removeItem(String itemName, int quantity) {
        for (InventoryItem item : inventory) {
            if (item.getName().equals(itemName)) {
                item.reduceQuantity(quantity);
                return;
            }
        }
    }

    public int checkQuantity(String itemName) {
        for (InventoryItem item : inventory) {
            if (item.getName().equals(itemName)) {
                return item.getQuantity();
            }
        }
        return 0;
    }

    private String centerString(String text, int width) {
        int padding = (width - text.length()) / 2;
        int paddingLeft = padding;
        int paddingRight = width - text.length() - paddingLeft;
        return " ".repeat(paddingLeft) + text + " ".repeat(paddingRight);
    }

    public void inventoryMenu() {
   
        int choice = 0;
        while(choice != 3){

            System.out.println("\nInventory Management System: Ready Coffee PH! \n");

            System.out.println("Menu: \n");
            System.out.println("[1] Display Inventory");
            System.out.println("[2] Update Inventory");
            System.out.println("[3] Exit Inventory \n");

            System.out.print("Enter choice: ");
            choice = sc.nextInt(); // Prompt the user first, then read the input
            switch (choice) {
                case 1:
                    displayInventory();
                    break;
                case 2:
                    displayInventory();
                    updateInventory();
                    break;
                case 3:
                    cls();
                    System.out.print("\nExiting Inventory\n");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    public void displayInventory() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        String itemsHeader = centerString("Items", 16);
        String qtyUnitHeader = centerString("QTY/Unit", 11);
        String skuHeader = centerString("SKU", 8);
        String costHeader = centerString("Cost", 11);
        String output = String.format("%s| %s| %s| %s", itemsHeader, qtyUnitHeader,skuHeader, costHeader);
        System.out.println(output);
        for (InventoryItem item : inventory) {
            //System.out.println(item.getName() + ": " + item.getQuantity());
            output = String.format("%-16s| %-7d%-4s| %-8d| %.2f", item.getName(), item.getQuantity(), item.getUnit(), item.getSKU(), item.getCost());
            System.out.println(output);
        }
    }

    public void updateInventory(){
        int selectItem;
        boolean found = false;
        while (!found){
            System.out.print("Select a SKU from the inventory you want to update: ");
            selectItem = Integer.parseInt(sc.nextLine());
            
            for (InventoryItem item : inventory){
                if (item.getSKU() == selectItem) {
                    String output = String.format("How many %s unit/QTY you want to update: ", item.getUnit());
                    System.out.print(output);
                    int updateQuantity = Integer.parseInt(sc.nextLine());
                    item.addQuantity(updateQuantity);
                    System.out.println("Inventory updated successfully!");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Item with SKU " + selectItem + " not found.");
                System.out.print("Do you want to continue [Y/N]: ");
                String yesORno = sc.nextLine();
                if(yesORno.equalsIgnoreCase("Y")){
                    found = false;
                    System.out.println();
                }
                else{
                    found = true;
                }
            }
            else {
                displayInventory();
            }   
        }
    }
}
