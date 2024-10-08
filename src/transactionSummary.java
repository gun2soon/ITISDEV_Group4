import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class transactionSummary {

    // Inner class to represent a single transaction
    public static class Transaction {
        private int idTransaction;
        private int cupQuantity;
        private String coffeeType;
        private float cost;
        private float profit;
        private String date;

        public Transaction(int idTransaction, int cupQuantity, String coffeeType, float cost, float profit, String date) {
            this.idTransaction = idTransaction;
            this.cupQuantity = cupQuantity;
            this.coffeeType = coffeeType;
            this.cost = cost;
            this.profit = profit;
            this.date = date;
        }

        // Getters
        public int getIdTransaction() {
            return idTransaction;
        }

        public int getcupQuantity() {
            return cupQuantity;
        }

        public String getCoffeeType() {
            return coffeeType;
        }

        public float getCost() {
            return cost;
        }

        public float getProfit() {
            return profit;
        }

        public String getDate() {
            return date;
        }

        @Override
        public String toString() {
            return String.format("ID: %d | Cups: %d | Coffee: %-20s | Cost: Php %.2f | Profit: Php %.2f | Date: %s",
                    idTransaction, cupQuantity, coffeeType, cost, profit, date);
        }
    }

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/inventory?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "12345";

    private List<Transaction> transactions;

    public transactionSummary() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(int cupQuantity, String coffeeType, float cost, float profit) {
        // Get the current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());

        // Get the next transaction ID
        int nextId = getNextTransactionId();

        // Create a new transaction and add it to the list
        Transaction transaction = new Transaction(nextId, cupQuantity, coffeeType, cost, profit, date);
        transactions.add(transaction);

        // Save to database
        saveTransactionToDatabase(transaction);
    }   

    public String getSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Transaction Summary:\n");
        for (Transaction transaction : transactions) {
            summary.append(transaction).append("\n");
        }
        return summary.toString();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // get transactions based on given span including start and end date
    public List<Transaction> getTransactions(Date startDate, Date endDate) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(transaction.date);
                if (date.after(startDate) && date.before(endDate) || date.equals(startDate) || date.equals(endDate)) {
                    filteredTransactions.add(transaction);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filteredTransactions;
    }

    private int getNextTransactionId() {
        String selectSQL = "SELECT COALESCE(MAX(idTransaction), 9999) + 1 AS nextId FROM transactions";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            if (rs.next()) {
                return rs.getInt("nextId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 10000; // Fallback value if there is an error
    }

    private void saveTransactionToDatabase(Transaction transaction) {
        String insertSQL = "INSERT INTO transactions (idTransaction, cupQuantity, coffeeType, coffeeCost, coffeeProfit, orderDate) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setInt(1, transaction.idTransaction);
            pstmt.setInt(2, transaction.cupQuantity);
            pstmt.setString(3, transaction.coffeeType);
            pstmt.setFloat(4, transaction.cost);
            pstmt.setFloat(5, transaction.profit);
            pstmt.setString(6, transaction.date);
            pstmt.executeUpdate();
            System.out.println(transaction);
            System.out.println("Transaction saved to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displaySummary() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nTransaction Summary:");

        // Display transactions
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        
        System.out.println("\n[1] Exit");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt(); // Prompt the user first, then read the input
        sc.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Exit Transaction Summary");
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
        sc.close();
    }

    // Method to load transactions from the database into the list
    public void loadTransactionsFromDatabase() {
        String selectSQL = "SELECT idTransaction, cupQuantity, coffeeType, coffeeCost, coffeeProfit, orderDate FROM transactions";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            transactions.clear(); // Clear existing transactions

            while (rs.next()) {
                int idTransaction = rs.getInt("idTransaction");
                int cupQuantity = rs.getInt("cupQuantity");
                String coffeeType = rs.getString("coffeeType");
                float cost = rs.getFloat("coffeeCost");
                float profit = rs.getFloat("coffeeProfit");
                String date = rs.getString("orderDate");
                
                Transaction transaction = new Transaction(idTransaction, cupQuantity, coffeeType, cost, profit, date);
                transactions.add(transaction);
            }
            System.out.println("Transactions loaded from database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get total cost, profit, and cup quantity for a date range
    public float[] getTotalCostProfitAndCups(String startDate, String endDate) {
        String query = "SELECT SUM(coffeeCost) AS totalCost, SUM(coffeeProfit) AS totalProfit, SUM(cupQuantity) AS totalCups FROM transactions WHERE orderDate BETWEEN ? AND ?";
        float totalCost = 0.0f;
        float totalProfit = 0.0f;
        float totalCups = 0.0f;

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalCost = rs.getFloat("totalCost");
                    totalProfit = rs.getFloat("totalProfit");
                    totalCups = rs.getFloat("totalCups");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new float[]{totalCost, totalProfit, totalCups};
    }
}
