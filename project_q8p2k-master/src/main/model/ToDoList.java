package model;

// A to-do list application

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// Represents a queue of tasks
public class ToDoList implements Writable {
    private ArrayList<Task> todoList;

    public ToDoList() {
        todoList = new ArrayList<>();
    }

    // MODIFIES:this
    // EFFECTS: add the new task to the end
    public void addTask(Task t) {
        todoList.add(t);
        Event event = new Event("Task: [" + t.changeToString() + "]" + " is added to the todoList");
        EventLog.getInstance().logEvent(event);
    }


    // EFFECTS: go through every task, convert it to a string, and add it to result list of strings
    public ArrayList<String> viewTasks() {
        ArrayList<String> taskString = new ArrayList<>();
        int l = this.length();
        for (int i = 0; i < l; i++) {
            Task everyTask = this.getIndex(i);
            taskString.add(everyTask.changeToString());
        }
        return taskString;
    }

    // REQUIRES: a Task called t
    // MODIFIES: t
    // EFFECTS: set complete of the Task t
    public void markCompleteTask(Task t) {
        t.setComplete();
        Event event = new Event("Task: [" + t.changeToString() + "]" + " is marked as complete");
        EventLog.getInstance().logEvent(event);
    }

    // REQUIRES: a Task called t
    // MODIFIES: t
    // EFFECTS: set incomplete of the Task t
    public void setIncompleteTask(Task t) {
        t.setIncomplete();
    }

    // REQUIRES: a Task called task
    // MODIFIES: this
    // EFFECTS: removes the task from todoList
    public void deleteTask(Task task) {
        todoList.remove(task);
        Event event = new Event("Task: [" + task.changeToString() + "]" + " is deleted from the todoList");
        EventLog.getInstance().logEvent(event);
    }


    // EFFECTS: see number of complete tasks
    public int seeNumberOfCompleteTasks() {
        int c = 0;
        for (Task t: todoList) {
            if (t.getIsComplete() == 1) {
                c = c + 1;
            }
        }
        return c;
    }

    // EFFECTS: see number of incomplete tasks
    public int seeNumberOfIncompleteTasks() {
        int i = 0;
        for (Task t : todoList) {
            if (t.getIsComplete() == 0) {
                i = i + 1;
            }
        }
        return i;
    }


    // EFFECTS: returns an int that represents the number of tasks in the todoList
    public int length() {
        return todoList.size();
    }


    // MODIFIES: this
    // EFFECTS: clear the todoList
    public void clearToDoList() {
        todoList.clear();
        Event event = new Event("Licy's todoList is cleared");
        EventLog.getInstance().logEvent(event);
    }


    // REQUIRES: index called ind
    // EFFECTS: get the task with index called ind
    public Task getIndex(int ind) {
        return todoList.get(ind);
    }


    // EFFECTS: returns an unmodifiable list of tasks in this todolist
    public List<Task> getTasks() {
        return Collections.unmodifiableList(todoList);
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tasks", tasksToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : todoList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: change the Event into a string
    public void printLog() {
        for (Event e: EventLog.getInstance()) {
            System.out.println(e.toString());
        }
    }
}
