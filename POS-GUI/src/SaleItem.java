public class SaleItem {
    private String coffeeType;
    private int quantity;
    private float price;
    private boolean isIced;

    public SaleItem(String coffeeType, int quantity, float price, boolean isIced) {
        this.coffeeType = coffeeType;
        this.quantity = quantity;
        this.price = price;
        this.isIced = isIced;
    }

    public String getCoffeeType() {
        return coffeeType;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price * quantity;
    }

    public boolean isIced() {
        return isIced;
    }

    @Override
    public String toString() {
        return quantity + " " + coffeeType + "(s)";
    }
}