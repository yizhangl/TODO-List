package ui.graphical;

import model.Task;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// AddTaskListener is based on ListDemo
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

// Represents a AddTaskListener having a ToDoListGUI and JButton, and helps addTask when clicking button
public class AddTaskListener implements ActionListener, DocumentListener {
    private boolean buttonEnabled = false;

    private ToDoListGUI toDoListGUI;
    private JButton addButton;

    public AddTaskListener(ToDoListGUI toDoListGUI, JButton addButton) {
        this.toDoListGUI = toDoListGUI;
        this.addButton = addButton;
    }

    // REQUIRES: task description is not empty
    // MODIFIES: todoList, listModel
    // EFFECTS: add a task to todolist
    @Override
    public void actionPerformed(ActionEvent e) {
        String description = toDoListGUI.description.getText();
        String deadline = toDoListGUI.deadline.getText();
        int i = toDoListGUI.myToDoList.getSelectedIndex();

        if (i == -1) {
            i = 0;
        } else {
            i++;
        }
        Task task = new Task(description, deadline);
        toDoListGUI.toDoList.addTask(task);
        toDoListGUI.listModel.addElement(task.changeToString());

        toDoListGUI.description.requestFocusInWindow();
        toDoListGUI.deadline.requestFocusInWindow();
        toDoListGUI.description.setText("");
        toDoListGUI.deadline.setText("");
        toDoListGUI.myToDoList.setSelectedIndex(i);
        toDoListGUI.myToDoList.ensureIndexIsVisible(i);
    }


    // EFFECTS: enable the button
    @Override
    public void insertUpdate(DocumentEvent e) {
        enableButton();
    }

    // EFFECTS: call handleEmptyTextField
    @Override
    public void removeUpdate(DocumentEvent e) {
        handleEmptyTextField(e);
    }

    // EFFECTS: if textField is not empty, enable button; otherwise, does nothing
    @Override
    public void changedUpdate(DocumentEvent e) {
        if (!handleEmptyTextField(e)) {
            enableButton();
        }
    }

    // EFFECTS: if button is not enabled, set enabled
    private void enableButton() {
        if (!buttonEnabled) {
            addButton.setEnabled(true);
        }
    }

    // EFFECTS: if the length <= 0, button is set disabled and return true
    private boolean handleEmptyTextField(DocumentEvent e) {
        if (e.getDocument().getLength() <= 0) {
            addButton.setEnabled(false);
            buttonEnabled = false;
            return true;
        }
        return false;
    }

    // EFFECTS: return todolistGUI
    public ToDoListGUI getToDoListGUI() {
        return toDoListGUI;
    }

    // EFFECTS: set GUI
    public void setGUI(ToDoListGUI toDoListGUI) {
        if (getToDoListGUI() != toDoListGUI) {
            this.toDoListGUI = toDoListGUI;
            toDoListGUI.addListener(this);
        }
    }
}

