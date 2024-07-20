import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;

class ShoppingCart extends JFrame {
    JTable cartTable;
    JScrollPane cartScroll;
    String[] cartCol = {"Product ID", "Quantity", "Price(£)"};
    Object[][] cartData;
    JLabel totalLabel, FinaldiscountLabel, discount10Label, totalLabel1, discount10Label1, FinaldiscountLabel1, discount20Label, discount20Label2;
    JPanel total, items;

    //use LinkedHashMap to add products respectively
    ShoppingCart(LinkedHashMap<String, Integer> cartItems, LinkedHashMap<String, Double> cartPrices, LinkedHashMap<String, String> productCategories) {
        // Convert cart details to table data format
        cartData = new Object[cartItems.size()][4];
        int i = 0;
        double totalPrice = 0;
        LinkedHashMap<String, Integer> categoryCounts = new LinkedHashMap<>();

        for (String productId : cartItems.keySet()) {
            int quantity = cartItems.get(productId);
            double price = cartPrices.get(productId) * quantity;
            String category = productCategories.get(productId);
            cartData[i][0] = productId;
            cartData[i][1] = quantity;
            cartData[i][2] = price; // Total price for each item
            cartData[i][3] = category; // category
            totalPrice += price;
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + quantity);
            i++;
        }

        // Get the first product Price according to the product id
        String firstProductId = cartItems.keySet().iterator().next();
        double firstProductPrice = cartPrices.get(firstProductId);

        // Apply discounts
        double discount2 =0;
        for (String category : categoryCounts.keySet()) {
            if (categoryCounts.get(category) >= 3) {
                discount2 = totalPrice * 0.2;
            }
        }
        double discount10 = firstProductPrice*0.10;

        double finalPrice = totalPrice - (discount10 + discount2);

        items = new JPanel(new BorderLayout());

        DecimalFormat dformat = new DecimalFormat("0.00");

        // Create cart table
        cartTable = new JTable(cartData, cartCol);
        cartScroll = new JScrollPane(cartTable);

        // Set grid lines and color
        cartTable.setShowGrid(true);
        cartTable.setGridColor(Color.GRAY);

        JTableHeader tableHeader = cartTable.getTableHeader();
        Font Font1 = new Font("sansSerif",Font.BOLD,16);
        tableHeader.setFont(Font1);
        // Center Table items
        // Custom header renderer
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setFont(Font1);
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        };
        tableHeader.setDefaultRenderer(headerRenderer);

        // Create total price label
        total = new JPanel(new GridLayout(4, 2,30,20));

        totalLabel = new JLabel("Total Price: ");
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        totalLabel1 = new JLabel(dformat.format(totalPrice)+"£");

        discount10Label = new JLabel("First Purchase Discount (10%): ");
        discount10Label.setHorizontalAlignment(SwingConstants.RIGHT);
        discount10Label1 = new JLabel( dformat.format(discount10)+"£");

        discount20Label = new JLabel("Three items in same Category Discount (20%): ");
        discount20Label.setHorizontalAlignment(SwingConstants.RIGHT);
        discount20Label2 = new JLabel( dformat.format(discount2)+"£");

        Font Font2 = new Font("sansSerif", Font.BOLD, 16);
        FinaldiscountLabel = new JLabel("Final Price: ");
        FinaldiscountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        FinaldiscountLabel1 = new JLabel(dformat.format(finalPrice)+"£");
        FinaldiscountLabel.setFont(Font2);
        FinaldiscountLabel1.setFont(Font2);

        total.add(totalLabel);
        total.add(totalLabel1);
        total.add(discount10Label);
        total.add(discount10Label1);
        total.add(discount20Label);
        total.add(discount20Label2);
        total.add(FinaldiscountLabel);
        total.add(FinaldiscountLabel1);

        items.add(total, BorderLayout.EAST);

        // Add components to the frame
        this.setLayout(new GridLayout(2, 0));
        this.add(cartScroll);
        this.add(items, BorderLayout.EAST);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
