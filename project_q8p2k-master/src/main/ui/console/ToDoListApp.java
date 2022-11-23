package ui.console;

import model.Task;
import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// ToDoListApp is based on TellerApp
// https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

// ToDoListApp is also based on WorkRoomApp in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// ToDoList application
public class ToDoListApp {
    private static final String JSON_STORE = "./data/workroom.json";
    private ToDoList toDoList;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the todoList application
    public ToDoListApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        toDoList = new ToDoList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runToDo();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runToDo() {
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGood Job! :)");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddTask();
        } else if (command.equals("v")) {
            doViewTasks();
        } else if (command.equals("sc")) {
            doSetCompleteTask();
        } else if (command.equals("si")) {
            doSetIncompleteTask();
        } else if (command.equals("d")) {
            doDeleteTask();
        } else if (command.equals("n")) {
            doSeeNumberOfTasks();
        } else if (command.equals("c")) {
            doClearToDoList();
        } else if (command.equals("s")) {
            saveToDoList();
        } else if (command.equals("l")) {
            loadToDoList();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        toDoList = new ToDoList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> addTask");
        System.out.println("\tv -> viewTasks");
        System.out.println("\tsc -> setCompleteTask");
        System.out.println("\tsi -> setIncompleteTask");
        System.out.println("\td -> deleteTask");
        System.out.println("\tn -> doSeeNumberOfTasks (including complete/incomplete/all tasks)");
        System.out.println("\tc -> clearToDoList");
        System.out.println("\ts -> save work room to file");
        System.out.println("\tl -> load work room from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: conduct an addTask action
    private void doAddTask() {
        System.out.print("Enter description of the new task");
        String description = input.next();
        System.out.print("Enter deadline of the new task");
        String deadline = input.next();

        Task t = new Task(description, deadline);
        toDoList.addTask(t);
        System.out.println("You've added a new Task  :)");
    }


    // MODIFIES: this
    // EFFECTS: conduct a viewing all tasks action
    private void doViewTasks() {
        System.out.println(toDoList.viewTasks());
    }

    // MODIFIES: this
    // EFFECTS: conduct a setting complete task action
    private void doSetCompleteTask() {
        System.out.println("Enter the position of task that you want to set complete (first one is 1)");
        int position = input.nextInt();
        int index = position - 1;

        if (toDoList.length() >= position) {
            Task task = toDoList.getIndex(index);
            toDoList.markCompleteTask(task);
            System.out.println("This task has been set complete :)");
        } else {
            System.out.println("Can't find that task!");
        }
    }

    // MODIFIES: this
    // EFFECTS: conduct a setting incomplete task action
    private void doSetIncompleteTask() {
        System.out.println("Enter the position of task that you want to set incomplete (first one is 1)");
        int position = input.nextInt();
        int index = position - 1;

        if (toDoList.length() >= position) {
            Task task = toDoList.getIndex(index);
            toDoList.setIncompleteTask(task);
            System.out.println("This task has been set incomplete :)");
        } else {
            System.out.println("Can't find that task!");
        }
    }

    // MODIFIES: this
    // EFFECTS: conduct a deleteTask action
    private void doDeleteTask() {
        System.out.println("Enter the position of task that you want to delete (first one is 1)");
        int position = input.nextInt();
        int index = position - 1;

        if (toDoList.length() >= position) {
            Task task = toDoList.getIndex(index);
            toDoList.deleteTask(task);
            System.out.println("This task has been deleted :)");
        } else {
            System.out.println("Can't find that task!");
        }
    }


    // MODIFIES: this
    // EFFECTS: conduct a seeing number of complete/incomplete/all tasks
    private void doSeeNumberOfTasks() {
        System.out.println("Number of complete tasks: " + toDoList.seeNumberOfCompleteTasks() + "  "
                + "Number of incomplete tasks: " + toDoList.seeNumberOfIncompleteTasks() + "  "
                + "Number of all tasks: " + toDoList.length());
    }

    // MODIFIES: this
    // EFFECTS: conduct a clearing todolist action
    private void doClearToDoList() {
        toDoList.clearToDoList();
        System.out.println("This ToDoList is now empty :)");
    }

    // EFFECTS: saves the todolist to file
    private void saveToDoList() {
        try {
            jsonWriter.open();
            jsonWriter.write(toDoList);
            jsonWriter.close();
            System.out.println("Saved Licy's todoList to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads todolist from file
    private void loadToDoList() {
        try {
            toDoList = jsonReader.read();
            System.out.println("Loaded Licy's todoList from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}


