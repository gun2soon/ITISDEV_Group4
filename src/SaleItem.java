public class SaleItem {
    private String coffeeType;
    private int quantity;
    private float price;
    private boolean isIced;
    private double discount;

    public SaleItem(String coffeeType, int quantity, float price, boolean isIced, double discount) {
        this.coffeeType = coffeeType;
        this.quantity = quantity;
        this.price = price;
        this.isIced = isIced;
        this.discount =  discount;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
    @Override
    public String toString() {
        return quantity + " " + coffeeType + "(s)";
    }
}