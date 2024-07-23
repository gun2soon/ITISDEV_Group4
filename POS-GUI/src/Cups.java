public class Cups extends InventoryItem {
    private String type; // "Hot" or "Cold"
    private String unit = "pcs";

    public Cups(String type, int quantity, float price) {
        super(type + " Cups", quantity, price);
        this.type = type;
    }

    public Cups(String type, int quantity) {
        super(type + " Cups", quantity);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getUnit() {
        return unit;
    }

    public String getTableName() {
        return "cups";
    }
}