public class Syrup extends InventoryItem {
    private String type; // "Chocolate", "Caramel", "Vanilla", "Simple"
    private String unit = "mL";
    
    public Syrup(String type, int quantity, float cost) {
        super(type + " Syrup", quantity, cost);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getUnit() {
        return unit;
    }
}

