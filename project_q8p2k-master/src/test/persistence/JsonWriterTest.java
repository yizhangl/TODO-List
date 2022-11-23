package persistence;


import model.Task;
import model.ToDoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// JsonWriterTest is based on JsonWriterTest in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ToDoList todo = new ToDoList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyToDoList() {
        try {
            ToDoList todo = new ToDoList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTodoList.json");
            writer.open();
            writer.write(todo);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTodoList.json");
            todo = reader.read();
            assertEquals(0, todo.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralToDoList() {
        try {
            ToDoList todo = new ToDoList();
            todo.addTask(new Task("Midterm1", "Oct. 27"));
            todo.addTask(new Task("Midterm2", "Oct. 28"));
            todo.markCompleteTask(todo.getIndex(0));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTodoList.json");
            writer.open();
            writer.write(todo);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTodoList.json");
            todo = reader.read();
            List<Task> tasks = todo.getTasks();
            assertEquals(2, tasks.size());
            checkTasks("Midterm1", "Oct. 27", tasks.get(0));
            assertEquals(1, tasks.get(0).getIsComplete());
            checkTasks("Midterm2", "Oct. 28", tasks.get(1));
            assertEquals(0, tasks.get(1).getIsComplete());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}