package org.example;

import org.json.JSONArray;

public class Main {
    private static final String FILE_NAME = "expenses.json";

    public static void main(String[] args) {
        if (args.length == 0) return;

        String action = args[0];
        JSONArray expensesArray = new JSONArray();
        Integer maxId = 0;

        switch (action) {
            case "add":
                System.out.println("Adding task");
            case "list":
                System.out.println("Listing tasks");
            case "summary":
                System.out.println("Total expenses: ");
            case "delete":
                System.out.println("Expense deleted successfully");
        }
    }
}