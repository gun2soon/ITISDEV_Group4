public class CoffeeBeans extends InventoryItem {
    private String unit = "g";
    public CoffeeBeans(int quantity) {
        super("Coffee Beans", quantity);
    }
    public String getUnit() {
        return unit;
    }
}
