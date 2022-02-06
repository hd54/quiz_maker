package model;

import java.util.ArrayList;
import java.util.List;

// Represents a quiz with a list of questions
public class QuestionsFile {
    private List<Question> questionsList;

    // EFFECTS: create a list of questions
    public QuestionsFile() {
        this.questionsList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a new question to file and return true, if not new return false
    public boolean addQuestion(Question question) {
        if (!questionsList.contains(question)) {
            questionsList.add(question);
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: remove an existing question from file and return true, if not exist return false
    public boolean removeQuestion(Question question) {
        if (questionsList.contains(question)) {
            questionsList.remove(question);
            return true;
        } else {
            return false;
        }
    }

    // GETTERS
    public List<Question> getQuestionsList() {
        return questionsList;
    }

    // REQUIRES: 0 <= index < questionsList.size()
    // EFFECTS: return the question at index position
    public Question getQuestionByIndex(int index) {
        return questionsList.get(index);
    }
}
