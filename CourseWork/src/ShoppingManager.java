interface ShoppingManager {
    void addProduct(Product product);
    void deleteProduct(String productID);
    void printProducts();
    void saveToFile();
    void loadFromFile(String fileName);
}
