package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                addTask(systemScan, taskList);
            } else if (userChoice.equals("remove")) {
                //removeTask();
            } else if (userChoice.equals("list")) {
                showList(taskList);
            }
        } systemScan.close();
        saveData(taskList, listPath);
    }

    public static void showMenu() {
        System.out.println(ConsoleColors.BLUE + "Please select an option: " + ConsoleColors.RESET);
        System.out.println("add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit\n");

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
        String[][] tasks = new String[list.size()][3];
        String[] line = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            line = list.get(i).split(",");
            for (int j = 0; j < line.length; j++) {
                tasks[i][j] = line[j];
            }
        }
        return tasks;
    }

    public static void saveData(String[][] taskList, Path savedList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taskList.length; i++) {
            for (int j = 0; j < taskList[i].length; j++) {
                if (j == taskList.length -1) {
                    sb.append(taskList[i][j]);
                }
                sb.append(taskList[i][j]).append(",");
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
            System.out.print("\nGo to: ");
            choice = scan.nextLine();
            for (int i = 0; i < options.length; i++) {
                if (options[i].equals(choice)) {
                    return choice;
                }
            }
            System.out.println(ConsoleColors.RED + "Option chosen invalid. Please try again." + ConsoleColors.RESET);
        }

    }

    public static void addTask(Scanner scan, String[][] taskList) {
        //todo add task to the list

    }

    public static void showList(String[][] list) {
        int index = 0;
        for (String[] row: list) {
            System.out.print(index + " : ");
            for (String pos: row) {
                System.out.print(pos + " ");
            }
            index++;
            System.out.println();

        }
    }
//
//    public static void removeTask(Scanner scan) {
//
//
//    }


}
