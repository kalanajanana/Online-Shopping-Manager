abstract class Product {
    private final String productID;
    private final String productName;
    private final int availableItems;
    private final double price;
    public Product(String productID, String productName, int availableItems, double price) {
        this.productID = productID;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }
    // Getters and Setters
    public String getProductId() {
            return this.productID;
    }
//    public void setProductId(String productId) {
//        this.productID = productId;
//    }

    public String getProductName() {
        return this.productName;
    }
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }

    public int getAvailableItems() {
        return this.availableItems;
    }
//    public void setAvailableItems(int availableItems) {
//        this.availableItems = availableItems;
//    }

    public double getPrice() {
        return this.price;
    }
//    public void setPrice(double price) {
//        this.price = price;
//    }
    public abstract void printProductList();
    public abstract void save();

}
