import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class transactionSummary {
    // Inner class to represent a single transaction
    private static class Transaction {
        private String coffeeType;
        private float cost;
        private float profit;
        private String date;
        private String time;

        public Transaction(String coffeeType, float cost, float profit, String date, String time) {
            this.coffeeType = coffeeType;
            this.cost = cost;
            this.profit = profit;
            this.date = date;
            this.time = time;
        }

        @Override
        public String toString() {
            return String.format("Coffee: %-20s | Cost: Php %.2f | Profit: Php %.2f | Date: %s | Time: %s",
                    coffeeType, cost, profit, date, time);
        }
    }

    private List<Transaction> transactions;

    public transactionSummary() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(String coffeeType, float cost, float profit) {
        // Get the current date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(new Date());
        String time = timeFormat.format(new Date());

        // Create a new transaction and add it to the list
        Transaction transaction = new Transaction(coffeeType, cost, profit, date, time);
        transactions.add(transaction);
    }

    public void displaySummary() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nTransaction Summary:");
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
    }
}