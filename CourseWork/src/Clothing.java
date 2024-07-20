import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Clothing extends Product{
    private final String size;
    private final String color;
    // Constructor to initialize Clothing object
    public Clothing(String productID, String productName, int availableItems, double price, String size, String color) {
        super(productID, productName, availableItems, price);
        this.size = size;
        this.color = color;
    }
    // Getters and Setters
    public String getSize() {
        return this.size;
    }
//    public void setSize(String size) {
//        this.size = size;
//    }
    public String getColor() {
        return this.color;
    }
//    public void setColor(String color) {
//        this.color = color;
//    }

    public void printProductList(){
        System.out.println("Clothes Details:- \nProduct ID is:- "+ this.getProductId() + "\nProduct Name is:- "+this.getProductName()
                + "\nAvailable items:- "+ this.getAvailableItems() + "\nPrice:- "+ this.getPrice()+"£" + "\nSize:- " + this.getSize()+"\nColour :- " + this.getColor());
    }

    @Override
    public void save() {
        try {
            FileWriter save = new FileWriter("Products.txt",true);
            FileWriter save1 = new FileWriter("Cloth.txt",true);
            FileWriter saveAll = new FileWriter("All.txt",true);

            Writer output = new BufferedWriter(save);
            Writer output1 = new BufferedWriter(save1);
            Writer outputAll = new BufferedWriter(saveAll);

            // print to 3 files
            output.write("Clothes Details:- \nProduct ID is:- "+ getProductId() + "\nProduct Name is:- "+getProductName()
                        + "\nAvailable items:- "+ getAvailableItems() + "\nPrice:- "+ getPrice()+"£" + "\nSize:- " + getSize()+"\nColour :- " + getColor()+ "\n\n");
            output1.write(getProductId() + "-"+getProductName() + "-"+ "Clothing" + "-"+ getPrice() + "-" + getSize()+"," + getColor()+"-" + getSize()+"-" + getColor()+"-" + getAvailableItems()+"\n");
            outputAll.write(getProductId() + "-"+getProductName() + "-"+ "Clothing" + "-"+ getPrice() + "-" + getSize()+"," + getColor()+"-" + getSize()+"-" + getColor()+"-" + getAvailableItems()+ "\n");

            output.close();
            output1.close();
            outputAll.close();
            System.out.println("Saved Successfully.");
        }catch (IOException e){
            System.out.println("An Error Occurred.");
        }
    }
}
