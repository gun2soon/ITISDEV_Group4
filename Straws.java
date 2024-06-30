public class Straws extends InventoryItem {
    private String unit = "pcs";
    public Straws(int quantity, float price) {
        super("Straws", quantity, price);
    }
    
    @Override
    public String getUnit() {
        return unit;
    }
}