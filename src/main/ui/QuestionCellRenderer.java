package ui;

import model.Question;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// codes modelled from https://stackoverflow.com/questions/14740381/display-a-property-of-objects-in-jlist
//                     https://stackoverflow.com/questions/28910200/how-to-print-settext-on-multiple-lines-of-a-jlabel
public class QuestionCellRenderer extends DefaultListCellRenderer {

    // EFFECTS: produce basic design of a question to appear on screen
    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Question) {
            Question question = (Question) value;
            String topic = question.getTopic();
            List<String> answers = question.getAnswers();
            String correct = question.getCorrectAnswer();
            setText("<html> Question: " + topic
                    + "<br/> A. " + answers.get(0)
                    + "<br/> B. " + answers.get(1)
                    + "<br/> C. " + answers.get(2)
                    + "<br/> Correct Answer: " + correct
                    + "<br/>" + "<hr style=\"border-width:0;\">" + "</html>");
        }
        return this;
    }
}
