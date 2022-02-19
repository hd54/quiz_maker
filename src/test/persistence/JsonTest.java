package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

// modelled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {

    // EFFECTS: compare question with data
    protected void checkQuestion(String topic, List<String> answers, String correctAnswer, Question question) {
        assertEquals(topic, question.getTopic());
        assertEquals(correctAnswer, question.getCorrectAnswer());

        for (String answer : answers) {
            assertTrue(answers.contains(answer));
        }
    }
}
