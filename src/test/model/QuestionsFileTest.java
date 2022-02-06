package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionsFileTest {
    private QuestionsFile questionsFileOne;
    private QuestionsFile questionsFileTwo;
    private Question questionOne;
    private Question questionTwo;
    private Question questionThree;

    @BeforeEach
    public void runBefore() {
        questionsFileOne = new QuestionsFile();
        questionsFileTwo = new QuestionsFile();
        questionOne = new Question("What is 2+2?");
        questionTwo = new Question("What is the opposite of black?");
        questionThree = new Question("Is Java the best language?");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, questionsFileOne.getQuestionsList().size());
    }

    @Test
    public void testAddQuestion() {
        assertTrue(questionsFileOne.addQuestion(questionOne));
        assertEquals(1, questionsFileOne.getQuestionsList().size());

        questionsFileTwo.addQuestion(questionTwo);
        questionsFileTwo.addQuestion(questionThree);
        assertEquals(2, questionsFileTwo.getQuestionsList().size());

        assertFalse(questionsFileTwo.addQuestion(questionTwo));
        assertEquals(2, questionsFileTwo.getQuestionsList().size());
    }

    @Test
    public void testRemoveQuestion() {
        questionsFileOne.addQuestion(questionOne);
        questionsFileTwo.addQuestion(questionTwo);
        questionsFileTwo.addQuestion(questionThree);

        questionsFileOne.removeQuestion(questionOne);
        assertEquals(0, questionsFileOne.getQuestionsList().size());

        questionsFileTwo.removeQuestion(questionOne);
        assertEquals(2, questionsFileTwo.getQuestionsList().size());
        questionsFileTwo.removeQuestion(questionTwo);
        assertEquals(1, questionsFileTwo.getQuestionsList().size());
        questionsFileTwo.removeQuestion(questionThree);
        assertEquals(0, questionsFileTwo.getQuestionsList().size());
    }

    @Test
    public void testGetQuestionByIndex() {
        questionsFileOne.addQuestion(questionOne);
        questionsFileOne.addQuestion(questionTwo);
        questionsFileOne.addQuestion(questionThree);

        assertEquals(questionOne, questionsFileOne.getQuestionByIndex(0));
        assertEquals(questionThree, questionsFileOne.getQuestionByIndex(2));
    }
}
