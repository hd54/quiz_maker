package model;

import java.util.ArrayList;
import java.util.List;

// Represents a question with a topic, a list of answers, and the correct answer
public class Question {
    private String topic;
    private List<String> answers;
    private String correctAnswer;

    // REQUIRES: no topic has zero length
    // EFFECTS: create a basic question with a topic and empty answers (no correct answer)
    public Question(String topic) {
        this.topic = topic;
        this.answers = new ArrayList<>();
        this.correctAnswer = "";
    }

    // MODIFIES: this
    // EFFECTS: as an answer to the list of answers
    public void addAnswer(String answer) {
        answers.add(answer);
    }

    // getter
    public String getTopic() {
        return topic;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    // SETTERS
    public void setTopic(String topic) {
        this.topic = topic;
    }

    // REQUIRES: 0 <= index < answers.size()
    // MODIFIES: this
    // EFFECTS: set answer at index position to a new one
    public void setAnswerByIndex(int index, String answer) {
        answers.set(index, answer);
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
