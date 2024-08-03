import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
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
    private JTextArea transactionTotal;
    private JTextArea transactionDiscount;
    private DefaultTableModel basketTableModel;
    private JLabel[] menuLabels;

    public pointOfSalesView() {
        setTitle("Ready Coffee PH - Point of Sales");
        setSize(1100, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BorderLayout(10, 10));
        // Menu Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 2, 10, 10));
      

        String[] menuItems = {
                "Iced Cafe Americano", "Hot Cafe Americano",
                "Iced Cafe Mocha", "Hot Cafe Mocha",
                "Iced Vanilla Macchiato", "Hot Vanilla Macchiato",
                "Iced Caramel Macchiato", "Hot Caramel Macchiato"
        };

        String[] price = {
            "P120.00", "P110.00",
            "P170.00", "160.00",
            "P170.00", "P160.00",
            "P190.00", "P180.00"
    };

        String[] imagePaths = {
                "resources/Iced-Americano.png", "resources/hot-americano.png", 
                "resources/iced-mocha.png", "resources/hot-mocha.png", 
                "resources/iced-vanilla.png","resources/hot-vanilla.png", 
                "resources/iced-caramelM.png", "resources/hot-caramelM.png"
        };

        menuLabels = new JLabel[menuItems.length];
    for (int i = 0; i < menuItems.length; i++) {
        menuLabels[i] = createMenuLabel(menuItems[i], price[i], imagePaths[i]);
        menuPanel.add(menuLabels[i]);
    }

        menuPanel.setBorder(
           BorderFactory.createCompoundBorder( 
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY),
                    "  Coffee Menu  ", 
                    TitledBorder.CENTER, 
                    TitledBorder.TOP), 
           BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        panel.add(menuPanel, BorderLayout.WEST);

        // Basket Table
        String[] columnNames = {"Quantity", "Product Name", "Price", "Discounted"};
        // resized the column width
        int[] columnWidth = {50, 200, 100, 100};

        // (Changes) create a basketModel that the discount is a dropdown of 0%, 5%, 10%, 15%, 20%
        basketTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) {
                    return String.class;
                }
                return super.getColumnClass(columnIndex);
            }
            
        };
        String[] discounts = {"0%", "5%", "10%", "15%", "20%"};
        JComboBox<String> comboBox = new JComboBox<>(discounts);

        
        basketTable = new JTable(basketTableModel);
        basketTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboBox));
        JScrollPane basketScrollPane = new JScrollPane(basketTable);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 5, 5)); // Changed to GridLayout(4, 1, 5, 5)
        deleteButton = new JButton("Delete Item from Basket");
        checkoutButton = new JButton("Checkout");
        transactionSummaryButton = new JButton("View Transaction Summary");
        exitButton = new JButton("Exit POS");

        JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(new GridLayout(1, 2, 5, 5));
        transactionPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        transactionTotal = new JTextArea();
        transactionTotal.setText("₱ 0.00");
        transactionTotal.setEditable(false);
        transactionTotal.setLineWrap(true);
        transactionTotal.setWrapStyleWord(true);
        transactionTotal.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
                    "Total:", 
                    TitledBorder.LEFT, 
                    TitledBorder.TOP,
                    new Font("Arial", Font.PLAIN, 14)), 
                BorderFactory.createEmptyBorder(3, 3, 3, 3))
        );
        transactionTotal.setBackground(null);
        transactionTotal.setOpaque(false);
        transactionPanel.add(transactionTotal);

        transactionDiscount = new JTextArea();
        transactionDiscount.setText("₱ 0.00");
        transactionDiscount.setEditable(false);
        transactionDiscount.setLineWrap(true);
        transactionDiscount.setWrapStyleWord(true);
        transactionDiscount.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
                    "Discount:", 
                    TitledBorder.LEFT, 
                    TitledBorder.TOP,
                    new Font("Arial", Font.PLAIN, 14)), 
                BorderFactory.createEmptyBorder(3, 3, 3, 3))
        );
        transactionDiscount.setBackground(null);
        transactionDiscount.setOpaque(false);
        transactionPanel.add(transactionDiscount);


        buttonPanel.add(deleteButton);
        buttonPanel.add(checkoutButton);
        buttonPanel.add(transactionSummaryButton);
        buttonPanel.add(exitButton);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(basketScrollPane, BorderLayout.CENTER);

        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BorderLayout());
        totalPanel.add(transactionPanel, BorderLayout.CENTER);
        totalPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        totalPanel.add(buttonPanel, BorderLayout.SOUTH);
        rightPanel.add(totalPanel, BorderLayout.SOUTH);

        panel.add(rightPanel, BorderLayout.CENTER);

        add(panel);
    }

    private JLabel createMenuLabel(String productName, String price, String imagePath) {
        String labelText = "<html><div style='text-align: center;'>" + productName + "<br>" + price + "</div></html>";
        JLabel label = new JLabel(labelText);
        
        label.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 20, 15)) // Added space around the item
        );
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setPreferredSize(new Dimension(185, 150)); // Increased height to accommodate price and extra space
        return label;
    }
    
    
    

    public void setMenuLabelListener(MouseAdapter listener) {
        for (JLabel label : menuLabels) {
            label.addMouseListener(listener);
        }
    }

    public void setComboBoxListener(CellEditorListener listener) {
        basketTable.getColumnModel().getColumn(3).getCellEditor().addCellEditorListener(listener);
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

    public int getSelectedItemIndex() {
        return basketTable.getSelectedRow();
    }

    public DefaultTableModel getBasketTableModel() {
        return basketTableModel;
    }
    
    public void addToBasketTable(int quantity, String productName, float price) {
        basketTableModel.addRow(new Object[]{quantity, productName, price, "0%"});
    }
    
    public void resetSelection() {
        basketTable.clearSelection();
    }

    public void deleteFromBasketTable(int index) {
        basketTableModel.removeRow(index);
    }

    public void setTransactionTotal(String total) {
        transactionTotal.setText(total);
    }

    public void setTransactionDiscount(String discount) {
        transactionDiscount.setText(discount);
    }
}
