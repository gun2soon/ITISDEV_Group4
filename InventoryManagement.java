import java.util.ArrayList;

public class InventoryManagement {
    private ArrayList<InventoryItem> inventory;

    public InventoryManagement() {
        inventory = new ArrayList<>();
    }

    public void addItem(InventoryItem item) {
        for (InventoryItem existingItem : inventory) {
            if (existingItem.getName().equals(item.getName())) {
                existingItem.addQuantity(item.getQuantity());
                return;
            }
        }
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

    public void displayInventory() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (InventoryItem item : inventory) {
            System.out.println(item.getName() + ": " + item.getQuantity());
        }
    }
}
