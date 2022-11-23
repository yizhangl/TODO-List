package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    private Task task;

    @BeforeEach
    void runBefore() {
        task = new Task("Math", "Oct. 20");
    }

    @Test
    void testConstructor() {
        assertEquals("Math", task.getDescription());
        assertEquals("Oct. 20", task.getDeadline());
        assertEquals(0, task.getIsComplete());
    }

    @Test
    void testConstructorNotComplete() {
        Task t = new Task("English", "Oct. 23");
        t.setComplete();
        assertEquals("English", t.getDescription());
        assertEquals("Oct. 23", t.getDeadline());
        assertEquals(1, t.getIsComplete());
    }


    @Test
    void testSetComplete() {
        task.setComplete();
        assertEquals(1, task.getIsComplete());
    }

    @Test
    void testSetIncomplete() {
        task.setIncomplete();
        assertEquals(0, task.getIsComplete());
    }

    @Test
    void testChangeToString() {
        assertTrue(task.changeToString().contains("Math Oct. 20 0"));
    }
}
