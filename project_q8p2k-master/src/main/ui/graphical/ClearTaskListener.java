package ui.graphical;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a ClearTaskListener having a ToDoListGUI, and helps clearTask when clicking button
public class ClearTaskListener implements ActionListener {
    private ToDoListGUI toDoListGUI;

    public ClearTaskListener(ToDoListGUI toDoListGUI) {
        this.toDoListGUI = toDoListGUI;
    }

    // MODIFIES: todolist, listModel
    // EFFECTS: clear the list
    @Override
    public void actionPerformed(ActionEvent e) {
        toDoListGUI.listModel.clear();
        toDoListGUI.toDoList.clearToDoList();
    }
}
