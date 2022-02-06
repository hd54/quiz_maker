package ui;

import model.Question;
import model.QuestionsFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// some codes are taken (and remodeled for this class) from https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class QuizApp {
    private QuestionsFile questionsFile;
    private Scanner input;
    private List<String> validChoices;

    // EFFECTS: runs the quiz application
    public QuizApp() {
        runQuizApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runQuizApp() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nThanks for visiting. See you next time!");
    }

    // MODIFIES: this
    // EFFECTS: initializes questions file
    private void init() {
        questionsFile = new QuestionsFile();
        input = new Scanner(System.in);

        validChoices = new ArrayList<>();
        validChoices.add("A");
        validChoices.add("B");
        validChoices.add("C");
        validChoices.add("N/A");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome to QuizApp. What do you want to do today?");
        System.out.println("\tadd -> add new question");
        System.out.println("\tdel -> remove a question");
        System.out.println("\tedit -> edit a question");
        System.out.println("\tview -> view all questions");
        System.out.println("\tquit -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "add":
                doAddQuestion();
                break;
            case "del":
                doRemoveQuestion();
                break;
            case "edit":
                doEditQuestion();
                break;
            case "view":
                doViewQuestions();
                break;
            default:
                System.out.println("Invalid command. Please try again.");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: add a question to the current file
    private void doAddQuestion() {
        System.out.println("Please type your question");
        String topic = input.next();
        Question newQuestion = new Question(topic);

        System.out.println("Please type your 3 answers below:");
        System.out.println("A:");
        String ansOne = input.next();
        newQuestion.addAnswer(ansOne);
        System.out.println("B:");
        String ansTwo = input.next();
        newQuestion.addAnswer(ansTwo);
        System.out.println("C:");
        String ansThree = input.next();
        newQuestion.addAnswer(ansThree);

        System.out.println("What is the correct answer? (A, B, C, or N/A)");
        validEditedCorrectAnswer(newQuestion);
        questionsFile.addQuestion(newQuestion);
        System.out.println("Your question has been added to the file!");
    }

    // MODIFIES: this
    // EFFECTS: edit correct answer according to user input after checking if it's valid
    private void validEditedCorrectAnswer(Question newQuestion) {
        String correctAns = validInput();
        newQuestion.setCorrectAnswer(correctAns.toUpperCase());
    }

    // EFFECTS: return the valid answer
    private String validInput() {
        String answer = input.next();
        while (!validChoices.contains(answer.toUpperCase())) {
            System.out.println("Invalid input. Please try again.");
            answer = input.next();
        }
        return answer;
    }

    // MODIFIES: this
    // EFFECTS: remove a question from the list
    private void doRemoveQuestion() {
        if (questionsFile.getQuestionsList().isEmpty()) {
            System.out.println("There are no questions to delete.");
        } else {
            printQuestionsList();
            int index = typeIndex();
            boolean isValid = isIndexValid(index);
            if (isValid) {
                System.out.println("Do you want to delete question " + index + " ? (yes or no)");
                String deleteIndication = input.next();
                indicationAction(index, deleteIndication);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: delete question if indication is yes, do nothing otherwise
    private void indicationAction(int index, String deleteIndication) {
        if (deleteIndication.equalsIgnoreCase("yes")) {
            Question removed = questionsFile.getQuestionByIndex(index - 1);
            questionsFile.removeQuestion(removed);
            System.out.println("Your selected question has been deleted from the file!");
        } else {
            System.out.println("Deletion request cancelled. If input is invalid, please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: edit any part (topic, answers, correct answer) of any question
    private void doEditQuestion() {
        if (questionsFile.getQuestionsList().isEmpty()) {
            System.out.println("There are no questions to delete.");
        } else {
            printQuestionsList();
            int index = typeIndex();
            boolean isValid = isIndexValid(index);
            if (isValid) {
                Question edited = questionsFile.getQuestionByIndex(index - 1);
                System.out.println("Question " + index + ":");
                printQuestion(edited, index);
                selectAndEdit(edited);
            }
        }
    }

    private int typeIndex() {
        System.out.println("\nSelect a question by typing its index:");
        return input.nextInt();
    }

    private boolean isIndexValid(int index) {
        if (index > questionsFile.getQuestionsList().size() || index <= 0) {
            System.out.println("Invalid input.");
            return false;
        } else {
            return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: select a question and edit it to something else
    private void selectAndEdit(Question editedQuestion) {
        System.out.println("Indicate what to edit: topic, answers, correct");
        String editIndication = input.next();
        switch (editIndication) {
            case "topic":
                editTopic(editedQuestion);
                break;
            case "answers":
                editAnswers(editedQuestion);
                break;
            case "correct":
                editCorrect(editedQuestion);
                break;
            default:
                System.out.println("Invalid command. Please try again.");
                break;
        }
    }

    // EFFECTS: edit topic
    private void editTopic(Question editedQuestion) {
        System.out.println("edited to:");
        String editedTopic = input.next();
        editedQuestion.setTopic(editedTopic);
        System.out.println("Success!");
    }

    // EFFECTS: edit an answer
    private void editAnswers(Question editedQuestion) {
        System.out.println("Select answer you wish to change:");
        String editedInput = validInput();
        System.out.println("Changing answer to:");
        String editedAnswer = input.next();
        editedQuestion.setAnswerByIndex(validChoices.indexOf(editedInput), editedAnswer);
        System.out.println("Success!");
    }

    // EFFECTS: edit correct answer
    private void editCorrect(Question editedQuestion) {
        System.out.println("edited to:");
        validEditedCorrectAnswer(editedQuestion);
        System.out.println("Success!");
    }

    // EFFECTS: print all components of a question
    private void printQuestion(Question question, int index) {
        System.out.println("\nQuestion " + index + ": " + question.getTopic());
        for (int i = 0; i < question.getAnswers().size(); i++) {
            System.out.println(validChoices.get(i) + "." + question.getAnswers().get(i));
        }
        System.out.println("Correct answer: " + question.getCorrectAnswer());
    }

    // EFFECTS: show all question from the file
    private void doViewQuestions() {
        System.out.println("Here is a list of all questions:");
        printQuestionsList();
        System.out.println("\nLoaded a total of " + questionsFile.getQuestionsList().size() + " question(s).");
    }

    // EFFECTS: print all questions in the list
    private void printQuestionsList() {
        int numbering = 1;
        for (Question q : questionsFile.getQuestionsList()) {
            printQuestion(q, numbering);
            numbering++;
        }
    }
}
