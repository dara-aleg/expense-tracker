import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main is the driver class. It displays the main menu, reads and validates
 * user input with a Scanner, and calls the matching ExpenseTracker method.
 *
 * Input is always read as a full line and parsed by hand, so letters,
 * negative numbers or out-of-range choices never crash the program (NFR-2).
 */
public class Main {
    private ExpenseTracker tracker;
    private Scanner scanner;

    public Main() {
        tracker = new ExpenseTracker();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    /**
     * Main menu loop (Activity Diagram, Figure 1). Repeats until the user exits.
     */
    public void run() {
        System.out.println("========================================");
        System.out.println("     Welcome to Personal Expense Tracker");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = readMenuChoice();

            switch (choice) {
                case 1 -> addExpense();
                case 2 -> displayExpenses();
                case 3 -> displayTotal();
                case 4 -> displayCategoryTotals();
                case 5 -> removeExpense();
                case 6 -> {
                    System.out.println("\nThank you for using Personal Expense Tracker. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please enter a number from 1 to 6.");
            }
        }
        scanner.close();
    }

    /** Displays the numbered main menu (FR-1). */
    private void displayMenu() {
        System.out.println();
        System.out.println("----------- Main Menu -----------");
        System.out.println("1. Add an expense");
        System.out.println("2. View expenses");
        System.out.println("3. Calculate total spending");
        System.out.println("4. View spending by category");
        System.out.println("5. Remove an expense");
        System.out.println("6. Exit");
        System.out.print("Enter your choice (1-6): ");
    }

    /**
     * Reads the menu choice. Returns -1 for anything that is not a whole number,
     * which the switch in run() treats as invalid.
     */
    private int readMenuChoice() {
        String line = readLine();
        try {
            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // ------------------------------------------------------------------
    // Option 1: Add an expense (UC-1, Activity Diagram Figure 2)
    // ------------------------------------------------------------------

    private void addExpense() {
        System.out.println("\n--- Add an Expense ---");
        double amount = readAmount();
        Category category = readCategory();
        String description = readDescription();

        tracker.addExpense(amount, category, description);
        System.out.println("Expense added successfully.");
    }

    /**
     * Prompts until the user enters a positive number (FR-3, UC-7).
     */
    private double readAmount() {
        while (true) {
            System.out.print("Enter the amount: $");
            String line = readLine().trim();
            try {
                double amount = Double.parseDouble(line);
                if (Double.isNaN(amount) || Double.isInfinite(amount)) {
                    System.out.println("Error: please enter a valid number, such as 12.50.");
                } else if (amount <= 0) {
                    System.out.println("Error: the amount must be greater than zero.");
                } else {
                    // Round to cents so totals stay accurate (NFR-4)
                    return Math.round(amount * 100.0) / 100.0;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a valid number, such as 12.50.");
            }
        }
    }

    /**
     * Shows the category menu and converts the user's choice into a
     * Category value. Repeats until the choice is valid (FR-3, UC-7).
     */
    private Category readCategory() {
        Category[] categories = Category.values();
        while (true) {
            System.out.println("Choose a category:");
            for (int i = 0; i < categories.length; i++) {
                System.out.println("  " + (i + 1) + ". " + categories[i].getDisplayName());
            }
            System.out.print("Enter your choice (1-" + categories.length + "): ");
            String line = readLine().trim();
            try {
                int choice = Integer.parseInt(line);
                if (choice >= 1 && choice <= categories.length) {
                    return categories[choice - 1];
                }
            } catch (NumberFormatException e) {
                // handled by the error message below
            }
            System.out.println("Error: please enter a number from 1 to " + categories.length + ".");
        }
    }

    /**
     * Reads a short description. A blank entry becomes "No description".
     */
    private String readDescription() {
        System.out.print("Enter a short description: ");
        String description = readLine().trim();
        if (description.isEmpty()) {
            description = "No description";
        }
        return description;
    }

    // ------------------------------------------------------------------
    // Option 2: View expenses (UC-2)
    // ------------------------------------------------------------------

    private void displayExpenses() {
        System.out.println("\n--- Expense List ---");
        if (tracker.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        printExpenseList();
    }

    /** Prints every expense with its list number, amount, category and description (FR-5). */
    private void printExpenseList() {
        ArrayList<Expense> expenses = tracker.getExpenses();
        System.out.println(String.format("%-4s %-10s | %-15s | %s", "#", "Amount", "Category", "Description"));
        System.out.println("-----------------------------------------------------");
        for (int i = 0; i < expenses.size(); i++) {
            System.out.println(String.format("%-4s %s", (i + 1) + ".", expenses.get(i)));
        }
    }

    // ------------------------------------------------------------------
    // Option 3: Calculate total spending (UC-3)
    // ------------------------------------------------------------------

    private void displayTotal() {
        System.out.println("\n--- Total Spending ---");
        System.out.println(String.format("Total spending: $%.2f", tracker.getTotal()));
        if (tracker.isEmpty()) {
            System.out.println("(No expenses have been recorded yet.)");
        } else {
            System.out.println("(" + tracker.getExpenseCount() + " expense(s) recorded)");
        }
    }

    // ------------------------------------------------------------------
    // Option 4: View spending by category (UC-4)
    // ------------------------------------------------------------------

    private void displayCategoryTotals() {
        System.out.println("\n--- Spending by Category ---");
        if (tracker.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        for (Category category : Category.values()) {
            System.out.println(String.format("%-15s $%.2f",
                    category.getDisplayName() + ":", tracker.getTotalByCategory(category)));
        }
        System.out.println("-----------------------------");
        System.out.println(String.format("%-15s $%.2f", "Total:", tracker.getTotal()));
    }

    // ------------------------------------------------------------------
    // Option 5: Remove an expense (UC-5, Activity Diagram Figure 3)
    // ------------------------------------------------------------------

    private void removeExpense() {
        System.out.println("\n--- Remove an Expense ---");
        if (tracker.isEmpty()) {
            System.out.println("No expenses to remove.");
            return;
        }

        // UC-5 includes UC-2: show the numbered list first
        printExpenseList();

        int number = readExpenseNumber();
        Expense selected = tracker.getExpense(number);

        String summary = String.format("$%.2f, %s, %s", selected.getAmount(),
                selected.getCategory().getDisplayName(), selected.getDescription());
        if (readConfirmation("Remove expense #" + number + " (" + summary + ")? (Y/N): ")) {
            tracker.removeExpense(number);
            System.out.println("Expense removed.");
        } else {
            System.out.println("Removal cancelled.");
        }
    }

    /** Prompts until the user enters a number that is in the list. */
    private int readExpenseNumber() {
        int count = tracker.getExpenseCount();
        while (true) {
            System.out.print("Enter the number of the expense to remove (1-" + count + "): ");
            String line = readLine().trim();
            try {
                int number = Integer.parseInt(line);
                if (number >= 1 && number <= count) {
                    return number;
                }
            } catch (NumberFormatException e) {
                // handled by the error message below
            }
            System.out.println("Error: please enter a number from 1 to " + count + ".");
        }
    }

    /** Asks a yes/no question until the user answers Y or N. */
    private boolean readConfirmation(String prompt) {
        while (true) {
            System.out.print(prompt);
            String answer = readLine().trim().toUpperCase();
            if (answer.equals("Y") || answer.equals("YES")) {
                return true;
            }
            if (answer.equals("N") || answer.equals("NO")) {
                return false;
            }
            System.out.println("Error: please enter Y or N.");
        }
    }

    // ------------------------------------------------------------------
    // Input helper
    // ------------------------------------------------------------------

    /**
     * Reads one line of input. If input ends unexpectedly (for example,
     * Ctrl+D), the program exits cleanly instead of crashing.
     */
    private String readLine() {
        if (!scanner.hasNextLine()) {
            System.out.println("\nInput closed. Goodbye!");
            System.exit(0);
        }
        return scanner.nextLine();
    }
}
