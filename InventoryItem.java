public abstract class InventoryItem {
    private String name;
    private int quantity;
    private int SKU;
    private int price;

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

<<<<<<< Updated upstream
=======
    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

>>>>>>> Stashed changes
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

    public boolean reduceQuantity(int amount) {
        if (amount > 0 && amount <= this.quantity) {
            float proportion = (float) amount / this.quantity;
            this.quantity -= amount;
            this.cost -= this.cost * proportion;
            return true;
        } else if (amount > 0 && amount > this.quantity) {
            this.quantity = 0;
            this.cost = 0;
            return true;
        }
        return false;
    }
}

