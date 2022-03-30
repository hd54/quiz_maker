package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads questions list from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public QuestionsFile read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseQuestionsFile(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses questions file from JSON object and returns it
    private QuestionsFile parseQuestionsFile(JSONObject jsonObject) {
        QuestionsFile questionsFile = new QuestionsFile();
        addQuestions(questionsFile, jsonObject);
        return questionsFile;
    }

    // MODIFIES: questionsFile
    // EFFECTS: parses questions from JSON object and adds them to file
    private void addQuestions(QuestionsFile questionsFile, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("questions");
        for (Object json : jsonArray) {
            JSONObject nextQuestion = (JSONObject) json;
            addQuestion(questionsFile, nextQuestion);
        }
    }

    // MODIFIES: questionsFile
    // EFFECTS: parses question from JSON object and adds it to file
    private void addQuestion(QuestionsFile questionsFile, JSONObject jsonObject) {
        String topic = jsonObject.getString("topic");
        JSONArray answers = jsonObject.getJSONArray("answers");
        String correctAnswer = jsonObject.getString("correct answers");

        Question question = new Question(topic, correctAnswer);

        for (Object answer : answers) {
            question.addAnswer(answer.toString());
        }

        questionsFile.addQuestion(question);
    }
}
