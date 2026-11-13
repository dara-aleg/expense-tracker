/**
 * Category defines the four allowed expense categories.
 * Using an enum guarantees that every Expense has a valid category (FR-3).
 */
public enum Category {
    FOOD("Food"),
    TRANSPORTATION("Transportation"),
    ENTERTAINMENT("Entertainment"),
    OTHER("Other");

    // Friendly name shown to the user, e.g. "Food" instead of "FOOD"
    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
