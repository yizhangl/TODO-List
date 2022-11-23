package persistence;

import org.json.JSONObject;

// Writable is based on Writable in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
