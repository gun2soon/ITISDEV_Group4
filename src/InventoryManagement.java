import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class InventoryManagement {
    private ArrayList<InventoryItem> inventory;
    private int nextSKU;
    private Scanner sc;

    private List<CoffeeBeans> coffeebeansList = new ArrayList<>();
    private List<Cups> cupsList = new ArrayList<>();
    private List<Lids> lidsList = new ArrayList<>();
    private List<Milk> milkList = new ArrayList<>();
    private List<Straws> strawsList = new ArrayList<>();
    private List<Syrup> syrupList = new ArrayList<>();

    public InventoryManagement() {
        inventory = new ArrayList<>();
        nextSKU = 1;
        sc = new Scanner(System.in);
    }

    public void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * @return
     */

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

    public void addItem(InventoryItem item) {
        if (item == null) {
            System.out.println("Cannot add null item.");
            return;
        }
    
        for (InventoryItem existingItem : inventory) {
            if (existingItem.getName().equals(item.getName())) {
                existingItem.addQuantity(item.getQuantity());
                return;
            }
        }
        //item.setSKU(nextSKU); // Set the SKU for the new item
        // nextSKU++; // Increment the SKU counter
        inventory.add(item);
    
        if (item instanceof CoffeeBeans) {
            coffeebeansList.add((CoffeeBeans) item);
        }else if (item instanceof Cups) {
            cupsList.add((Cups) item);
        }else if (item instanceof Lids) {
            lidsList.add((Lids) item);
        }else if (item instanceof Milk) {
            milkList.add((Milk) item);
        }else if (item instanceof Straws) {
            strawsList.add((Straws) item);
        }else if (item instanceof Syrup) {
            syrupList.add((Syrup) item);
        }
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

    public void displayInventory() {
        // System.out.print("\033[H\033[2J");
        // System.out.flush();
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

    //Added code 
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/inventory?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "YES";

    public int List_inventory() {
        Connection conn = null;
        try {
            // 1. Connect to our database
            Class.forName("com.mysql.cj.jdbc.Driver");    
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            System.out.println("Connected successfully");
    
            // 2. Prepare our SQL Statement of coffeebeans database
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM coffeebeans");
            ResultSet rst = pstmt.executeQuery();
    
            coffeebeansList.clear();
    
            // Fetch all rows from the result set
            while (rst.next()) {
                int id = rst.getInt("idCoffeeBeans");
                float price = rst.getFloat("cost");
                int quantity = rst.getInt("quantity");
    
                CoffeeBeans coffeeBean = new CoffeeBeans(quantity, price);
                coffeeBean.setSKU(id);
                addItem(coffeeBean); // Use addItem to add to both inventory and coffeebeansList
            }

            // 3. Prepare our SQL Statement of cups database
            pstmt = conn.prepareStatement("SELECT * FROM cups");
            rst = pstmt.executeQuery();
    
            // Fetch all rows from the result set
            while (rst.next()) {
                int id = rst.getInt("idCups");
                float price = rst.getFloat("cost");
                int quantity = rst.getInt("quantity");
                String type = rst.getString("type");
    
                Cups cup = new Cups(type, quantity, price);
                cup.setSKU(id);
                addItem(cup); // Use addItem to add to both inventory and cupList
            }

            // 4. Prepare our SQL Statement of lids database
            pstmt = conn.prepareStatement("SELECT * FROM lids");
            rst = pstmt.executeQuery();
    
            // Fetch all rows from the result set
            while (rst.next()) {
                int id = rst.getInt("idLids");
                float price = rst.getFloat("cost");
                int quantity = rst.getInt("quantity");
                String type = rst.getString("type");
    
                Lids lid = new Lids(type, quantity, price);
                lid.setSKU(id);
                addItem(lid); // Use addItem to add to both inventory and lidsList
            }

            // 5. Prepare our SQL Statement of milk database
            pstmt = conn.prepareStatement("SELECT * FROM milk");
            rst = pstmt.executeQuery();
    
            // Fetch all rows from the result set
            while (rst.next()) {
                int id = rst.getInt("idMilk");
                float price = rst.getFloat("cost");
                int quantity = rst.getInt("quantity");
    
                Milk milk = new Milk(quantity, price);
                milk.setSKU(id);
                addItem(milk); // Use addItem to add to both inventory and milkList
            }

            // 6. Prepare our SQL Statement of straws database
            pstmt = conn.prepareStatement("SELECT * FROM straws");
            rst = pstmt.executeQuery();
    
            // Fetch all rows from the result set
            while (rst.next()) {
                int id = rst.getInt("idStraws");
                float price = rst.getFloat("cost");
                int quantity = rst.getInt("quantity");
    
                Straws straw = new Straws(quantity, price);
                straw.setSKU(id);
                addItem(straw); // Use addItem to add to both inventory and strawsList
            }
    
            // 7. Prepare our SQL Statement of syrup database
            pstmt = conn.prepareStatement("SELECT * FROM syrup");
            rst = pstmt.executeQuery();
    
            // Fetch all rows from the result set
            while (rst.next()) {
                int id = rst.getInt("idSyrup");
                float price = rst.getFloat("cost");
                int quantity = rst.getInt("quantity");
                String type = rst.getString("type");
    
                Syrup syrup = new Syrup(type, quantity, price);
                syrup.setSKU(id);
                addItem(syrup); // Use addItem to add to both inventory and syrupList
            }

            pstmt.close();
            conn.close();
            
            // Display the coffee beans details
            for (CoffeeBeans coffeeBean : coffeebeansList) {
                System.out.println(coffeeBean);
            }
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }      

    public void saveInventoryToDatabase() {
        Connection conn = null;
        try {
            // Connect to our database
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcURL = "jdbc:mysql://localhost:3306/inventory?useSSL=false&serverTimezone=UTC";
            conn = DriverManager.getConnection(jdbcURL, "root", "YES");
            System.out.println("Connected successfully");
    
            // Prepare our SQL Statement
            // Iterate through inventory and update the database
            for (InventoryItem item : inventory) {
                String tableName = item.getTableName();
                String updateSQL = "UPDATE " + tableName + " SET cost = ?, quantity = ? WHERE id" + capitalizeFirstLetter(tableName) + " = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                    pstmt.setFloat(1, item.getCost());
                    pstmt.setInt(2, item.getQuantity());
                    pstmt.setInt(3, item.getSKU());
                    pstmt.executeUpdate();
                }
            }
    
            
            conn.close();
            System.out.println("Inventory saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public ArrayList<InventoryItem> getInventory() {
        return inventory;
    }

    // public static void main(String[] args) {
    //     InventoryManagement inventoryManagement = new InventoryManagement();
    //     inventoryManagement.List_inventory();

    //     inventoryManagement.displayInventory();
    // }
    
}
