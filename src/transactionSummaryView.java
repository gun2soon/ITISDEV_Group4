import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class transactionSummaryView {
    private JFrame frame;
    private JTextArea textArea;
    private JButton refreshButton;
    private transactionSummary transactionSummary; // Add this

    public transactionSummaryView(transactionSummary transactionSummary) {
        this.transactionSummary = transactionSummary; // Assign the passed instance
        frame = new JFrame("Transaction Summary");
        textArea = new JTextArea(20, 50);
        refreshButton = new JButton("Refresh");

        JPanel panel = new JPanel();
        panel.add(new JScrollPane(textArea));
        panel.add(refreshButton);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(false);

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshSummary();
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    private void refreshSummary() {
        textArea.setText(transactionSummary.getSummary());
    }

    public void loadTransactions(List<Float> transactions) {
        textArea.setText("");
        for (Float transaction : transactions) {
            textArea.append("Transaction: " + transaction + "\n");
        }
    }
    
    public void displaySummaryForDateRange(String startDate, String endDate) {
        float[] totals = transactionSummary.getTotalCostProfitAndCups(startDate, endDate);
        textArea.setText("");
        textArea.append("Total Sales: Php " + totals[0] + "\n");
        textArea.append("Total Profit: Php " + totals[1] + "\n");
        textArea.append("Total Cups Sold: " + totals[2] + "\n");
    }
}
