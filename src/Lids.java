public class Lids extends InventoryItem {
    private String type; // "Hot" or "Cold"
    private String unit = "pcs";
    public Lids(String type, int quantity, float cost) {
        super(type + " Lids", quantity, cost);
        this.type = type;
    }

    public Lids(String type, int quantity) {
        super(type + " Lids", quantity);
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
        return "lids";
    }
}