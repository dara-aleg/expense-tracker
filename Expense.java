/**
 * Expense represents one recorded expense: its amount, category and description.
 * Fields are private and read through getter methods.
 */
public class Expense {
    private double amount;
    private Category category;
    private String description;

    /**
     * Creates a new expense.
     *
     * @param amount      the amount spent (must be positive; validated by Main)
     * @param category    one of the Category values
     * @param description a short description of the expense
     */
    public Expense(double amount, Category category, String description) {
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns the expense formatted for display, for example:
     * "$12.50  | Food            | Lunch with friends"
     * Amounts always show a dollar sign and two decimal places (NFR-4).
     */
    @Override
    public String toString() {
        return String.format("%-10s | %-15s | %s",
                String.format("$%.2f", amount), category.getDisplayName(), description);
    }
}
