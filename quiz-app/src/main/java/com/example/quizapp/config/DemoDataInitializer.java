package com.example.quizapp.config;

import com.example.quizapp.model.QuizQuestion;
import com.example.quizapp.repository.QuizQuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoDataInitializer {
    @Bean CommandLineRunner loadQuestions(QuizQuestionRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new QuizQuestion("Which language runs on the Java Virtual Machine?", "Java", "HTML", "CSS", "SQL", "A", "Java Basics"));
                repo.save(new QuizQuestion("Which keyword creates a subclass in Java?", "implements", "extends", "inherits", "instanceof", "B", "Java Basics"));
                repo.save(new QuizQuestion("Which collection stores unique elements?", "ArrayList", "LinkedList", "HashSet", "Vector", "C", "Java Basics"));
                repo.save(new QuizQuestion("What does SQL stand for?", "Structured Query Language", "Simple Question List", "System Query Link", "Sequential Queue Language", "A", "Database"));
                repo.save(new QuizQuestion("Which SQL command retrieves data?", "UPDATE", "INSERT", "DELETE", "SELECT", "D", "Database"));
                repo.save(new QuizQuestion("Which HTML tag creates a hyperlink?", "<link>", "<a>", "<href>", "<url>", "B", "Web Basics"));
            }
        };
    }
}
