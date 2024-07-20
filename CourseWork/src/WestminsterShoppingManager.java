import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {

    final private List<Product> productList = new ArrayList<>(50);

    @Override
    public void addProduct(Product product) {
        for (Product p : productList) {
            if (p.getProductId().equals(product.getProductId())) {
                System.out.println("Product ID already exists: " + product.getProductId());
                return;
            }
        }
        productList.add(product);
    }

    @Override
    public void deleteProduct(String productID) {
        for (Product product : productList) {
            if (product.getProductId().equals(productID)) {
                productList.remove(product);
                System.out.println("Product removed: " + product.getProductName());
                System.out.println("Total number of products: " + productList.size());
                return;
            }
        }
    }

    @Override
    public void printProducts() {
        productList.sort((p1, p2) -> p1.getProductId().compareTo(p2.getProductId()));
        for (Product product : productList) {
            product.printProductList();
            System.out.println();
        }

    }

    @Override
    public void saveToFile() {
        productList.sort((p1, p2) -> p1.getProductId().compareTo(p2.getProductId()));
        for (Product product : productList) {
            product.save();
        }
    }

    @Override
    public void loadFromFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }


    public static void main(String[] args) {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        Scanner input = new Scanner(System.in);

        while (true) {
        System.out.println("Enter 1 to Add product: ");
        System.out.println("Enter 2 to Print: ");
        System.out.println("Enter 3 to Delete: ");
        System.out.println("Enter 4 to Save to File: ");
        System.out.println("Enter 5 to Load From File: ");
        System.out.println("Enter 0 to Exit the System: ");
            if (!input.hasNextInt()) {
                System.out.println("Invalid choice. Please enter a number.");
                input.next(); // Clear the invalid input
                continue;
            }

        int id = input.nextInt();
        input.nextLine();
            switch (id) {
                case 1:
                    boolean validProductType = false;
                    int productType = 0;

                    while (!validProductType) {
                        System.out.println("Enter product type (1 for Electronics, 2 for Clothing): ");
                        try {
                            productType = input.nextInt();
                            input.nextLine(); // Consume newline
                            if (productType == 1 || productType == 2) {
                                validProductType = true;
                            } else {
                                System.out.println("Invalid Input. Please enter 1 or 2.");
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid Input. Please enter an integer (1 or 2).");
                            input.next(); // Clear invalid input
                        }
                    }

                        System.out.println("Enter product ID: ");
                        String productId = input.nextLine();

                        System.out.println("Enter product name: ");
                        String productName = input.nextLine();

                        int availableItems = 0;
                        boolean validInput = false;
                        while (!validInput) {
                            try {
                                System.out.println("Enter available items: ");
                                availableItems = input.nextInt();
                                validInput = true;
                            } catch (Exception e) {
                                System.out.println("The item is not an integer.");
                                input.next();
                            }
                        }

                        double price = 0;
                        boolean pri = false;
                        while (!pri) {
                            try {
                                System.out.println("Enter price: ");
                                price = input.nextDouble();
                                pri = true;
                            } catch (Exception e) {
                                System.out.println("The price should be an integer.");
                                input.next();
                            }
                        }


                        if (productType == 1) {
                            // Electronics
                            Scanner Elecscanner = new Scanner(System.in);
                            System.out.println("Enter brand: ");
                            String brand = Elecscanner.nextLine();

                            int warrantyPeriod = 0;
                            boolean warrant = false;
                            while (!warrant) {
                                try {
                                    System.out.println("Enter warranty period (Weeks): ");
                                    warrantyPeriod = Elecscanner.nextInt();
                                    warrant = true;
                                } catch (Exception e) {
                                    System.out.println("Warranty Should be an integer");
                                    Elecscanner.next();
                                }
                            }

                            Electronics electronics = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
                            manager.addProduct(electronics);
                            break;


                        } else if (productType == 2) {
                            // Clothing
                            Scanner Clothscanner = new Scanner(System.in);
                            System.out.println("Enter size: ");
                            String size = Clothscanner.nextLine();

                            System.out.println("Enter color: ");
                            String color = Clothscanner.nextLine();

                            Clothing clothing = new Clothing(productId, productName, availableItems, price, size, color);
                            manager.addProduct(clothing);

                            break;
                        } else {
                            System.out.println("Invalid product type.");
                        }
                case 2:
                    // print to terminal
                    manager.printProducts();
                    break;
                case 3:
                    // Delete from file
                    Scanner delete = new Scanner(System.in);
                    System.out.println("Enter product ID to delete: ");
                    String productIdToDelete = delete.nextLine();
                    manager.deleteProduct(productIdToDelete);
                    break;
                case 4:
                    // Save to file
                    manager.saveToFile();
                    break;
                case 5:
                    manager.loadFromFile("Products.txt");
                    break;
                case 0:
                    System.out.println("Exiting the system.");
                    input.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
