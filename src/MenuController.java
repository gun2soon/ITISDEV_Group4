import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuController implements ActionListener {

    private MenuView menuView;
    private InventoryView inventoryView;
    private InventoryController inventoryController;
    private InventoryManagement inventory;
    private pointOfSales pos;

    public MenuController(MenuView menuView, InventoryManagement inventory, pointOfSales pos, InventoryView inventoryView) {
        this.menuView = menuView;
        this.inventory = inventory;
        this.pos = pos;
        this.inventoryView = inventoryView;
        this.menuView.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        if (source.equals(menuView.getInventoryButton())) {
            showInventoryView();
            menuView.getFrame().dispose();
        } else if (source.equals(menuView.getPosButton())) {
            openPOS();
            menuView.getFrame().dispose();
        } else if (source.equals(menuView.getExitButton())) {
            System.exit(0);
        }
    }

    private void openPOS() {
        pointOfSalesView view = new pointOfSalesView();
        new pointOfSalesController(pos, view, inventory, menuView); // Adjusted constructor if needed
        view.setVisible(true);
    }

    private void showInventoryView() {
        if (inventoryController == null) {
            inventoryController = new InventoryController(inventory, inventoryView, menuView);
        }
        JFrame inventoryFrame = inventoryView.getFrame();
        inventoryFrame.setVisible(true);
        //InventoryController.populateTable();
    }
}
