package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {
    private Question questionOne;
    private Question questionTwo;
    private Question questionThree;
    private Question questionFour;

    @BeforeEach
    public void runBefore() {
        questionOne = new Question("What is 2+2?");
        questionTwo = new Question("What is the opposite of black?");
        questionThree = new Question("Is Java the best language?");
        questionFour = new Question("What is 2+2?","A");
    }

    @Test
    public void testConstructorOne() {
        assertEquals("What is 2+2?", questionOne.getTopic());
        assertEquals("", questionOne.getCorrectAnswer());
        questionOne.setTopic("Is UBC in Canada?");
        assertEquals("Is UBC in Canada?", questionOne.getTopic());
        assertEquals(0, questionOne.getAnswers().size());
        questionOne.setCorrectAnswer("A");
        assertEquals("A", questionOne.getCorrectAnswer());
    }

    @Test
    public void testConstructorTwo() {
        assertEquals("What is 2+2?", questionFour.getTopic());
        assertEquals("A", questionFour.getCorrectAnswer());
        assertEquals(0, questionFour.getAnswers().size());
        questionFour.setCorrectAnswer("N/A");
        assertEquals("N/A", questionFour.getCorrectAnswer());
    }

    @Test
    public void testAddAnswer() {
        assertEquals(0, questionOne.getAnswers().size());
        assertEquals(0, questionTwo.getAnswers().size());
        assertEquals(0, questionThree.getAnswers().size());

        questionOne.addAnswer("4");
        questionTwo.addAnswer("pink");
        questionTwo.addAnswer("white");

        assertEquals(1, questionOne.getAnswers().size());
        assertEquals(2, questionTwo.getAnswers().size());
        assertEquals(0, questionThree.getAnswers().size());
    }

    @Test
    public void testSetAnswerByIndex() {
        questionTwo.addAnswer("pink");
        questionTwo.addAnswer("white");
        questionTwo.addAnswer("fork");

        ArrayList<String> answers = new ArrayList<>();
        answers.add("eggs");
        answers.add("white");
        answers.add("moon");

        questionTwo.setAnswerByIndex(2, "moon");
        questionTwo.setAnswerByIndex(0, "eggs");

        assertEquals(answers, questionTwo.getAnswers());
    }
}