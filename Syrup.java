public class Syrup extends InventoryItem {
    private String type; // "Chocolate", "Caramel", "Vanilla", "Simple"
    private String unit = "mL";
    
    public Syrup(String type, int quantity) {
        super(type + " Syrup", quantity);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getUnit() {
        return unit;
    }
}

