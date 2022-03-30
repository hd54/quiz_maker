package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a quiz with a list of questions
public class QuestionsFile implements Writable {
    private final List<Question> questionsList;
    public final List<String> validChoices;
    private int grade;
    private int maxGrade;

    // EFFECTS: create a list of questions with the list of accepted answers.
    //          initialized grading system
    public QuestionsFile() {
        this.questionsList = new ArrayList<>();
        validChoices = new ArrayList<>();
        grade = 0;
        maxGrade = 0;

        validChoices.add("A");
        validChoices.add("B");
        validChoices.add("C");
        validChoices.add("N/A");
    }

    // MODIFIES: this
    // EFFECTS: add a new question to file and return true, if not new return false
    public boolean addQuestion(Question question) {
        if (!questionsList.contains(question)) {
            EventLog.getInstance().logEvent(new Event("New question added."));
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
            EventLog.getInstance().logEvent(
                    new Event("Question" + questionsList.indexOf(question) + "deleted."));
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
        List<String> correctList = new ArrayList<>();
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
        Question removed = questionsList.get(index);
        EventLog.getInstance().logEvent(
                new Event("Question " + (index + 1) + " deleted."));
        questionsList.remove(removed);
    }

    // EFFECTS: return true if element index is not out of list bounds, else return false
    public boolean isIndexValid(int index) {
        return index <= questionsList.size() && index > 0;
    }

    // MODIFIES: this
    // EFFECTS: implement a grading system for the quiz user is taking
    public int validMaxGrade() {
        maxGrade = questionsList.size();
        for (Question q : questionsList) {
            if (q.getCorrectAnswer().equals("N/A")) {
                maxGrade--;
            }
        }
        return maxGrade;
    }

    // REQUIRES: userAnswerRecord has same size as questionsList and only contains "A","B", or "C"
    // MODIFIES: this
    // EFFECTS: compare list with user answers with the correct answer list.
    //          increments grade by 1 if elements in same index of each list are equal, then return final grade
    public int produceGrade(List<String> userAnswerRecord) {
        grade = 0;
        for (int i = 0; i < questionsList.size(); i++) {
            if (userAnswerRecord.get(i).equals(questionsList.get(i).getCorrectAnswer())) {
                grade++;
            }
        }
        return grade;
    }

    // 2 blocks below are modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: converts the questions file to a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("questions", questionsToJson());
        return json;
    }

    // EFFECTS: returns questions in the questions list as a JSON array
    private JSONArray questionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Question q : questionsList) {
            jsonArray.put(q.toJson());
        }

        return jsonArray;
    }
}