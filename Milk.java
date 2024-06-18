public class Milk extends InventoryItem {
    private String unit = "mL";

    public Milk(int quantity) {
        super("Milk", quantity);
    }

    public String getUnit() {
        return unit;
    }
}
