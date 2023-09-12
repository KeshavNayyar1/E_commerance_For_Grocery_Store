
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class GroceryStoreApp {
    private JFrame frame;
    private DefaultListModel<Product> productListModel;
    private JList<Product> productList;
    private DefaultListModel<Product> cartListModel;
    private JList<Product> cartList;
    private JLabel totalPriceLabel;

    private Map<Product, Integer> cartContents;

    public GroceryStoreApp() {
        frame = new JFrame("Grocery Store App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(new GridLayout(1, 2));

        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);
        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cartListModel = new DefaultListModel<>();
        cartList = new JList<>(cartListModel);
        cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JLabel("Products"), BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(productList), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JLabel("Shopping Cart"), BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product selectedProduct = productList.getSelectedValue();
                if (selectedProduct != null) {
                    // Add the selected product to the cart
                    cartContents.put(selectedProduct, cartContents.getOrDefault(selectedProduct, 0) + 1);
                    // Update the cart list and total price
                    updateCartList();
                    updateTotalPrice();
                }
            }
        });

        JButton buyButton = new JButton("Buy");
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Simulate the purchase process (e.g., deduct items from inventory, clear the cart, etc.)
                cartContents.clear();
                cartListModel.clear();
                updateTotalPrice();
            }
        });

        leftPanel.add(addToCartButton, BorderLayout.SOUTH);
        rightPanel.add(buyButton, BorderLayout.SOUTH);

        // Initialize some sample products
        cartContents = new HashMap<>();
        productListModel.addElement(new Product("Apples", 2.0));
        productListModel.addElement(new Product("Bananas", 1.5));
        productListModel.addElement(new Product("Bread", 3.0));
        productListModel.addElement(new Product("Milk", 2.5));
        productListModel.addElement(new Product("Chicken", 10));
        productListModel.addElement(new Product("Eggs", 4));
        productListModel.addElement(new Product("Potatoes", 2));
        productListModel.addElement(new Product("Ground Beef", 12));
        productListModel.addElement(new Product("Lettuce", 2));
        productListModel.addElement(new Product("Fish", 25));
        productListModel.addElement(new Product("Chocolate Bar", 1));
        productListModel.addElement(new Product("Water Bottles", 3));
        totalPriceLabel = new JLabel("Total Price: $0.00");
        rightPanel.add(totalPriceLabel, BorderLayout.SOUTH);

        frame.add(leftPanel);
        frame.add(rightPanel);

        frame.setVisible(true);
    }

    private void updateCartList() {
        cartListModel.clear();
        // Update the cart list with quantities
        for (Map.Entry<Product, Integer> entry : cartContents.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            cartListModel.addElement(new Product(product.getName(), product.getPrice(), quantity));
        }
    }

    private void updateTotalPrice() {
        double total = 0.0;
        // Calculate the total price based on quantities in the cart
        for (Map.Entry<Product, Integer> entry : cartContents.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            total += product.getPrice() * quantity;
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        totalPriceLabel.setText("Total Price: $" + df.format(total));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GroceryStoreApp();
            }
        });
    }

    class Product {
        private String name;
        private double price;
        private int quantity;

        public Product(String name, double price) {
            this(name, price, 1);
        }

        public Product(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        @Override
        public String toString() {
            return name + " - $" + price + " (x" + quantity + ")";
        }
    }
}
