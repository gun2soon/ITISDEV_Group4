import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class InventoryManagement {
    private ArrayList<InventoryItem> inventory;
    private int nextSKU;
    private Scanner sc;

    public InventoryManagement() {
        inventory = new ArrayList<>();
        nextSKU = 1;
        sc = new Scanner(System.in);
    }

    public void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
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
        Iterator<InventoryItem> iterator = inventory.iterator();
        while (iterator.hasNext()) {
            InventoryItem item = iterator.next();
            if (item.getName().equals(itemName)) {
                boolean success = item.reduceQuantity(quantity);
                if (success && item.getQuantity() == 0) {
                    iterator.remove();
                }
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

    public float getItemCost(String itemName) {
        for (InventoryItem item : inventory) {
            if (item.getName().equals(itemName)) {
                return item.getCost();
            }
        }
        return 0.0f; // Return 0 if item not found
    }

    public void inventoryMenu() {
        int choice = 0;
        while(choice != 2){

            System.out.println("\nInventory Management System: Ready Coffee PH! \n");

            displayInventory();

            System.out.println("Menu: \n");
            System.out.println("[1] Update Inventory");
            System.out.println("[2] Exit Inventory \n");

            System.out.print("Enter choice: ");
            choice = sc.nextInt(); // Prompt the user first, then read the input
            sc.nextLine();
            switch (choice) {
                case 1:
                    updateInventory();
                    break;
                case 2:
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

    public void updateInventory() {
        int selectItem;
        boolean found = false;
        while (!found) {
            System.out.print("Select a SKU from the inventory you want to update: ");
            selectItem = Integer.parseInt(sc.nextLine());
            
            for (InventoryItem item : inventory) {
                if (item.getSKU() == selectItem) {
                    System.out.print("How many units/QTY you want to add: ");
                    int updateQuantity = Integer.parseInt(sc.nextLine());
    
                    System.out.print("Enter the cost of the added quantity: ");
                    float costOfAddedQuantity = Float.parseFloat(sc.nextLine());
    
                    // Update quantity and cost
                    item.addQuantity(updateQuantity);
                    item.setCost(item.getCost() + costOfAddedQuantity);
    
                    System.out.println("Inventory updated successfully!");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Item with SKU " + selectItem + " not found.");
                System.out.print("Do you want to continue [Y/N]: ");
                String yesORno = sc.nextLine();
                if (yesORno.equalsIgnoreCase("Y")) {
                    found = false;
                    System.out.println();
                } else {
                    found = true;
                }
            } else {
                return;
            }
        }
    }
    
    
    
}
