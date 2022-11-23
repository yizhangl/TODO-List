package persistence;

import model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

// JsonTest is based on JsonTest in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {
    protected void checkTasks(String description, String deadline, Task task) {
        assertEquals(description, task.getDescription());
        assertEquals(deadline, task.getDeadline());
    }
}
