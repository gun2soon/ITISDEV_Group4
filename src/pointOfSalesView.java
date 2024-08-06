import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;

public class pointOfSalesView extends JFrame {
    private JButton deleteButton;
    private JButton checkoutButton;
    private JButton exitButton;
    private JButton transactionSummaryButton;
    private JCheckBox discountedButton;
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

        String[] imagePaths = {
                "resources/Iced-Americano.png", "resources/hot-americano.png", 
                "resources/iced-mocha.png", "resources/hot-mocha.png", 
                "resources/iced-vanilla.png","resources/hot-vanilla.png", 
                "resources/iced-caramelM.png", "resources/hot-caramelM.png"
        };

        menuLabels = new JLabel[menuItems.length];
        for (int i = 0; i < menuItems.length; i++) {
            menuLabels[i] = createMenuLabel(menuItems[i], imagePaths[i]);
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
        String[] columnNames = {"Quantity", "Product Name", "Price", "Total Price"};
        // resized the column width
        int[] columnWidth = {50, 200, 50, 50};

        /*
        String[] columnNames = {"Quantity", "Product Name", "Price", "Discounted"};
        int[] columnWidth = {50, 200, 100, 100};
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
        */

        basketTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return String.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        

        basketTable = new JTable(basketTableModel);
        for (int i = 0; i < columnWidth.length; i++) {
            basketTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);
        }
        // basketTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboBox));
        basketTable.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JTextField()));
        

        JScrollPane basketScrollPane = new JScrollPane(basketTable);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 5, 5)); // Changed to GridLayout(4, 1, 5, 5)
        deleteButton = new JButton("Delete Item from Basket");
        checkoutButton = new JButton("Checkout");
        transactionSummaryButton = new JButton("View Transaction Summary");
        exitButton = new JButton("Exit POS");

        JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(new GridBagLayout());
        transactionPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(3, 3, 3, 3);

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
        transactionPanel.add(transactionTotal, gbc);

        gbc.gridx = 1;
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
        transactionPanel.add(transactionDiscount, gbc);



        discountedButton = new JCheckBox("<html>Do you have 20% discount?</html>");
        discountedButton.setFont(new Font("Arial", Font.PLAIN, 14));
        discountedButton.setPreferredSize(new Dimension(290, 50));
        

        gbc.gridx = 2;
        transactionPanel.add(discountedButton, gbc);




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

    private JLabel createMenuLabel(String text, String imagePath) {
        JLabel label = new JLabel(text);
        label.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 20, 15))
        );
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setPreferredSize(new Dimension(185, 150));
        return label;
    }

    public void setMenuLabelListener(MouseAdapter listener) {
        for (JLabel label : menuLabels) {
            label.addMouseListener(listener);
        }
    }

    public void setDiscountedButtonListener(ChangeListener listener) {
        discountedButton.addChangeListener(listener);
    }

    public void setComboBoxListener(CellEditorListener listener) {
        basketTable.getColumnModel().getColumn(3).getCellEditor().addCellEditorListener(listener);
    }

    public void setQuantityListener(CellEditorListener listener) {
        basketTable.getColumnModel().getColumn(0).getCellEditor().addCellEditorListener(listener);
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

    public void setDiscountButtonText(String text){
        discountedButton.setText(text);
    }


    public int getSelectedItemIndex() {
        return basketTable.getSelectedRow();
    }

    public DefaultTableModel getBasketTableModel() {
        return basketTableModel;
    }

    public void addToBasketTable(int quantity, String productName, float price) {
        // basketTableModel.addRow(new Object[]{quantity, productName, price, "0%"});
        // check if already in the table
        for (int i = 0; i < basketTableModel.getRowCount(); i++) {
            if (basketTableModel.getValueAt(i, 1).equals(productName)) {
                int oldQuantity = (int) basketTableModel.getValueAt(i, 0);
                basketTableModel.setValueAt(oldQuantity + quantity, i, 0);
                basketTableModel.setValueAt(price * (oldQuantity + quantity), i, 3);
                return;
            }
        }
        basketTableModel.addRow(new Object[]{quantity, productName, price, price * quantity});
    }

    public void resetSelection() {
        basketTable.clearSelection();
    }


    public void addToBasketTable(String productName, String price) {
        // basketTableModel.addRow(new Object[]{null, productName, price, "0%"});
        basketTableModel.addRow(new Object[]{null, productName, price, null});
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
