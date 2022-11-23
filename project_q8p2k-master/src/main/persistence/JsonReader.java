package persistence;

import model.Task;
import model.ToDoList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// JsonReader is based on JsonReader in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a reader that reads ToDoList from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }


    // EFFECTS: reads ToDoList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ToDoList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseToDoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // EFFECTS: parses todolist from JSON object and returns it
    private ToDoList parseToDoList(JSONObject jsonObject) {
        ToDoList toDoList = new ToDoList();
        addTasks(toDoList, jsonObject);
        return toDoList;
    }


    // MODIFIES: ToDoList
    // EFFECTS: parses tasks from JSON object and adds them to todolist
    private void addTasks(ToDoList todo, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object t : jsonArray) {
            JSONObject nextThingy = (JSONObject) t;
            addTask(todo, nextThingy);
        }
    }

    // MODIFIES: ToDoList
    // EFFECTS: parses task from JSON object and adds it to todolist
    private void addTask(ToDoList todo, JSONObject jsonObject) {
        String description = jsonObject.getString("description");
        String deadline = jsonObject.getString("deadline");
        int isComplete = jsonObject.getInt("isComplete");
        Task task = new Task(description, deadline);
        if (isComplete == 1) {
            task.setComplete();
        }
        todo.addTask(task);
    }
}
