public class Cups extends InventoryItem {
    private String type; // "Hot" or "Cold"

    public Cups(String type, int quantity) {
        super(type + " Cups", quantity);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
