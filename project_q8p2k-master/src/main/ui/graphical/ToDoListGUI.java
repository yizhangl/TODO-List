package ui.graphical;

import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;


// ToDoListGUI is based on ListDemo
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

// Main GUI application
public class ToDoListGUI extends JPanel implements ListSelectionListener {
    private static final String JSON_STORE = "./data/user_program.json";
    public static final Color ACCENT_COLOR = Color.decode("#dff7f7");

    protected ToDoList toDoList;
    protected JList<String> myToDoList;
    protected DefaultListModel<String> listModel;

    private JFrame frame;
    private JPanel northPanel;
    private JScrollPane centerList;
    private JPanel southPanel;

    private JButton addButton;
    protected JButton deleteButton;
    protected JButton setComplete;
    protected JButton clear;
    protected JTextField description;
    protected JTextField deadline;
    private JLabel descriptionLabel;
    private JLabel deadlineLabel;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private AddTaskListener addTask;
    private DeleteTaskListener deleteTaskListener;
    private CompleteTaskListener completeTaskListener;
    private ClearTaskListener clearTaskListener;

    // constructor
    ToDoListGUI() {
        super(new BorderLayout());
        toDoList = new ToDoList();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        listModel = new DefaultListModel<>();

        createSplashFrame();
        addCenterList();
        placeCenterPanel();

        addDescriptionLabel();
        addDescriptionTextField();
        addDeadlineLabel();
        addDeadlineTextField();
        addAddButton();

        addDeleteButton();
        addSetCompleteButton();
        addClearButton();

        placeNorthPanel();
        placeSouthPanel();

        createFrame();
    }


    // createSplashFrame is based on splash
    // https://stackoverflow.com/questions/16134549/how-to-make-a-splash-screen-for-gui
    // EFFECTS: create a splash window and display a welcoming picture when opening the app
    private void createSplashFrame() {
        JWindow window = new JWindow();
        ImageIcon picture = new ImageIcon("./data/picture.PNG");
        System.out.println(new java.io.File("picture.PNG").exists());
        JLabel label = new JLabel("Happy ToDoList");
        label.setIcon(picture);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        window.setIconImage(picture.getImage());
        window.add(label, BorderLayout.CENTER);

        window.setBounds(500,150,650,600);
        window.setVisible(true);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.dispose();
    }

    // MODIFIES: this
    // EFFECTS: create a frame
    private void createFrame() {
        this.frame = new JFrame();
        frame.setTitle("ToDoList App");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(centerList, BorderLayout.CENTER);
        frame.add(northPanel, BorderLayout.PAGE_START);
        frame.add(southPanel, BorderLayout.PAGE_END);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        loadAndSaveWindows();
    }

    // MODIFIES: this
    // EFFECTS: place north buttons and textFields on north panel
    public void placeNorthPanel() {
        northPanel = new JPanel();
        northPanel.setBackground(ACCENT_COLOR);
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.LINE_AXIS));

        northPanel.add(descriptionLabel);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(description);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(deadlineLabel);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(deadline);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(addButton);
        northPanel.add(Box.createHorizontalStrut(5));
        northPanel.add(deleteButton);

        northPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(northPanel, BorderLayout.PAGE_START);
    }

    // MODIFIES: this
    // EFFECTS: place south buttons on the south panel
    private void placeSouthPanel() {
        southPanel = new JPanel();
        southPanel.setBackground(ACCENT_COLOR);
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));
        southPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(southPanel, BorderLayout.PAGE_END);

        southPanel.add(setComplete);
        northPanel.add(Box.createHorizontalStrut(5));
        southPanel.add(clear);
    }

    // MODIFIES: this
    // EFFECTS: place the center list on the center panel
    private void placeCenterPanel() {
        centerList = new JScrollPane(myToDoList);
        centerList.createVerticalScrollBar();
    }


    // MODIFIES: this
    // EFFECTS: add center list
    private void addCenterList() {
        myToDoList = new JList<>(listModel);
        myToDoList.setSelectionMode((ListSelectionModel.SINGLE_SELECTION));
        myToDoList.setSelectedIndex(0);
        myToDoList.addListSelectionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: add description label
    private void addDescriptionLabel() {
        descriptionLabel = new JLabel("Description: ");
    }

    // MODIFIES: this
    // EFFECTS: add description text field
    private void addDescriptionTextField() {
        description = new JTextField(4);
        description.addActionListener(addTask);
        description.getDocument().addDocumentListener(addTask);
    }


    // MODIFIES: this
    // EFFECTS: add deadline label
    private void addDeadlineLabel() {
        deadlineLabel = new JLabel("Deadline: ");
    }


    // MODIFIES: this
    // EFFECTS: add deadline text field
    private void addDeadlineTextField() {
        deadline = new JTextField(4);
        deadline.addActionListener(addTask);
        deadline.getDocument().addDocumentListener(addTask);
    }


    // MODIFIES: this
    // EFFECTS: add addButton
    private void addAddButton() {
        addButton = new JButton("addTask");
        addTask = new AddTaskListener(this, addButton);
        addListener(addTask);
    }

    // EFFECTS: return addTask; help with the method below
    public AddTaskListener getAddTask() {
        return addTask;
    }

    // EFFECTS: add listener to addButton
    public void addListener(AddTaskListener listener) {
        if (getAddTask() != addTask) {
            this.addTask = listener;
            addTask.setGUI(this);
        }
        addButton.setActionCommand("addTask");
        addButton.addActionListener(addTask);
        addButton.setEnabled(true);
    }


    // MODIFIES: this
    // EFFECTS: add setComplete Button
    private void addSetCompleteButton() {
        setComplete = new JButton("completeTask");
        completeTaskListener = new CompleteTaskListener(this);
        setComplete.setActionCommand("completeTask");
        setComplete.addActionListener(completeTaskListener);
        setComplete.setEnabled(true);
    }

    // MODIFIES: this
    // EFFECTS: add clear button
    private void addClearButton() {
        clear = new JButton("clearAll");
        clearTaskListener = new ClearTaskListener(this);
        clear.setActionCommand("clearAll");
        clear.addActionListener(clearTaskListener);
        clear.setEnabled(true);
    }

    // MODIFIES: this
    // EFFECTS: add delete button
    private void addDeleteButton() {
        deleteButton = new JButton("deleteTask");
        deleteTaskListener = new DeleteTaskListener(this);
        deleteButton.setActionCommand("deleteTask");
        deleteButton.addActionListener(deleteTaskListener);
    }


    // MODIFIES: this
    // EFFECTS:  create pop-up windows when opening, show pop-up window when closing
    private void loadAndSaveWindows() {
        load();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                save();
            }
        });
    }


    // EFFECTS: Opens a pop-up window, if yes, saves data; otherwise, does nothing
    private void save() {
        int n = JOptionPane.showConfirmDialog(frame,
                "Do you want to save data to Licy's todolist?",
                "Save data?",
                JOptionPane.YES_NO_CANCEL_OPTION);
        if (n == 0) {
            try {
                jsonWriter.open();
                jsonWriter.write(toDoList);
                jsonWriter.close();
                System.out.println("Saved to " + JSON_STORE);
                frame.dispose();
                toDoList.printLog();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        } else if (n == 1) {
            frame.dispose();
            toDoList.printLog();
        }
    }

    // MODIFIES: this
    // EFFECTS: if yes, load data; otherwise, does nothing
    private void load() {
        int n = JOptionPane.showConfirmDialog(frame,
                "Do you want to load from Licy's todolist?",
                "Load data?",
                JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            try {
                toDoList = jsonReader.read();
                for (String t: toDoList.viewTasks()) {
                    listModel.addElement(t);
                }
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
                toDoList = new ToDoList();
            }
        } else {
            toDoList = new ToDoList();
        }
    }


    // MODIFIES
    // EFFECTS: if there is no selection, disable these buttons; otherwise, enable these buttons
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (myToDoList.getSelectedIndex() == -1) {
                deleteButton.setEnabled(false);
                setComplete.setEnabled(false);

            } else {
                deleteButton.setEnabled(true);
                setComplete.setEnabled(true);
            }
        }
    }
}
