public class Lids extends InventoryItem {
    private String type; // "Hot" or "Cold"

    public Lids(String type, int quantity) {
        super(type + " Lids", quantity);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}