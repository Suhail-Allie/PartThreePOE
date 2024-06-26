package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static String username;
    private static String password;
    private static String firstName;
    private static String lastName;
    private static boolean loggedIn = false;

    private static List<String> developers = new ArrayList<>();
    private static List<String> taskNames = new ArrayList<>();
    private static List<String> taskIDs = new ArrayList<>();
    private static List<Double> taskDurations = new ArrayList<>();
    private static List<String> taskStatuses = new ArrayList<>();

    public static void main(String[] args) {
        register();
        if (login()) {
            displayWelcomeMessage();
            showMenu();
        } else {
            System.out.println("Login required. Exiting the application.");
        }
    }

    public static void register() {
        System.out.println("Welcome to User Registration");
        System.out.println("Please enter your details to register:");

        while (true) {
            System.out.print("Username: ");
            username = scanner.nextLine();
            if (isValidUsername(username)) {
                break;
            } else {
                System.out.println("Invalid username. Must contain an underscore and be no more than 5 characters in length.");
            }
        }

        System.out.print("Password: ");
        password = scanner.nextLine();

        System.out.print("First Name: ");
        firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        lastName = scanner.nextLine();

        System.out.println("Registration Successful");
    }

    public static boolean isValidUsername(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public static boolean login() {
        System.out.println("\nWelcome to Login");
        System.out.println("Please enter your credentials to Login:");

        System.out.print("Username: ");
        String inputUsername = scanner.nextLine();

        System.out.print("Password: ");
        String inputPassword = scanner.nextLine();

        if (inputUsername.equals(username) && inputPassword.equals(password)) {
            System.out.println("Login Successful! Welcome, " + firstName + " " + lastName);
            loggedIn = true;
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    public static void displayWelcomeMessage() {
        System.out.println("\nWelcome to EasyKanban");
    }

    public static void showMenu() {
        int choice;
        do {
            System.out.println("\nMain Menu");
            System.out.println("1. Add tasks");
            System.out.println("2. Show report");
            System.out.println("3. Display tasks with status 'Done'");
            System.out.println("4. Display task with the longest duration");
            System.out.println("5. Search for a task by name");
            System.out.println("6. Search for tasks by developer");
            System.out.println("7. Delete a task by name");
            System.out.println("8. Quit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    addTasks();
                    break;
                case 2:
                    showReport();
                    break;
                case 3:
                    displayTasksWithStatusDone();
                    break;
                case 4:
                    displayTaskWithLongestDuration();
                    break;
                case 5:
                    searchTaskByName();
                    break;
                case 6:
                    searchTasksByDeveloper();
                    break;
                case 7:
                    deleteTaskByName();
                    break;
                case 8:
                    System.out.println("\nThank you for using EasyKanban. Goodbye!");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
                    break;
            }
        } while (choice != 8);
    }

    private static void deleteTaskByName() {
    }

    public static void addTasks() {
        System.out.print("\nEnter the number of tasks you want to enter: ");
        int numTasks = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        for (int i = 0; i < numTasks; i++) {
            System.out.println("\nTask " + (i + 1));

            System.out.print("Task Name: ");
            String taskName = scanner.nextLine();
            taskNames.add(taskName);

            System.out.print("Task Description (max 50 chars): ");
            String taskDescription = scanner.nextLine();
            if (taskDescription.length() > 50) {
                System.out.println("Description too long. Please enter up to 50 characters.");
                i--;
                continue;
            }

            System.out.print("Developer First Name: ");
            String devFirstName = scanner.nextLine();

            System.out.print("Developer Last Name: ");
            String devLastName = scanner.nextLine();
            developers.add(devFirstName + " " + devLastName);

            System.out.print("Task Duration (in hours): ");
            double taskDuration = scanner.nextDouble();
            scanner.nextLine(); // consume the newline character
            taskDurations.add(taskDuration);

            String taskID = generateTaskID(taskName, i, devLastName);
            taskIDs.add(taskID);

            System.out.println("Task ID: " + taskID);

            System.out.println("Task Status Menu");
            System.out.println("1. To Do");
            System.out.println("2. Done");
            System.out.println("3. Doing");
            System.out.print("Select the task status: ");
            int statusChoice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (statusChoice) {
                case 1:
                    taskStatuses.add("To Do");
                    break;
                case 2:
                    taskStatuses.add("Done");
                    break;
                case 3:
                    taskStatuses.add("Doing");
                    break;
                default:
                    System.out.println("Invalid status choice. Defaulting to 'To Do'.");
                    taskStatuses.add("To Do");
                    break;
            }
        }
        System.out.println("All tasks entered.");
    }

    public static String generateTaskID(String taskName, int taskNumber, String devLastName) {
        return taskName.substring(0, 2).toUpperCase() + ":" + taskNumber + ":" + devLastName.substring(devLastName.length() - 3).toUpperCase();
    }

    public static void showReport() {
        System.out.println("\nTask Report:");
        double totalDuration = 0;

        for (int i = 0; i < taskNames.size(); i++) {
            System.out.println("Task ID: " + taskIDs.get(i));
            System.out.println("Task Name: " + taskNames.get(i));
            System.out.println("Developer: " + developers.get(i));
            System.out.println("Task Duration: " + taskDurations.get(i) + " hours");
            System.out.println("Task Status: " + taskStatuses.get(i));
            System.out.println();

            totalDuration += taskDurations.get(i);
        }

        System.out.println("Total Task Duration: " + totalDuration + " hours");
    }

    public static void displayTasksWithStatusDone() {
        System.out.println("\nTasks with Status 'Done':");
        for (int i = 0; i < taskNames.size(); i++) {
            if ("Done".equals(taskStatuses.get(i))) {
                System.out.println("Developer: " + developers.get(i));
                System.out.println("Task Name: " + taskNames.get(i));
                System.out.println("Task Duration: " + taskDurations.get(i) + " hours");
                System.out.println();
            }
        }
    }

    public static void displayTaskWithLongestDuration() {
        if (taskDurations.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        double maxDuration = 0;
        int maxIndex = 0;
        for (int i = 0; i < taskDurations.size(); i++) {
            if (taskDurations.get(i) > maxDuration) {
                maxDuration = taskDurations.get(i);
                maxIndex = i;
            }
        }
        System.out.println("\nTask with Longest Duration:");
        System.out.println("Developer: " + developers.get(maxIndex));
        System.out.println("Task Duration: " + taskDurations.get(maxIndex) + " hours");
    }

    public static void searchTaskByName() {
        System.out.print("\nEnter the Task Name to search: ");
        String taskName = scanner.nextLine();

        for (int i = 0; i < taskNames.size(); i++) {
            if (taskNames.get(i).equalsIgnoreCase(taskName)) {
                System.out.println("Task Name: " + taskNames.get(i));
                System.out.println("Developer: " + developers.get(i));
                System.out.println("Task Status: " + taskStatuses.get(i));
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public static void searchTasksByDeveloper() {
        System.out.print("\nEnter the Developer's name to search tasks: ");
        String devName = scanner.nextLine();

        boolean found = false;
        for (int i = 0; i < developers.size(); i++) {
            if (developers.get(i).equalsIgnoreCase(devName)) {
                System.out.println("Task Name: " + taskNames.get(i));
                System.out.println("Task Status: " + taskStatuses.get(i));
                found = true;
            }
        }

    }
}