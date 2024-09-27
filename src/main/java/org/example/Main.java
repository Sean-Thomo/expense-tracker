package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    private static final String FILE_NAME = "expenses.json";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static final Gson GSON = new Gson();

    public static void main(String[] args) {
        if (args.length == 0) return;

        String action = args[0];
        JsonArray expensesArray = new JsonArray();
        JsonArray newExpenseArray = new JsonArray();
        int maxId = 0;

        try (FileReader reader = new FileReader(FILE_NAME)) {
//            JsonParser jsonParser = new JsonParser();
            expensesArray = (JsonArray) JsonParser.parseReader(reader);
            for (int i = 0; i < expensesArray.size(); i++) {
                JsonObject item = expensesArray.get(i).getAsJsonObject();
                int currentId = item.get("id").getAsInt();
                if (currentId > maxId) {
                    maxId = currentId;
                }
            }
        } catch (Exception e) {
            System.out.println("No existing file found, creating a new one.");
        }

        switch (action) {
            case "add":
                newExpenseArray = addExpense(expensesArray, ++maxId, args);
                saveExpenses(expensesArray);
            case "list":
                displayList(newExpenseArray);
                System.out.println("Listing Expenses:");
            case "summary":
                System.out.println("Total expenses: ");
            case "delete":
                System.out.println("Expense deleted successfully");
        }
    }

    private static JsonArray addExpense(JsonArray expensesArray, int id, String[] args) {
        if(Objects.equals(args[1], "--description") & Objects.equals(args[3], "--amount")) {
            String taskDescription = args[2];
            double amount = Double.parseDouble(args[4]);
            LocalDateTime now = LocalDateTime.now();
            JsonObject newExpense = new JsonObject();
            newExpense.addProperty("id", id);
            newExpense.addProperty("description", taskDescription);
            newExpense.addProperty("amount", amount);
            newExpense.addProperty("createdAt", now.format(DATE_FORMAT));
            newExpense.addProperty("updatedAt", now.format(DATE_FORMAT));
            System.out.println("Task added successfully ID: " + newExpense.get("id"));
            expensesArray.add(newExpense);
        } else {
            System.out.println("Please check your arguments");
            System.out.println(Arrays.toString(args));
        }
        return expensesArray;
    }

    private static void displayList(JsonArray newExpenseArray) {
        if(newExpenseArray.isEmpty()) return;
        System.out.println("ID       DATE           DESCRIPTION     AMOUNT");
        for (int i = 0; i < newExpenseArray.size(); i++) {
            System.out.println();
        }
    }

    private static void saveExpenses(JsonArray expensesArray) {
        System.out.println("Saving Expense to " + FILE_NAME);
        try (FileWriter file = new FileWriter(FILE_NAME)) {
            file.write(GSON.toJson(expensesArray));
            file.flush();
            System.out.println("Expense added successfully.");
        } catch (IOException e) {
            System.out.println("Error saving expense: " + e.getMessage());
        }
    }
}