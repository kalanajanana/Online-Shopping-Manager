import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
public class Electronics extends Product {
    private final String brand;
    private final int warrantyPeriod;

    public Electronics(String productID, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        super(productID, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    //Getters and Setters
    public String getBrand() {
        return this.brand;
    }

    public int getWarrantyPeriod() {
        return this.warrantyPeriod;
    }

//    public void setBrand(String brand) {
//        this.brand = brand;
//    }

//    public void setWarrantyPeriod(int warrantyPeriod) {
//        this.warrantyPeriod = warrantyPeriod;
//    }

    public void printProductList() {
        System.out.println("Electronics Details:- \nProduct ID is:- " + this.getProductId() + "\nProduct Name is:- " + this.getProductName()
                    + "\nAvailable items:- " + this.getAvailableItems() + "\nPrice:- " + this.getPrice()+"£" + "\nBrand:- " + this.getBrand() + "\nWarranty Period:- "
                    + this.getWarrantyPeriod() + " Weeks");
    }
    @Override
    public void save() {
        try {
            FileWriter save = new FileWriter("Products.txt",true);
            FileWriter save1 = new FileWriter("Electronics.txt",true);
            FileWriter saveAll = new FileWriter("All.txt",true);

            Writer output = new BufferedWriter(save);
            Writer output1 = new BufferedWriter(save1);
            Writer outputAll = new BufferedWriter(saveAll);

            // print to 3 files
            output.write("Electronics Details:- \nProduct ID is:- " + this.getProductId() + "\nProduct Name is:- " + this.getProductName()
                        + "\nAvailable items:- " + this.getAvailableItems() + "\nPrice:- " + this.getPrice()+"£" + "\nBrand:- " + this.getBrand() + "\nWarranty Period:- "
                        + this.getWarrantyPeriod() + " Weeks\n\n");
            output1.write(this.getProductId() +"-" +this.getProductName() +"-"+"Electronics"+ "-" + this.getPrice() +"-"+ this.getBrand()+", "+ this.getWarrantyPeriod() + " Weeks warranty"+ "-"+getWarrantyPeriod()+"-"+getBrand()+"-"+getAvailableItems()+"\n");
            outputAll.write(this.getProductId() +"-" +this.getProductName() +"-"+"Electronics"+ "-" + this.getPrice() +"-"+ this.getBrand()+", "+ this.getWarrantyPeriod() + " Weeks warranty"+"-"+getWarrantyPeriod()+"-"+ getBrand()+"-"+getAvailableItems()+"\n");

            output.close();
            output1.close();
            outputAll.close();
            System.out.println("Saved Successfully.");
        }catch (IOException e){
            System.out.println("An Error Occurred.");
        }
    }
}
