# Personal Expense Tracker

**ITCS 3112 – Final Project Submission**
**Author:** Dara Alegre

A small, console-based Java application that lets a single user record expenses,
review them, see total spending, see spending by category, and remove entries.
It runs entirely offline, and data is kept in memory for the current session only.

---

## 1. What's included

```
expense-tracker/
├── ExpenseTracker.jar      Ready-to-run executable program
├── README.md               This file (running and usage instructions)
└── src/                    Java source code
    ├── Main.java           Driver: menu, input, validation
    ├── ExpenseTracker.java Manages the list of expenses and the calculations
    ├── Expense.java        One expense (amount, category, description)
    └── Category.java       Enum of the four allowed categories
```

---

## 2. Requirements

- **Java 17 or later** (JDK to compile, JRE/JDK to run) on Windows, macOS or Linux.
- To check your version, open a terminal and type:
  ```
  java -version
  ```
  If the command is not found or the version is below 17, install a JDK
  (for example, from https://adoptium.net) and try again.

---

## 3. How to run the program

Open a terminal (Command Prompt / PowerShell on Windows, Terminal on macOS or Linux)
and move into the `expense-tracker` folder:

```
cd path/to/expense-tracker
```

### Option A – Run the included executable (easiest)

```
java -jar ExpenseTracker.jar
```

### Option B – Compile and run from source

```
javac -d out src/*.java
java -cp out Main
```

> On Windows Command Prompt, if `src/*.java` is not accepted, use `src\*.java`.

### Option C – Run in an IDE (IntelliJ, Eclipse, VS Code)

1. Create a new Java project (or open the folder).
2. Mark `src` as the source folder and copy the four `.java` files into it.
3. Run `Main.java`. The program uses the IDE's console for input and output.

---

## 4. How to use the program

When the program starts, it shows the main menu. Type the number of an option
and press **Enter**. After each option finishes, the menu appears again.

```
----------- Main Menu -----------
1. Add an expense
2. View expenses
3. Calculate total spending
4. View spending by category
5. Remove an expense
6. Exit
Enter your choice (1-6):
```

### 1 – Add an expense
1. Enter the **amount** (for example `12.50`). It must be a number greater than zero.
   Do not type the `$` sign.
2. Choose a **category** by number: `1` Food, `2` Transportation, `3` Entertainment, `4` Other.
3. Enter a short **description** (for example `Lunch with friends`).
   If you leave it blank, the program saves it as "No description".

The program prints **"Expense added successfully."**

### 2 – View expenses
Shows every expense in a numbered list:

```
#    Amount     | Category        | Description
-----------------------------------------------------
1.   $12.50     | Food            | Lunch
2.   $30.00     | Transportation  | Gas
```

### 3 – Calculate total spending
Shows the sum of all expenses, for example `Total spending: $42.50`.

### 4 – View spending by category
Shows the total for each of the four categories (categories with no expenses show `$0.00`):

```
Food:           $12.50
Transportation: $30.00
Entertainment:  $0.00
Other:          $0.00
-----------------------------
Total:          $42.50
```

### 5 – Remove an expense
1. The program shows the numbered expense list.
2. Enter the **number** of the expense to remove.
3. Confirm with **Y** to remove it, or **N** to cancel.

### 6 – Exit
Prints a goodbye message and closes the program. Because data is kept in memory
only, expenses are not saved after you exit.

### Invalid input
The program never crashes on bad input. If you type a letter, a negative or zero
amount, or a number outside the allowed range, it shows an error message and asks again.
If there are no expenses yet, options 2, 4 and 5 show a message such as
"No expenses recorded yet."

---

## 5. Example session

```
Enter your choice (1-6): 1

--- Add an Expense ---
Enter the amount: $abc
Error: please enter a valid number, such as 12.50.
Enter the amount: $12.50
Choose a category:
  1. Food
  2. Transportation
  3. Entertainment
  4. Other
Enter your choice (1-4): 1
Enter a short description: Lunch
Expense added successfully.
```

---

## 6. Design summary (matches Milestones 2–4)

| Class | Responsibility |
|---|---|
| `Main` | Displays the menu, reads input with a `Scanner`, validates it, and calls the matching `ExpenseTracker` method. Holds one `ExpenseTracker` in its private `tracker` field. |
| `ExpenseTracker` | Owns an `ArrayList<Expense>` (composition). Methods: `addExpense()`, `removeExpense()`, `getTotal()`, `getTotalByCategory()`, plus helpers. |
| `Expense` | One expense with private `amount`, `category` and `description` fields, getters and `toString()`. |
| `Category` | Enum: `FOOD`, `TRANSPORTATION`, `ENTERTAINMENT`, `OTHER`. |

- The menu loop follows Activity Diagram Figure 1; adding and removing follow Figures 2 and 3.
- Amount and category validation implement FR-3 / UC-7.
- Money is always shown with a `$` and two decimal places (NFR-4).
