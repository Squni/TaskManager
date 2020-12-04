package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static void main (String[] args) {
        Scanner systemScan = new Scanner(System.in);
        Path listPath = Paths.get("list.csv");
        String[][] taskList = getData(listPath);
        String userChoice = "";
        while (!userChoice.equals("exit")) {
            showMenu();
            userChoice = getChoice(systemScan);
            if (userChoice.equals("add")) {
                taskList = addTask(systemScan, taskList);
            } else if (userChoice.equals("remove")) {
                taskList = removeTask(systemScan, taskList);
            } else if (userChoice.equals("list")) {
                showList(taskList);
            }
        } systemScan.close();
        saveData(taskList, listPath);
        System.out.println(ConsoleColors.RED + "See you later!" + ConsoleColors.RESET);
    }

    public static void showMenu() {
        System.out.println(ConsoleColors.BLUE + "Please select an option: " + ConsoleColors.RESET);
        System.out.println("add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit\n");

    }

    public static String[][] listToArray(ArrayList<String> list) {
        String[][] tasks = new String[list.size()][3];
        String[] line = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            line = list.get(i).split(",");
            for (int j = 0; j < line.length; j++) {
                tasks[i][j] = line[j];
            }
        } return tasks;

    }

    public static ArrayList<String> arrayToList (String[][] arr) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                sb.append(arr[i][j]);
                if (j != arr[i].length - 1) {
                    sb.append(",");
                }

            }
            list.add(String.valueOf(sb));
            sb.setLength(0);
        }
        return list;
    }

    public static String[][] getData(Path taskList) {
        ArrayList<String> list =  new ArrayList<String>();
        if (!Files.exists(taskList)) {
            try {
                Files.createFile(taskList);

            } catch (IOException e) {
                System.out.println(ConsoleColors.RED + "Something went wrong." + ConsoleColors.RESET);

            }
            String[][] emptyList = {};
            return  emptyList;

            }
        Scanner scan = null;
        try {
            scan = new Scanner(taskList);

        } catch (IOException e) {
            System.out.printf(ConsoleColors.RED + "Something went wrong. Cannot read file %s", String.valueOf(taskList) + ConsoleColors.RESET);

        }
        while(scan.hasNextLine()) {
            list.add(scan.nextLine());

        }
        String[][] tasks = listToArray(list);
        return tasks;
    }

    public static void saveData(String[][] taskList, Path savedList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taskList.length; i++) {
            for (int j = 0; j < taskList[i].length; j++) {
                sb.append(taskList[i][j]);
                if (j != taskList[i].length -1) {
                    sb.append(",");
                }

            } sb.append("\n");
        }
        try (FileWriter fw = new FileWriter(String.valueOf(savedList), false)) {
            fw.append(sb);
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Something went wrong. File not saved." + ConsoleColors.RESET);
        }

    }

    public static String getChoice(Scanner scan) {
        String[] options = {"add", "remove", "list", "exit"};
        String choice = "";
        while (true) {
            System.out.print("Go to: ");
            choice = scan.nextLine();
            for (int i = 0; i < options.length; i++) {
                if (options[i].equals(choice)) {
                    return choice;
                }
            }
            System.out.println(ConsoleColors.RED + "Option chosen invalid. Please try again." + ConsoleColors.RESET);
        }

    }

    public static String[][] addTask(Scanner scan, String[][] taskList) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> list = arrayToList(taskList);
        System.out.print("\nPlease add task description: ");
        sb.append(scan.nextLine()).append(",");
        System.out.print("Please add task due date: ");
        String date = scan.nextLine();
        while (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println(ConsoleColors.RED + "Invalid date format. Please try:  YYYY-MM-DD." + ConsoleColors.RESET);
                System.out.print("Please add task due date: ");
                date = scan.nextLine();
            }
        sb.append(date).append(",");
        System.out.print("Is your task important (true/false): ");
        String priority = scan.nextLine();
        while (!priority.equals("true") && !priority.equals("false")) {
            System.out.println(ConsoleColors.RED + "Invalid input. Please try:  true / false." + ConsoleColors.RESET);
            System.out.print("Is your task important (true/false): ");
            priority = scan.nextLine();
        } sb.append(priority);
        list.add(String.valueOf(sb));
        System.out.println(ConsoleColors.GREEN + "Task successfully added.\n" + ConsoleColors.RESET);
        return listToArray(list);
    }

    public static void showList(String[][] list) {
        int index = 0;
        System.out.println();
        for (String[] row: list) {
            System.out.print(index + " : ");
            for (String pos: row) {
                System.out.print(pos + " ");
            }
            index++;
            System.out.println();

        }
        System.out.println();
    }

    public static String[][] removeTask(Scanner scan, String[][] taskList) {
        ArrayList<String> list = arrayToList(taskList);
        System.out.print("\nPlease select number to remove: ");
        int input = 0;
        while (true) {
            try {
                input = Integer.parseInt(scan.nextLine());
                if (input < 0 || input >= list.size()){
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println(ConsoleColors.RED + "Task with that index doesn't exist." + ConsoleColors.RESET);
            } System.out.print("Please select number to remove: ");
        } list.remove(input);
        System.out.println(ConsoleColors.GREEN + "Task successfully removed.\n" + ConsoleColors.RESET);
        return listToArray(list);
    }


}
