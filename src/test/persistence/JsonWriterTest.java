package persistence;

import model.Question;
import model.QuestionsFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// modelled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest {
    private Question questionFive;
    private Question questionSix;
    private Question questionSeven;

    @BeforeEach
    public void setup() {
        questionFive = new Question("What is 2+2?","A");
        questionFive.addAnswer("4");
        questionFive.addAnswer("5");
        questionFive.addAnswer("6");

        questionSix = new Question("What is the opposite of black?", "B");
        questionSix.addAnswer("White");
        questionSix.addAnswer("Pink");
        questionSix.addAnswer("Red");

        questionSeven = new Question("Is Java the best language?", "N/A");
        questionSeven.addAnswer("Yes");
        questionSeven.addAnswer("No");
        questionSeven.addAnswer("Maybe");
    }

    @Test
    public void testWriterInvalidFile() {
        try {
            QuestionsFile questionsFile = new QuestionsFile();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyQuiz() {
        try {
            QuestionsFile questionsFile = new QuestionsFile();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyQuiz.json");
            writer.open();
            writer.write(questionsFile);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyQuiz.json");
            questionsFile = reader.read();
            assertEquals(0, questionsFile.getQuestionsList().size());
            assertEquals(4, questionsFile.validChoices.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralQuiz() {
        try {
            QuestionsFile questionsFile = new QuestionsFile();
            questionsFile.addQuestion(questionFive);
            questionsFile.addQuestion(questionSix);
            questionsFile.addQuestion(questionSeven);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralQuiz.json");
            writer.open();
            writer.write(questionsFile);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralQuiz.json");
            questionsFile = reader.read();
            List<Question> questions = questionsFile.getQuestionsList();
            assertEquals(3, questions.size());
            checkQuestion("What is 2+2?", questionFive.getAnswers(), "A", questions.get(0));
            checkQuestion("What is the opposite of black?", questionSix.getAnswers(), "B", questions.get(1));
            checkQuestion("Is Java the best language?", questionSeven.getAnswers(), "N/A", questions.get(2));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
