# Quiz Maker

## Multiple Choice Bank Builder

### About

**Quiz Maker** is a desktop application that allows user to
*create* multiple-choice questions. Users are able to *write*
a set number of answers (3) for each question. Users can also decide whether to *include* correct answer as well.

This application would be most useful for teachers and professors, as they may want to make and store course exams for
students to take. Of course, the application may also be used for non-academic purposes, such as creating quiz for
friends and family to have a fun social time together.

### Inspiration

My main inspiration for this idea comes from Kahoot, a very fun, interactive learning platform where everyone can create
their own quizzes and invite others to play it by typing in an access code. Another website that is probably more
familiar to students is Canvas. Canvas allows professors to create homework quiz for their students, and it can include
multiple-choice, with one or several correct answers, and even reflective questions where students can type in their
proof or arguments. Hence, I want to try and create an app that can do one of the core function of these websites:
creating a quiz. For this program, I will only be focusing on designing multiple-choice quiz maker.

## User Stories

- As a user, I want to be able to add a new question into a file
- As a user, I want to be able to delete a question from a file
- As a user, I want to be able to modify a question from a file (i.e., change topic/answers/correct answer)
- As a user, I want to be able to view all questions within a file
- As a user, I want to be able to take the quiz, get my grades and also correct answers for all questions
- As a user, I want to be able to save my question file
- As a user, I want to be able to load my question file

## Phase 4: Task 2
- Sample for when user loads saved file, adding and deleting a question:

File loaded from ./data/quiz.json

Tue Mar 29 10:21:23 PDT 2022
New question added.


Tue Mar 29 10:21:23 PDT 2022
New question added.


Tue Mar 29 10:21:23 PDT 2022
New question added.


Tue Mar 29 10:21:23 PDT 2022
New question added.


Tue Mar 29 10:21:32 PDT 2022
New question added.


Tue Mar 29 10:21:33 PDT 2022
Question 5 deleted.

- Deleting question shows the index of removed question.

## Phase 4: Task 3

Reflection on program design:

- Dependencies on **Gui** on certain classes (**BorderlessTextField** and
**QuestionCellRender**), since they only have one usage only
in Gui and nowhere else. For example, **BorderlessTextField** can be
implemented as an anonymous class with the overridden methods.
Cohesion should still be maintained, since these classes do not pose as
too many responsibilities if included in **Gui**.
- **Gui** and **ButtonWindow** can extend a new abstract class (**ApplicationWindow**
for example) that deals with initializing window design (method that create a 
generic panel from given size as parameters) to avoid some duplicates, since
these classes share some similar properties which can be seen in their constructors.

Other reflections:

- New methods can be extracted to avoid duplicate codes. For example,
**doAdd()** and **doEdit** share similar functionality since they also
use a similar panel that receives texts typed in textbox by users.
We can call the method **setUpUserInputs()**

Other notes (what I think I've done well for the project):

- Cohesion is maintained through introducing new class **ButtonWindow**.
The class is solely responsible for all button-related panels.
- Tight coupling does not pose too much issue. We actually avoid tight
coupling thanks to introducing the **ButtonWindow** class as well. Objects of
this type are instantiated within a method in **Gui**.
