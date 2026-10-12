package com.example.quizapp.service;

import com.example.quizapp.model.QuizAttempt;
import com.example.quizapp.model.QuizQuestion;
import com.example.quizapp.repository.QuizAttemptRepository;
import com.example.quizapp.repository.QuizQuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class QuizService {
    private final QuizQuestionRepository questions;
    private final QuizAttemptRepository attempts;
    public QuizService(QuizQuestionRepository questions, QuizAttemptRepository attempts) { this.questions=questions; this.attempts=attempts; }
    public List<QuizQuestion> allQuestions(){ return questions.findAll(); }
    public List<QuizQuestion> questionsForTopic(String topic){ return topic==null || topic.isBlank() ? questions.findAll() : questions.findByTopicIgnoreCase(topic); }
    public List<String> topics(){ return questions.findAll().stream().map(QuizQuestion::getTopic).distinct().sorted().toList(); }
    public long questionCount(){ return questions.count(); }
    public List<QuizAttempt> allAttempts(){ return attempts.findAll().stream().sorted(Comparator.comparing(QuizAttempt::getAttemptedAt).reversed()).toList(); }
    public List<QuizAttempt> studentAttempts(String studentId){ return attempts.findByStudentIdIgnoreCaseOrderByAttemptedAtDesc(studentId); }
    public long attemptCount(){ return attempts.count(); }
    public double averageScore(){ return attempts.findAll().stream().mapToDouble(QuizAttempt::getPercentage).average().orElse(0); }
    public long passCount(){ return attempts.findAll().stream().filter(a -> a.getPercentage() >= 50).count(); }
    public Map<String,Long> topicAttemptCounts(){ Map<String,Long> out=new LinkedHashMap<>(); for(String topic:topics()) out.put(topic, attempts.findAll().stream().filter(a -> topic.equals(a.getTopic())).count()); return out; }

    @Transactional
    public QuizAttempt submit(String name, String studentId, String topic, Map<String,String> answers) {
        List<QuizQuestion> selected=questionsForTopic(topic);
        int score=0;
        for (QuizQuestion q:selected) {
            String answer=answers.get(String.valueOf(q.getId()));
            if(answer!=null && answer.equalsIgnoreCase(q.getCorrectOption())) score++;
        }
        String label=(topic==null || topic.isBlank()) ? "All Topics" : topic;
        return attempts.save(new QuizAttempt(name.trim(), studentId.trim(), label, selected.size(), score));
    }
    public QuizQuestion addQuestion(QuizQuestion q) { q.setCorrectOption(q.getCorrectOption().toUpperCase(Locale.ROOT)); return questions.save(q); }
    public void deleteQuestion(Long id){ questions.deleteById(id); }
}
