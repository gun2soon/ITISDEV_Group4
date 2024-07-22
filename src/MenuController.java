import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuController implements ActionListener {

    private MenuView menuView;
    private InventoryView inventoryView;
    private InventoryController inventoryController;
    private InventoryManagement inventory;
   // private PointOfSales pos;

    public MenuController(MenuView menuView, InventoryManagement inventory, InventoryView inventoryView) {
        this.menuView = menuView;
        this.inventory = inventory;
       // this.pos = pos;
        this.inventoryView = inventoryView;
        this.menuView.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        if (source.equals(menuView.getInventoryButton())) {
            showInventoryView();
            menuView.getFrame().dispose();
        } else if (source.equals(menuView.getExitButton())) {
            System.exit(0);
        }
        // inser the other buttons
    }

    private void showInventoryView() {
        if (inventoryController == null) {
            inventoryController = new InventoryController(inventory, inventoryView, menuView);
        }
        JFrame inventoryFrame = inventoryView.getFrame();
        inventoryFrame.setVisible(true);
    }
}
