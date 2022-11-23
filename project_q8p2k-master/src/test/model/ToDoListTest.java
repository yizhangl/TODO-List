package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ToDoListTest {
    private ToDoList toDoList;
    private Task t1;
    private Task t2;
    private Task t3;
    private Task t4;


    @BeforeEach
    void setup() {
        toDoList = new ToDoList();

        t1 = new Task("Math", "Oct. 10");
        t2 = new Task("Phys", "Oct. 13");
        t3 = new Task("Stat", "Oct. 15");
        t4 = new Task("Cp", "Oct. 20");
    }


    @Test
    void testConstructor() {
        assertEquals(0, toDoList.length());
    }

    @Test
    void testAddTask() {
        assertEquals(0, toDoList.length());

        toDoList.addTask(t1);
        toDoList.addTask(t3);


        assertEquals(2, toDoList.length());

        toDoList.addTask(t2);
        toDoList.addTask(t4);

        assertEquals(4, toDoList.length());
    }

    @Test
    void testViewTasks() {

        toDoList.addTask(t1);
        toDoList.addTask(t2);
        toDoList.addTask(t4);

        toDoList.markCompleteTask(t4);

        ArrayList<String> arrayString = toDoList.viewTasks();

        assertEquals("Math Oct. 10 0", arrayString.get(0));
        assertEquals("Phys Oct. 13 0", arrayString.get(1));
        assertEquals("Cp Oct. 20 1", arrayString.get(2));
    }

    @Test
    void testMarkCompleteTask() {
        toDoList.addTask(t1);
        toDoList.addTask(t3);
        toDoList.addTask(t2);
        toDoList.addTask(t4);


        toDoList.markCompleteTask(t2);

        assertEquals(1, t2.getIsComplete());
        assertEquals(0, t3.getIsComplete());
    }

    @Test
    void testSetIncompleteTask() {
        toDoList.addTask(t1);
        toDoList.addTask(t3);
        toDoList.addTask(t2);
        toDoList.addTask(t4);

        toDoList.markCompleteTask(t3);
        toDoList.setIncompleteTask(t1);

        assertEquals(0, t2.getIsComplete());
        assertEquals(0, t1.getIsComplete());
        assertEquals(1, t3.getIsComplete());

        toDoList.setIncompleteTask(t3);

        assertEquals(0, t3.getIsComplete());
    }

    @Test
    void testDeleteTask() {
        toDoList.addTask(t1);
        toDoList.addTask(t3);
        toDoList.addTask(t2);
        toDoList.addTask(t4);


        toDoList.deleteTask(t3);
        assertEquals(3, toDoList.length());
    }

    @Test
    void testSeeNumberOfCompleteTasks() {
        toDoList.addTask(t1);
        toDoList.addTask(t3);
        toDoList.addTask(t2);
        toDoList.addTask(t4);

        assertEquals(0, toDoList.seeNumberOfCompleteTasks());

        toDoList.markCompleteTask(t1);
        assertEquals(1, toDoList.seeNumberOfCompleteTasks());

        toDoList.markCompleteTask(t2);
        assertEquals(2, toDoList.seeNumberOfCompleteTasks());
    }


    @Test
    void testSeeNumberOfIncompleteTasks() {
        toDoList.addTask(t1);
        toDoList.addTask(t3);
        toDoList.addTask(t2);
        toDoList.addTask(t4);

        assertEquals(4, toDoList.seeNumberOfIncompleteTasks());

        toDoList.setIncompleteTask(t1);
        assertEquals(4, toDoList.seeNumberOfIncompleteTasks());

        toDoList.markCompleteTask(t2);
        assertEquals(3, toDoList.seeNumberOfIncompleteTasks());

        toDoList.setIncompleteTask(t2);
        assertEquals(4, toDoList.seeNumberOfIncompleteTasks());
    }

    @Test
    void testLength() {
        assertEquals(0, toDoList.length());

        toDoList.addTask(t1);

        assertEquals(1, toDoList.length());

        toDoList.addTask(t3);
        toDoList.addTask(t2);
        toDoList.addTask(t4);

        assertEquals(4, toDoList.length());
    }


    @Test
    void testClearToDoList() {
        toDoList.addTask(t1);
        toDoList.addTask(t3);
        toDoList.addTask(t2);
        toDoList.addTask(t4);

        assertEquals(4, toDoList.length());

        toDoList.clearToDoList();
        assertEquals(0, toDoList.length());
    }

    @Test
    void testGetIndex() {
        toDoList.addTask(t1);
        toDoList.addTask(t3);
        toDoList.addTask(t2);
        toDoList.addTask(t4);

        assertEquals(t2, toDoList.getIndex(2));
        assertEquals(t3, toDoList.getIndex(1));
    }

}