package ui.graphical;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a CompleteTaskListener having a ToDoListGUI, and helps completeTask when clicking button
public class CompleteTaskListener implements ActionListener {
    private ToDoListGUI toDoListGUI;

    public CompleteTaskListener(ToDoListGUI toDoListGUI) {
        this.toDoListGUI = toDoListGUI;
    }

    // MODIFIES: todolist, listModel, task
    // EFFECTS: set the selected task as complete
    @Override
    public void actionPerformed(ActionEvent e) {
        int size = toDoListGUI.listModel.getSize();
        int i = toDoListGUI.myToDoList.getSelectedIndex();

        String wholeTask = toDoListGUI.listModel.getElementAt(i);
        String lastCharacter = wholeTask.substring(wholeTask.length() - 1);
        String withoutLastCha = wholeTask.substring(0, wholeTask.length() - 1);

        if (size == 0 || lastCharacter.equals("1")) {
            toDoListGUI.setComplete.setEnabled(false);
        } else {
            toDoListGUI.setComplete.setEnabled(true);
            toDoListGUI.toDoList.markCompleteTask(toDoListGUI.toDoList.getIndex(i));
            toDoListGUI.listModel.remove(i);
            String newTask = withoutLastCha + "1";
            toDoListGUI.listModel.insertElementAt(newTask, i);
            toDoListGUI.myToDoList.setSelectedIndex(i);
            toDoListGUI.myToDoList.ensureIndexIsVisible(i);
        }
    }
}
