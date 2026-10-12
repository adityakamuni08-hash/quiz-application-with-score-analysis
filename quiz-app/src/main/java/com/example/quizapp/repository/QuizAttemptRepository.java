package com.example.quizapp.repository;
import com.example.quizapp.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findByStudentIdIgnoreCaseOrderByAttemptedAtDesc(String studentId);
}
