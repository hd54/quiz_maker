package ui;

import model.Question;
import model.QuestionsFile;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

// graphical user interface
public class Gui extends JFrame implements ActionListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private QuestionsFile questionsFile;
    private static final String JSON_STORE = "./data/quiz.json";
    private static final ImageIcon imageIcon = new ImageIcon("./data/thonk.png");
    private static final ImageIcon loadingImage = new ImageIcon("./data/loadscreen.png");
    private JList<Question> dataFile;
    private DefaultListModel<Question> dataFileModel;

    // EFFECTS: create graphic interface for application
    public Gui() {
        super("Quiz Maker");

        initiateLoadingScreen();
        initializeMainWindow();
        initializeButtons();
        initializeData();
        intializeSave();
        intializeLoad();
        initializeDataPanel();

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    // partially modelled from https://stackoverflow.com/questions/16134549/how-to-make-a-splash-screen-for-gui
    // MODIFIES: this
    // EFFECTS: initiate loading screen at the start of the program
    private void initiateLoadingScreen() {
        JWindow window = new JWindow();
        window.getContentPane().add(new JLabel(loadingImage), SwingConstants.CENTER);
        window.setSize(new Dimension(loadingImage.getIconWidth(), loadingImage.getIconHeight()));
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
        window.dispose();
    }

    // MODIFIES: this
    // EFFECTS: initialize panel for questions to appear on
    private void initializeDataPanel() {
        dataFile = new JList<>(dataFileModel);
        dataFile.setVisibleRowCount(6);
        dataFile.setCellRenderer(new QuestionCellRenderer());

        JScrollPane dataPanel = new JScrollPane(dataFile);
        dataPanel.createVerticalScrollBar();
        add(dataPanel);
    }

    // EFFECTS: initialize database with questions file
    private void initializeData() {
        this.jsonReader = new JsonReader(JSON_STORE);
        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.questionsFile = new QuestionsFile();
        this.dataFileModel = new DefaultListModel<>();
    }

    // some codes are modeled from https://github.students.cs.ubc.ca/CPSC210/TellerApp
    //                and https://stackoverflow.com/questions/2361510/how-to-save-application-options-before-exit
    // MODIFIES: this
    // EFFECTS: intialize save window when open application
    private void intializeSave() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JPanel savePanel = inititiatePersistencePanel("Save changes to file?");
                int save = JOptionPane.showConfirmDialog(null, savePanel, "Save",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, logoInPersistence());
                if (save == JOptionPane.YES_OPTION) {
                    try {
                        jsonWriter.open();
                        jsonWriter.write(questionsFile);
                        jsonWriter.close();
                    } catch (FileNotFoundException exception) {
                        System.out.println("Unable to write to file: " + JSON_STORE);
                    }
                }
            }
        });
    }

    // EFFECTS: create a logo to put in persistence window (for fun)
    private Icon logoInPersistence() {
        Image scaleImage = imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        return new ImageIcon(scaleImage);
    }

    // MODIFIES: this
    // EFFECTS: initiate load window
    private JPanel inititiatePersistencePanel(String label) {
        JPanel savePanel = new JPanel();
        savePanel.setSize(new Dimension(400, 200));
        savePanel.setLayout(null);

        JLabel saveLabel = new JLabel(label);
        saveLabel.setBounds(-20, 50, 300, 50);
        saveLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        saveLabel.setHorizontalAlignment(SwingConstants.CENTER);
        saveLabel.setVerticalAlignment(SwingConstants.CENTER);
        savePanel.add(saveLabel);

        UIManager.put("OptionPane.minimumSize", new Dimension(400, 200));
        return savePanel;
    }

    // some codes are modeled from https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // MODIFIES: this
    // EFFECTS: intialize load window when open application
    private void intializeLoad() {
        JPanel loadPanel = inititiatePersistencePanel("Load changes from file?");
        int load = JOptionPane.showConfirmDialog(null, loadPanel, "Load",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, logoInPersistence());
        if (load == JOptionPane.YES_OPTION) {
            try {
                questionsFile = jsonReader.read();
                updateChange();
                System.out.println("File loaded from " + JSON_STORE);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes main application window
    private void initializeMainWindow() {
        setIconImage(imageIcon.getImage());
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // MODIFIES: this
    // EFFECTS: initializes all buttons that has application features
    private void initializeButtons() {
        JPanel buttonPanel = new JPanel();

        JButton addButton = createButton("Add Question");
        JButton editButton = createButton("Edit Question");
        JButton deleteButton = createButton("Delete Question");
        JButton quizButton = createButton("Take Quiz");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(quizButton);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    // EFFECTS: creates 1 button with no features yet
    public JButton createButton(String buttonName) {
        JButton genericButton = new JButton(buttonName);
        genericButton.setFocusable(false);
        genericButton.setActionCommand(buttonName);
        genericButton.addActionListener(this);
        return genericButton;
    }

    // EFFECTS: perform action based on the button clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add Question":
                initializeAddWindow();
                break;
            case "Edit Question":
                initializeEditWindow();
                break;
            case "Delete Question":
                this.doDelete();
                break;
            case "Take Quiz":
                ButtonWindow quizWindow = new ButtonWindow(1000, 800);
                quizWindow.doQuiz();
                break;
        }
    }

    // MODFIES: this, questionsFile
    // EFFECTS: initialize a window for adding question
    private void initializeAddWindow() {
        ButtonWindow addWindow = new ButtonWindow(600, 400);
        Question questionAdd = addWindow.doAdd();
        if (questionAdd != null) {
            questionsFile.addQuestion(questionAdd);
        }
        updateChange();
    }

    // MODIFIES: this, questionsFile
    // EFFECTS: initialize a window for editing question
    private void initializeEditWindow() {
        ButtonWindow editWindow = new ButtonWindow(600, 400);
        int index = dataFile.getSelectedIndex();
        if (index != -1) {
            Question questionEdit = questionsFile.getQuestionByIndex(index);
            editWindow.doEdit(questionEdit);
        } else {
            new ButtonWindow(0,0);
            JOptionPane.showMessageDialog(null,"Please select a question first");
        }
        updateChange();
    }

    // codes modelled from https://stackoverflow.com/questions/19396717/how-do-i-display-a-list-of-items-in-a-jscrollpane
    // MODIFIES: this, questionsFile
    // EFFECTS: delete selected question and update window to show change
    private void doDelete() {
        try {
            int index = dataFile.getSelectedIndex();
            questionsFile.removeQuestionByIndex(index);
            updateChange();
            dataFile.setSelectedIndex(0);
        } catch (IndexOutOfBoundsException e) {
            //
        }
    }

    // codes modelled from https://stackoverflow.com/questions/19396717/how-do-i-display-a-list-of-items-in-a-jscrollpane
    // EFFECTS: update window to reflect changes due to clicking button
    protected void updateChange() {
        dataFileModel.removeAllElements();
        for (Question q : questionsFile.getQuestionsList()) {
            dataFileModel.addElement(q);
        }
    }
}