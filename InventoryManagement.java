import java.util.ArrayList;
import java.util.Scanner;

public class InventoryManagement {
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

    public void displayInventory() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        String itemsHeader = centerString("Items", 16);
        String qtyUnitHeader = centerString("QTY/Unit", 11);
        String skuHeader = centerString("SKU", 8);
        String output = String.format("%s| %s| %s", itemsHeader, qtyUnitHeader,skuHeader);
        System.out.println(output);
        for (InventoryItem item : inventory) {
            //System.out.println(item.getName() + ": " + item.getQuantity());
            output = String.format("%-16s| %-7d%-4s| %-8d", item.getName(), item.getQuantity(), item.getUnit(), item.getSKU());
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
