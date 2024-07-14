import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class connectors{
    
    public int coffeebeans_id;
    public int cups_id;
    public int lids_id;
    public int milk_id;
    public int straws_id;
    public int syrup_id; // Added declaration for eventcode
    public String contributions;
    public int amounts;
    public String contributionStatus; // Added declaration for status

    public ArrayList<String> coffeebeansList = new ArrayList<>();
    public ArrayList<String> cupsList = new ArrayList<>();
    public ArrayList<String> lidsList = new ArrayList<>();
    public ArrayList<String> milkList = new ArrayList<>();
    public ArrayList<String> strawsList = new ArrayList<>();
    public ArrayList<String> syrupList = new ArrayList<>();
    
    public connectors(){}

    public int List_coffeebeans() {
        try {
            // 1. Connect to our database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "root", "12345");
            System.out.println("Connected successfully");

            // 2. Prepare our SQL Statement
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM coffeebeans");
            ResultSet rst = pstmt.executeQuery();

            coffeebeansList.clear();

            // Fetch all rows from the result set
            while (rst.next()) {
                int id = rst.getInt("idCoffeeBeans");
                float price = rst.getFloat("price");
                int quantity = rst.getInt("quantity");
                String unit = rst.getString("unit");

                CoffeeBeans coffeeBean = new CoffeeBeans(quantity, price);
                coffeeBean.setSKU(id);
                coffeebeansList.add(coffeeBean);
            }

            pstmt.close();
            conn.close();

            // Display the coffee beans details
            for (CoffeeBeans coffeeBean : coffeebeansList) {
                System.out.println(coffeeBean);
            }
            InventoryManagement inventoryManagement = new InventoryManagement();
            inventoryManagement.List_coffeebeans();
            return 1;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }

    public int List_cups() {
        try {
            // 1. Connect to our database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "root", "12345");
            System.out.println("Connected successfully");

            // 2. Prepare our SQL Statement
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM coffeebeans");
            ResultSet rst = pstmt.executeQuery();

            coffeebeansList.clear();

            // Fetch all rows from the result set
            while (rst.next()) {
                int id = rst.getInt("idCoffeeBeans");
                float price = rst.getFloat("price");
                int quantity = rst.getInt("quantity");
                String unit = rst.getString("unit");

                CoffeeBeans coffeeBean = new CoffeeBeans(quantity, price);
                coffeeBean.setSKU(id);
                coffeebeansList.add(coffeeBean);
            }

            pstmt.close();
            conn.close();

            // Display the coffee beans details
            for (CoffeeBeans coffeeBean : coffeebeansList) {
                System.out.println(coffeeBean);
            }
            InventoryManagement inventoryManagement = new InventoryManagement();
            inventoryManagement.List_coffeebeans();
            return 1;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }

}