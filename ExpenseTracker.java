import java.util.ArrayList;

/**
 * ExpenseTracker owns the list of expenses (composition) and contains the
 * logic to add, list, total, group by category and remove them.
 * Expenses are kept in memory only for the current session (NFR-8).
 */
public class ExpenseTracker {
    private ArrayList<Expense> expenses;

    public ExpenseTracker() {
        expenses = new ArrayList<>();
    }

    /**
     * Creates a new Expense and adds it to the list (FR-2, FR-4).
     */
    public void addExpense(double amount, Category category, String description) {
        Expense expense = new Expense(amount, category, description);
        expenses.add(expense);
    }

    /**
     * Removes the expense at the given 1-based list number (FR-8).
     *
     * @param number the number shown next to the expense in the list
     * @return the removed Expense
     * @throws IndexOutOfBoundsException if the number is not in the list
     */
    public Expense removeExpense(int number) {
        return expenses.remove(number - 1);
    }

    /**
     * Returns the expense at the given 1-based list number.
     */
    public Expense getExpense(int number) {
        return expenses.get(number - 1);
    }

    /**
     * Returns a copy of the expense list so callers cannot change it directly.
     */
    public ArrayList<Expense> getExpenses() {
        return new ArrayList<>(expenses);
    }

    /**
     * Adds up every expense amount and returns the total (FR-6).
     */
    public double getTotal() {
        double total = 0.0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    /**
     * Adds up the amounts of every expense in one category (FR-7).
     */
    public double getTotalByCategory(Category category) {
        double total = 0.0;
        for (Expense expense : expenses) {
            if (expense.getCategory() == category) {
                total += expense.getAmount();
            }
        }
        return total;
    }

    public int getExpenseCount() {
        return expenses.size();
    }

    public boolean isEmpty() {
        return expenses.isEmpty();
    }
}
