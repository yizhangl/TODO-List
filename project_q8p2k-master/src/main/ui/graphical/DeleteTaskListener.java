package ui.graphical;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// DeleteTaskListener is based on ListDemo
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

// Represents a DeleteTaskListener having a ToDoListGUI, and helps deleteTask when clicking button
public class DeleteTaskListener implements ActionListener {
    private ToDoListGUI toDoListGUI;

    public DeleteTaskListener(ToDoListGUI toDoListGUI) {
        this.toDoListGUI = toDoListGUI;
    }

    // MODIFIES: todolist, listModel
    // EFFECTS: delete the selected task from todolist
    @Override
    public void actionPerformed(ActionEvent e) {
        int size = toDoListGUI.listModel.getSize();
        int i = toDoListGUI.myToDoList.getSelectedIndex();

        toDoListGUI.toDoList.deleteTask(toDoListGUI.toDoList.getIndex(i));
        toDoListGUI.listModel.remove(i);

        if (size == 0) {
            toDoListGUI.deleteButton.setEnabled(false);
        } else {
            toDoListGUI.deleteButton.setEnabled(true);
            if (i == size) {
                i--;
            }
            toDoListGUI.myToDoList.setSelectedIndex(i);
            toDoListGUI.myToDoList.ensureIndexIsVisible(i);
        }

    }

}
