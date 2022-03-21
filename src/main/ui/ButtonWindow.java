package ui;

import model.Question;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// code partially modelled from https://youtube.com/watch?v=HgkBvwgciB4
// responsible for pop-up window that specializes in some button functions
public class ButtonWindow {
    private final JPanel controlPanel;

    private JTextArea topic;
    private JTextField correct;
    private JTextField answerA;
    private JTextField answerB;
    private JTextField answerC;

    // EFFECTS: create empty window after clicking a button
    public ButtonWindow(int width, int height) {
        controlPanel = new JPanel();
        controlPanel.setSize(new Dimension(width, height));
        controlPanel.setLayout(null);
        setQuestionField();

        UIManager.put("OptionPane.minimumSize", new Dimension(width, height));
    }

    // MODIFIES: this
    // EFFECTS: add text field to window
    private void setQuestionField() {
        setTopicField();
        setAnswersField();
        setCorrectField();
    }

    // MODIFIES: this
    // EFFECTS: add topic field to window
    private void setTopicField() {
        JLabel topicLabel = new JLabel("Question:");
        topicLabel.setBounds(0,0, 100, 10);
        topic = new JTextArea();
        int topicHeight = controlPanel.getHeight() / 3;
        topic.setBounds(0, 20, controlPanel.getWidth(), topicHeight);
        controlPanel.add(topicLabel);
        controlPanel.add(topic);
    }

    //
    // MODIFIES: this
    // EFFECTS: add answers field to window
    private void setAnswersField() {
        JLabel answersLabel = new JLabel("Answers:");
        int answerLabelHeight = controlPanel.getHeight() / 3 + 25;
        answersLabel.setBounds(0, answerLabelHeight, 100, 10);
        List<JLabel> alphabetLabel = initiateAlphabet();

        answerA = setTextField(alphabetLabel.get(0), 1, 2, 25);
        answerB = setTextField(alphabetLabel.get(1), 1, 2, -15);
        answerC = setTextField(alphabetLabel.get(2), 1, 2, -55);

        controlPanel.add(answersLabel);
    }

    // EFFECTS: set a generic text field
    private JTextField setTextField(JLabel alphabetLabel, int numerator, int denominator, int gap) {
        int alphabetLabelHeight = controlPanel.getHeight() * numerator / denominator - gap;
        alphabetLabel.setBounds(0, alphabetLabelHeight, 100, 10);
        JTextField text = new BorderlessTextField();
        int answerHeight = controlPanel.getHeight() * numerator / denominator - gap + 15;
        text.setBounds(0, answerHeight, controlPanel.getWidth() - 10, 20);
        controlPanel.add(alphabetLabel);
        controlPanel.add(text);
        return text;
    }

    // EFFETCS: return list of letter A B C to put as answer choices
    private List<JLabel> initiateAlphabet() {
        List<JLabel> alphabetLabel = new ArrayList<>();
        alphabetLabel.add(new JLabel("A"));
        alphabetLabel.add(new JLabel("B"));
        alphabetLabel.add(new JLabel("C"));
        return alphabetLabel;
    }

    // MODIFIES: this
    // EFFECTS: add correct field to window
    private void setCorrectField() {
        correct = setTextField(new JLabel("Correct Answer:"), 3, 4, 5);
    }

    // MODIFIES: this
    // EFFECTS: open new window, add new question to file and update main window if created
    protected Question doAdd() {
        int addChoice = JOptionPane.showConfirmDialog(null, controlPanel,
                "Type your question here:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (addChoice == JOptionPane.OK_OPTION) {
            Question question = new Question(topic.getText(), correct.getText());
            question.addAnswer(answerA.getText());
            question.addAnswer(answerB.getText());
            question.addAnswer(answerC.getText());
            return question;
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: open new window, edit existing question and update main window if edited
    protected void doEdit(Question question) {
        topic.setText(question.getTopic());
        List<String> answerTexts = question.getAnswers();
        answerA.setText(answerTexts.get(0));
        answerB.setText(answerTexts.get(1));
        answerC.setText(answerTexts.get(2));
        correct.setText(question.getCorrectAnswer());
        controlPanel.revalidate();
        controlPanel.repaint();
        JOptionPane.showConfirmDialog(null, controlPanel,
                "Edit your question here:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: open new window and intitiate a quiz
    protected void doQuiz() {

    }
}
