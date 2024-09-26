package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static final String FILE_NAME = "expenses.json";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static final Gson GSON = new Gson();

    public static void main(String[] args) {
        if (args.length == 0) return;

        String action = args[0];
        JsonArray expensesArray = new JsonArray();
        int maxId = 0;
        double amount = 0;

        switch (action) {
            case "add":
                addExpense(expensesArray, args[1], ++maxId, amount);
                System.out.println("Adding task");
            case "list":
                System.out.println("Listing tasks");
            case "summary":
                System.out.println("Total expenses: ");
            case "delete":
                System.out.println("Expense deleted successfully");
        }
    }

    private static void addExpense(JsonArray expensesArray, String taskDescription, int id, double amount) {
        LocalDateTime now = LocalDateTime.now();
        JsonObject newExpense = new JsonObject();
        newExpense.addProperty("id", id);
        newExpense.addProperty("description", taskDescription);
        newExpense.addProperty("amount", amount);
        newExpense.addProperty("createdAt", now.format(DATE_FORMAT));
        newExpense.addProperty("updatedAt", now.format(DATE_FORMAT));
        expensesArray.add(newExpense);
        System.out.println("Task added successfully ID: " + newExpense.get("id"));
    }
}