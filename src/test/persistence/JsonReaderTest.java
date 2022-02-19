package persistence;

import model.Question;
import model.QuestionsFile;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// modelled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            QuestionsFile questionsFile = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyQuiz() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyQuiz.json");
        try {
            QuestionsFile questionsFile = reader.read();
            assertEquals(0, questionsFile.getQuestionsList().size());
            assertEquals(4, questionsFile.validChoices.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralQuiz() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralQuiz.json");
        try {
            QuestionsFile questionsFile = reader.read();
            List<Question> questions = questionsFile.getQuestionsList();
            assertEquals(3, questions.size());

            List<String> answersOne = new ArrayList<>(Arrays.asList("Egg", "Tomato", "Lemon"));
            List<String> answersTwo = new ArrayList<>(Arrays.asList("15", "F", "4 1s"));
            List<String> answersThree = new ArrayList<>(Arrays.asList("Canada", "USA", "Mars"));

            checkQuestion("What is life?", answersOne, "N/A", questions.get(0));
            checkQuestion("What is 1111?", answersTwo, "C", questions.get(1));
            checkQuestion("Where is UBC?", answersThree, "A", questions.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
