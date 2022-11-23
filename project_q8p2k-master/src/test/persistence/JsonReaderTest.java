package persistence;


import model.Task;
import model.ToDoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// JsonReaderTest is based on JsonReaderTest in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ToDoList todo = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyToDoList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTodoList.json");
        try {
            ToDoList todo = reader.read();
            assertEquals(0, todo.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralToDoList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTodoList.json");
        try {
            ToDoList todo = reader.read();
            List<Task> todoList = todo.getTasks();
            assertEquals(2, todoList.size());
            checkTasks("Midterm1", "Oct. 27", todoList.get(0));
            assertEquals(1, todoList.get(0).getIsComplete());
            checkTasks("Midterm2", "Oct. 28", todoList.get(1));
            assertEquals(0, todoList.get(1).getIsComplete());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
