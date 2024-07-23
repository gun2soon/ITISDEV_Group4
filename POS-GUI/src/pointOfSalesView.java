import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;

public class pointOfSalesView extends JFrame {
    private JButton deleteButton;
    private JButton checkoutButton;
    private JButton exitButton;
    private JButton transactionSummaryButton;
    private JTable basketTable;
    private DefaultTableModel basketTableModel;

    private JLabel[] menuLabels;

    public pointOfSalesView() {
        setTitle("Ready Coffee PH - Point of Sales");
        setSize(770, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Menu Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 2, 10, 10));

        String[] menuItems = {
                "Iced Cafe Americano", "Hot Cafe Americano",
                "Iced Cafe Mocha", "Hot Cafe Mocha",
                "Iced Vanilla Macchiato", "Hot Vanilla Macchiato",
                "Iced Caramel Macchiato", "Hot Caramel Macchiato"
        };

        String[] imagePaths = {
                "resources/iced-Americano.png", "resources/hot-americano.png", 
                "resources/iced-mocha.png", "resources/hot-mocha.png", 
                "resources/iced-vanilla.png","resources/hot-vanilla.png", 
                "resources/iced-caramelM.png", "resources/hot-caramelM.png"
        };

        menuLabels = new JLabel[menuItems.length];
        for (int i = 0; i < menuItems.length; i++) {
            menuLabels[i] = createMenuLabel(menuItems[i], imagePaths[i]);
            menuPanel.add(menuLabels[i]);
        }

        panel.add(menuPanel, BorderLayout.WEST);

        // Basket Table
        String[] columnNames = {"Quantity", "Product Name", "Price"};
        basketTableModel = new DefaultTableModel(columnNames, 0);
        basketTable = new JTable(basketTableModel);
        JScrollPane basketScrollPane = new JScrollPane(basketTable);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 5, 5));
        deleteButton = new JButton("Delete Item from Basket");
        checkoutButton = new JButton("Checkout");
        transactionSummaryButton = new JButton("View Transaction Summary");
        exitButton = new JButton("Exit POS");

        buttonPanel.add(deleteButton);
        buttonPanel.add(checkoutButton);
        buttonPanel.add(transactionSummaryButton);
        buttonPanel.add(exitButton);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(basketScrollPane, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(rightPanel, BorderLayout.EAST);

        add(panel);
    }

    private JLabel createMenuLabel(String text, String imagePath) {
        JLabel label = new JLabel(text);
        label.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setPreferredSize(new Dimension(150, 100));
        return label;
    }

    public void setMenuLabelListener(MouseAdapter listener) {
        for (JLabel label : menuLabels) {
            label.addMouseListener(listener);
        }
    }

    public void setDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void setcheckoutButtonListener(ActionListener listener) {
        checkoutButton.addActionListener(listener);
    }

    public void setTransactionSummaryButtonListener(ActionListener listener) {
        transactionSummaryButton.addActionListener(listener);
    }

    public void setExitButtonListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    public void addToBasketTable(int quantity, String productName, float price) {
        float total = quantity * price;
        String formattedPrice = String.format("%.2f", total);
        basketTableModel.addRow(new Object[]{quantity, productName, formattedPrice});
    }
    
    // Method to add a blank line
    public void addBlankLine() {
        basketTableModel.addRow(new Object[]{"----------------", "----------------", "----------------"});
    }

    public void addToBasketTable(String productName, String price) {
        basketTableModel.addRow(new Object[]{0, productName, price});
    }
    
    public void deleteFromBasketTable(int index) {
        if (index >= 0 && index < basketTableModel.getRowCount()) {
            basketTableModel.removeRow(index);
        }
    }

    public int getSelectedItemIndex() {
        return basketTable.getSelectedRow();
    }

    public DefaultTableModel getBasketTableModel() {
        return basketTableModel;
    }

    public void displayTransactionSummary(String summary) {
        JOptionPane.showMessageDialog(this, summary, "Transaction Summary", JOptionPane.INFORMATION_MESSAGE);
    }
}
