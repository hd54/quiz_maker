package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a question with a topic, a list of answers, and the correct answer
public class Question implements Writable {
    private String topic;
    private List<String> answers;
    private String correctAnswer;

    // EFFECTS: create a basic question with a topic and empty answers (no correct answer)
    public Question(String topic) {
        this.topic = topic;
        this.answers = new ArrayList<>();
        this.correctAnswer = "";
    }

    // EFFECTS: create a basic complete question with all components filled in
    public Question(String topic, String correctAnswer) {
        this.topic = topic;
        this.answers = new ArrayList<>();
        this.correctAnswer = correctAnswer;
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

    // modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: converts a question to a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("topic", topic);
        json.put("answers", answersToJson());
        json.put("correct answers", correctAnswer);
        return json;
    }

    // EFFECTS: returns answers in the question as a JSON array
    private JSONArray answersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String answer : answers) {
            jsonArray.put(answer);
        }

        return jsonArray;
    }
}
