# Quiz Application with Score Analysis

A beginner-friendly full-stack Java project for students and administrators/faculty. It includes a login-style role selection, student dashboard, topic-based multiple-choice quizzes, score calculation, result feedback, student attempt history, and an admin dashboard for managing questions and reviewing score analytics.

## Technology stack
- Java 17+
- Spring Boot 3.5.6
- Spring MVC and Thymeleaf
- Spring Data JPA / Hibernate
- H2 file database
- HTML, CSS, JavaScript
- Maven

## Run on Windows
1. Install JDK 17 or later: https://adoptium.net/
2. Install Apache Maven: https://maven.apache.org/install.html
3. Verify in CMD: `java -version` and `mvn -version`.
4. Extract the ZIP file.
5. Open the extracted `quiz-app` folder in VS Code. Confirm `pom.xml` is visible at the top level.
6. In VS Code, choose Terminal > New Terminal.
7. Run `mvn spring-boot:run` and wait for Spring Boot to start.
8. Open http://localhost:8080/ in your browser.
9. Keep the terminal open while using the site. Press Ctrl+C to stop it.

## Demo workflow
- Login screen: enter any name and student ID; choose Student to open the student dashboard.
- Students can take All Topics or topic-based quizzes, submit answers, see score/percentage/feedback, and review prior attempts.
- Choose Admin / Faculty on the login screen to open the admin dashboard. Add/delete questions and view attempts, average score, pass rate, and attempts by topic.
- Six sample questions are added automatically when the database is first created.

## Important limitation
The login screen is a role-selection demo, not real authentication. Do not use this app for real student records or deploy it publicly without Spring Security, password hashing, role authorization, CSRF protection, validation, tests, and a production database. Anyone can currently choose the Admin / Faculty option. Scores are calculated by the server, but answer keys are stored in the application database.

## Database
The H2 database is stored in `./quiz_app_db`. The H2 console is at http://localhost:8080/h2-console with JDBC URL `jdbc:h2:file:./quiz_app_db;DB_CLOSE_ON_EXIT=FALSE`, username `sa`, and a blank password. Keep this console local-only.

## Project structure
```text
src/main/java/com/example/quizapp/
  QuizApplication.java
  config/DemoDataInitializer.java
  controller/QuizController.java
  model/QuizQuestion.java
  model/QuizAttempt.java
  repository/QuizQuestionRepository.java
  repository/QuizAttemptRepository.java
  service/QuizService.java
src/main/resources/
  application.properties
  templates/login.html
  templates/student.html
  templates/quiz.html
  templates/result.html
  templates/admin.html
  static/styles.css
```
