# expense-tracker

[PROJECT URL](https://roadmap.sh/projects/expense-tracker)

A simple Command Line Interface (CLI) application to help you manage your expenses efficiently. This tool allows you to add, delete, and list expenses.

## Features

- **Add Expense:** Create new tasks with a unique identifier.
- **Delete Expense:** Remove tasks by their unique ID.
- **View All Expense:** Display all tasks or filter them by status (e.g., show only completed tasks).
- **View A Summary of Expenses:** Display all tasks or filter them by status (e.g., show only completed tasks).
- **View A Summary of Expenses for a specific month:** Display all tasks or filter them by status (e.g., show only completed tasks).

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher

### Installation

1. **Clone the Repository:**

   ```bash
   git clone git@github.com:Sean-Thomo/expense-tracker.git
   cd expense-tracker
   ```

2. **Install packages:**

   ```bash
   mvn install
   ```

3. **Run application:**

   ```bash
   mvn exec:java -Dexec.mainClass="com.expense-tracker.Main" -Dexec.args="<command> '<args>'"

   #Example
   #mvn exec:java -Dexec.mainClass="com.expense-tracker.Main" -Dexec.args=" add --description "Lunch" --amount 20"
   #mvn exec:java -Dexec.mainClass="com.expense-tracker.Main" -Dexec.args="list"
   #mvn exec:java -Dexec.mainClass="com.expense-tracker.Main" -Dexec.args="delete --id 1"
   ```

### Usage

```bash
$ expense-tracker add --description "Lunch" --amount 20
# Expense added successfully (ID: 1)

$ expense-tracker add --description "Dinner" --amount 10
# Expense added successfully (ID: 2)

$ expense-tracker list
# ID  Date       Description  Amount
# 1   2024-08-06  Lunch        $20
# 2   2024-08-06  Dinner       $10

$ expense-tracker summary
# Total expenses: $30

$ expense-tracker delete --id 1
# Expense deleted successfully

$ expense-tracker summary
# Total expenses: $20

$ expense-tracker summary --month 8
# Total expenses for August: $20
```

### Expense Properties

Each expense is stored with the following properties:

- `id`: A unique identifier for the task.
- `description`: A short description of the task.
- `amount`: 
- `createdAt`: The date and time when the task was created.
- `updatedAt`: The date and time when the task was last updated.
