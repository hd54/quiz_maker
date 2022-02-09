package model;

import java.util.ArrayList;
import java.util.List;

// Represents a quiz with a list of questions
public class QuestionsFile {
    private List<Question> questionsList;
    private List<String> correctList = new ArrayList<>();
    public final List<String> validChoices;

    // EFFECTS: create a list of questions with the valid choices that can be assigned to correct answer
    public QuestionsFile() {
        this.questionsList = new ArrayList<>();
        validChoices = new ArrayList<>();

        validChoices.add("A");
        validChoices.add("B");
        validChoices.add("C");
        validChoices.add("N/A");
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

    // GETTER
    public List<Question> getQuestionsList() {
        return questionsList;
    }

    // REQUIRES: 0 <= index < questionsList.size()
    // EFFECTS: return the question at index position
    public Question getQuestionByIndex(int index) {
        return questionsList.get(index);
    }

    // EFFECTS: return a list of all correct answers
    public List<String> allCorrectAnswers() {
        correctList = new ArrayList<>();
        for (Question q : questionsList) {
            correctList.add(q.getCorrectAnswer());
        }
        return correctList;
    }

    // Assume list is not empty
    // REQUIRES: 1 <= index <= questionsList.size()
    // MODIFIES: this
    // EFFECTS: removes the question by index
    public void removeQuestionByIndex(int index) {
        Question removed = questionsList.get(index - 1);
        questionsList.remove(removed);
    }

    // EFFECTS: return true if element index is not out of list bounds, else return false
    public boolean isIndexValid(int index) {
        return index <= questionsList.size() && index > 0;
    }
}
