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

    public float getPiecePrice() {
        return price;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return quantity + " " + coffeeType + "(s)";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SaleItem) {
            SaleItem other = (SaleItem) obj;
            return coffeeType.equals(other.coffeeType) && (isIced == other.isIced);
        }
        return false;
    }
}