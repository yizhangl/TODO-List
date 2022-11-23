package model;


import org.json.JSONObject;
import persistence.Writable;

// Represents a task having a description and complete/incomplete status
public class Task implements Writable {
    private String description;
    private String deadline;
    private int isComplete;


    public Task(String description, String deadline) {
        this.description = description;
        this.deadline = deadline;
        isComplete = 0;
    }

    // EFFECTS: returns description
    public String getDescription() {
        return description;
    }

    // EFFECTS: returns deadline
    public String getDeadline() {
        return deadline;
    }


    // EFFECTS: returns 1 if task is completed, 0 otherwise
    public int getIsComplete() {
        return isComplete;
    }

    // MODIFIES: this
    // EFFECTS: set complete of the task
    public void setComplete() {
        isComplete = 1;
    }

    // REQUIRES: isComplete()
    // MODIFIES: this
    // EFFECTS: set incomplete of the task
    public void setIncomplete() {
        isComplete = 0;
    }


    // EFFECTS: return a string representation of task
    public String changeToString() {
        return description + " " + deadline + " " + isComplete;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("description", description);
        json.put("deadline", deadline);
        json.put("isComplete", isComplete);
        return json;
    }
}