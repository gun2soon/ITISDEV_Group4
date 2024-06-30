public class Milk extends InventoryItem {
    private String unit = "mL";

    public Milk(int quantity, float cost) {
        super("Milk", quantity, cost);
    }

    @Override
    public String getUnit() {
        return unit;
    }
}