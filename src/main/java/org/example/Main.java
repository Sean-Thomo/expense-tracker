package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class Main {
    private static final String FILE_NAME = "expenses.json";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final Gson GSON = new Gson();

    public static void main(String[] args) {
        if (args.length == 0) return;

        String action = args[0];
        JsonArray expensesArray = new JsonArray();
        int maxId = 0;

        try (FileReader reader = new FileReader(FILE_NAME)) {
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
                addExpense(expensesArray, ++maxId, args);
                break;
            case "list":
                displayList(expensesArray);
                break;
            case "summary":
                sumExpense(expensesArray, args.length == 3 ? args[2] == null ? 0 : Integer.parseInt(args[2]) : null);
                break;
            case "delete":
                deleteExpense(expensesArray, args[1], Integer.parseInt(args[2]));
                break;
            case "filter":
                filter(expensesArray, args[1]);
                break;
        }
    }

    private static void filter(JsonArray expensesArray, String category) {
        System.out.println("ID   Date            Description      Amount");

        expensesArray.forEach(expense -> {
            JsonObject expenseObj = expense.getAsJsonObject();
            String id = String.valueOf(expense.getAsJsonObject().get("id"));
            String createdAt = String.valueOf(expense.getAsJsonObject().get("createdAt"));
//            LocalDate date = LocalDate.parse(createdAt, DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH));
            String description = String.valueOf(expense.getAsJsonObject().get("description"));
            Double amount = Double.valueOf(String.valueOf(expense.getAsJsonObject().get("amount")));

            System.out.println(id + "    " + createdAt + "    " + description + "          " + amount);
        });
    }

    private static void deleteExpense(JsonArray expensesArray, String arg, Integer id) {
        boolean expenseFound = false;
        if (Objects.equals(arg, "--id")) {
            for (int i = 0; i < expensesArray.size(); i++) {
                JsonObject expense = expensesArray.get(i).getAsJsonObject();
                if (expense.get("id").getAsInt() == id) {
                    expenseFound = true;
                    expensesArray.remove(i);
                    saveExpenses(expensesArray);
                }
            }
        } else {
            System.out.println("Please check your arguments.");
        }

        if (!expenseFound) {
            System.out.println("Expense not found.");
        }
    }

    private static void sumExpense(JsonArray expensesArray, Integer month) {
        final double[] total = {0};

        if (month == null) {
            expensesArray.forEach(expense ->
                    total[0] += Double.parseDouble(String.valueOf(expense.getAsJsonObject().get("amount")))
            );
            System.out.println("Total expenses: R " + total[0]);
        } else {
            expensesArray.forEach(expense -> {
                JsonObject expenseObj = expense.getAsJsonObject();
                String createdAt = expenseObj.get("createdAt").getAsString();

                LocalDate date = LocalDate.parse(createdAt, DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH));

                if (date.getMonthValue() == month) {
                    total[0] += Double.parseDouble(String.valueOf(expenseObj.get("amount")));
                }
            });

            if (total[0] > 0) {
                System.out.println("Total expenses for " + Month.of(month) + ": R " + total[0]);
            } else {
                System.out.println("No expenses found for " + Month.of(month));
            }
        }
    }

    private static void addExpense(JsonArray expensesArray, int id, String[] args) {
        if(Objects.equals(args[1], "--description") & Objects.equals(args[3], "--amount")) {

            String taskDescription = args[2];
            double amount = Double.parseDouble(args[4]);
            LocalDateTime now = LocalDateTime.now();
            JsonObject newExpense = new JsonObject();

            if (args.length == 6) {
                String category = args[5];
                newExpense.addProperty("category", category);
            }
            newExpense.addProperty("id", id);
            newExpense.addProperty("description", taskDescription);
            newExpense.addProperty("amount", amount);
            newExpense.addProperty("createdAt", now.format(DATE_FORMAT));
            newExpense.addProperty("updatedAt", now.format(DATE_FORMAT));
            System.out.println("Task added successfully ID: " + newExpense.get("id"));
            expensesArray.add(newExpense);
            saveExpenses(expensesArray);
        } else {
            System.out.println("Please check your arguments.");
        }
    }

    private static void displayList(JsonArray expensesArray) {
        System.out.println("ID       DATE            DESCRIPTION     AMOUNT");
        expensesArray.forEach(expense -> System.out.println(
                expense.getAsJsonObject().get("id") + "        "
                        + expense.getAsJsonObject().get("updatedAt") + "    "
                        + expense.getAsJsonObject().get("description") + "         R "
                        + expense.getAsJsonObject().get("amount")));
    }

    private static void saveExpenses(JsonArray expensesArray) {
        try (FileWriter file = new FileWriter(FILE_NAME)) {
            file.write(GSON.toJson(expensesArray));
            file.flush();
        } catch (IOException e) {
            System.out.println("Error saving expense: " + e.getMessage());
        }
    }
}