package ui.console;

import java.io.FileNotFoundException;

// run the console app
public class Main {
    public static void main(String[] args) {
        try {
            new ToDoListApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
