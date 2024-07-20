import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class GUI extends JFrame implements ActionListener {

    JScrollPane Scroll;
    JTable table;
    String[] col;
    Object[][] data;
    JLabel Category, productIdLabel, nameLabel, categoryLabel, sizeLable, colorLabel, headerLabel, items;
    JComboBox<String> comboBox;
    JPanel comboPanel, buttonPanel, mainPanel1, addPanel, ComboxCatePanelB, ComboxCatePanelG, shoppingPanel, centerPanel, detailsPanel, emptyPanel, addCartPanel;
    JButton ShoppingCart, Add;

    // Maps to store cart details
    LinkedHashMap<String, Integer> cartItems; // Product ID -> Quantity
    LinkedHashMap<String, Double> cartPrices; // Product ID -> Price
    LinkedHashMap<String, String> productCategories;
    int selectedRowIndex = -1; // To track selected row

    GUI(){

        cartItems = new LinkedHashMap<>();
        cartPrices = new LinkedHashMap<>();
        productCategories = new LinkedHashMap<>();

        String[] cate = {"All","Electronics","Clothing"};
        //table
        col = new String[]{ "Product ID" , "Name" , "Category" , "Price(£)" , "Info", "Size", "Colour", "Items Available"};
        data = getData("All.txt");

        // set table for non-editable
        DefaultTableModel model = new DefaultTableModel(data, col) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // all cells false
            }
        };
        table = new JTable(model);

        // Set grid lines and color
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        
        // Access the header
        JTableHeader tableHeader = table.getTableHeader();
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

        // Center Table items
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.setRowHeight(30);

        // Custom renderer for Product ID column
        DefaultTableCellRenderer productIdRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int itemsAvailable = Integer.parseInt(data[table.convertRowIndexToModel(row)][7].toString());
                if (itemsAvailable <= 3) {
                    cellComponent.setBackground(Color.RED);
                } else {
                    cellComponent.setBackground(Color.WHITE);
                }
                if (row == selectedRowIndex) {
                    cellComponent.setBackground(Color.BLUE);
                    cellComponent.setForeground(Color.WHITE);
                } else {
                    cellComponent.setForeground(Color.BLACK);
                }
                return cellComponent;
            }
        };

        table.getColumnModel().getColumn(0).setCellRenderer(productIdRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(productIdRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(productIdRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(productIdRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(productIdRenderer);

        // Hide the "Info" column (column index 4)
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn sizeColumn = columnModel.getColumn(5);
        TableColumn colorColumn = columnModel.getColumn(6);
        TableColumn itemColumn = columnModel.getColumn(7);
        columnModel.removeColumn(sizeColumn);
        columnModel.removeColumn(colorColumn);
        columnModel.removeColumn(itemColumn);

        Scroll = new JScrollPane(table);

        // Create a comboBox
        Category = new JLabel("Select Product Category");
        comboBox = new JComboBox<>(cate);
        // Top main panel
        comboPanel = new JPanel(new GridLayout(1,2,10,5));
        shoppingPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // For Selected items and combobox panel
        ComboxCatePanelB = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // New panel to Divide those 2 in to 2 columns
        ComboxCatePanelG = new JPanel(new GridLayout(1,2,10,15));
        // Add Those 2 to panel
        ComboxCatePanelG.add(Category);
        ComboxCatePanelG.add(comboBox);
        // add grid panel to flowlayout
        ComboxCatePanelB.add(ComboxCatePanelG);
        comboBox.addActionListener(this);

        centerPanel = new JPanel(new BorderLayout(0,40));
        centerPanel.add(Scroll);

        //Shopping Cart Button
        ShoppingCart = new JButton("Shopping Cart");
        ShoppingCart.addActionListener(this);
        buttonPanel = new JPanel(new FlowLayout());

        // add flow layout to top main panel
        comboPanel.add(ComboxCatePanelB,BorderLayout.CENTER);
        shoppingPanel.add(ShoppingCart);
        comboPanel.add(shoppingPanel,BorderLayout.WEST);

        //Add to cart Button
        Add = new JButton("Add to Shopping Cart");
        Add.addActionListener(this);
        addPanel = new JPanel(new FlowLayout());
        addPanel.add(Add);

        // Product Details Panel
        Font header = new Font("sansSerif", Font.BOLD, 12);
        emptyPanel = new JPanel(new BorderLayout());
        addCartPanel = new JPanel(new BorderLayout());
        detailsPanel = new JPanel(new GridLayout(8, 2, 0, 5));
        productIdLabel = new JLabel();
        nameLabel = new JLabel();
        categoryLabel = new JLabel();
        sizeLable = new JLabel();
        colorLabel = new JLabel();
        items = new JLabel();
        JLabel headerLabel = new JLabel("Selected Product - Details");
        headerLabel.setFont(header);
        detailsPanel.add(headerLabel);
        detailsPanel.add(productIdLabel);
        detailsPanel.add(categoryLabel);
        detailsPanel.add(nameLabel);
        detailsPanel.add(sizeLable);
        detailsPanel.add(colorLabel);
        detailsPanel.add(items);
        emptyPanel.add(detailsPanel,BorderLayout.NORTH);
        emptyPanel.add(addCartPanel,BorderLayout.SOUTH);

        // Main Panel
        mainPanel1 = new JPanel(new BorderLayout(0,20));
        mainPanel1.add(comboPanel,BorderLayout.NORTH);
        mainPanel1.add(emptyPanel, BorderLayout.SOUTH);
        addCartPanel.add(addPanel,BorderLayout.SOUTH);
        mainPanel1.add(centerPanel);

        this.add(mainPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add Mouse Listener to table
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                selectedRowIndex = table.getSelectedRow();
                table.repaint();
                if(Objects.equals(table.getValueAt(selectedRow, 2).toString(), "Electronics")){
                    if(selectedRow != -1){
                        productIdLabel.setText("Product ID: "+ table.getValueAt(selectedRow, 0).toString());
                        nameLabel.setText("Name: "+table.getValueAt(selectedRow, 1).toString());
                        categoryLabel.setText("Category: "+table.getValueAt(selectedRow, 2).toString());
                        sizeLable.setText("Warranty period: "+data[selectedRow][5].toString()+" Weeks");
                        colorLabel.setText("Brand: "+data[selectedRow][6].toString());
                        items.setText("Items Available: "+data[selectedRow][7].toString());
                    }
                }
                if (Objects.equals(table.getValueAt(selectedRow, 2).toString(), "Clothing")){
                    if (selectedRow != -1) {
                        productIdLabel.setText("Product ID: "+ table.getValueAt(selectedRow, 0).toString());
                        nameLabel.setText("Name: "+table.getValueAt(selectedRow, 1).toString());
                        categoryLabel.setText("Category: "+table.getValueAt(selectedRow, 2).toString());
                        sizeLable.setText("Size: "+data[selectedRow][5].toString());
                        colorLabel.setText("Colour: "+data[selectedRow][6].toString());
                        items.setText("Items Available: "+data[selectedRow][7].toString());
                    }
                }
            }
        });
    }

    Object[][] getData(String filename){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            ArrayList<String> list = new ArrayList<>();
            String str = "";
            while ((str = reader.readLine()) != null){
                list.add(str);
            }
            int n = list.get(0).split("-").length;
            Object[][] data = new Object[list.size()][n];
            for (int i = 0; i < list.size(); i++){
                data[i] = list.get(i).split("-");
            }
            reader.close();
            return data;
        } catch (IOException e) {
            System.out.println("File not found.");
        }
        return null;
    }

    void reloadTableData(String filename) {
        Object[][] newData = getData(filename);
        DefaultTableModel model = new DefaultTableModel(newData, col) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // all cells false
            }
        };
        table.setModel(model);

        // Center table items after new file render
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.setRowHeight(30);
        // Custom renderer for Product ID column
        DefaultTableCellRenderer productIdRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int itemsAvailable = Integer.parseInt(data[table.convertRowIndexToModel(row)][7].toString());
                if (itemsAvailable <= 3) {
                    cellComponent.setBackground(Color.RED);
                } else {
                    cellComponent.setBackground(Color.WHITE);
                }
                if (row == selectedRowIndex) {
                    cellComponent.setBackground(Color.BLUE);
                    cellComponent.setForeground(Color.WHITE);
                } else {
                    cellComponent.setForeground(Color.BLACK);
                }
                return cellComponent;
            }
        };
        table.getColumnModel().getColumn(0).setCellRenderer(productIdRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(productIdRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(productIdRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(productIdRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(productIdRenderer);

        // Hide the "Info" column again after reloading data
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn sizeColumn = columnModel.getColumn(5);
        TableColumn colorColumn = columnModel.getColumn(6);
        TableColumn itemColumn = columnModel.getColumn(7);
        columnModel.removeColumn(sizeColumn);
        columnModel.removeColumn(colorColumn);
        columnModel.removeColumn(itemColumn);

        // Update data array to new data
        data = newData;


    }

    public void actionPerformed(ActionEvent e){

        if(e.getSource() == comboBox){
            int index = comboBox.getSelectedIndex();
            if(index == 0){
                reloadTableData("All.txt");
            }else if(index == 1){
                reloadTableData("Electronics.txt");
            }else if(index == 2){
                reloadTableData("Cloth.txt");
            }
        }
        if (e.getSource() == ShoppingCart) {
            if (cartItems.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Your shopping cart is empty.");
            } else {
                ShoppingCart g = new ShoppingCart(cartItems, cartPrices, productCategories);
                g.setTitle("Shopping Cart");
                g.setSize(720, 480);
                g.setVisible(true);
            }
        }
        if (e.getSource() == Add) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int itemsAvailable = Integer.parseInt(data[selectedRow][7].toString());
                //Check items have more than 0
                if (itemsAvailable > 0) {
                    String productId = table.getValueAt(selectedRow, 0).toString();
                    double price = Double.parseDouble(table.getValueAt(selectedRow, 3).toString());
                    String category = table.getValueAt(selectedRow, 2).toString();

                    // Add to cart
                    cartItems.put(productId, cartItems.getOrDefault(productId, 0) + 1);
                    cartPrices.put(productId, price);
                    productCategories.put(productId, category);

                    // Decrease items available
                    itemsAvailable -= 1;
                    data[selectedRow][7] = String.valueOf(itemsAvailable);

                    // Update table model
                    ((DefaultTableModel) table.getModel()).fireTableDataChanged();

                    // Provide feedback to the user
                    JOptionPane.showMessageDialog(this, "Product added to cart.");

                } else {
                    JOptionPane.showMessageDialog(this, "Product is out of stock.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product.");
            }
        }
    }

}
