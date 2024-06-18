public class CoffeeBeans extends InventoryItem {
    private String unit = "g";
    public CoffeeBeans(int quantity, float price) {
        super("Coffee Beans", quantity, price);
    }

    @Override
    public String getUnit() {
        return unit;
    }
}
