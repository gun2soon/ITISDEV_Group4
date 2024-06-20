public abstract class InventoryItem {
    private String name;
    private int quantity;
    private int SKU;
    private float cost;

    public InventoryItem(String name, int quantity, float cost) {
        this.name = name;
        this.quantity = quantity;
        this.cost = cost;
    }

    public InventoryItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getCost()
    {
        return cost;
    }

    public int getSKU() {
        return SKU;
    }

    public String getUnit() {
        return ""; // Default unit is empty
    }

    public void setSKU(int SKU) {
        this.SKU = SKU;
    }

    public void addQuantity(int amount) {
        if (amount > 0) {
            this.quantity += amount;
        }
    }

    public void reduceQuantity(int amount) {
        if (amount > 0 && amount <= this.quantity) {
            this.quantity -= amount;
        }
    }
}

