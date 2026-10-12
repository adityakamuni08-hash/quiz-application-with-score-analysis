package com.example.quizapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class QuizAttempt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentName;
    private String studentId;
    private String topic;
    private int totalQuestions;
    private int score;
    private double percentage;
    private LocalDateTime attemptedAt;
    public QuizAttempt() {}
    public QuizAttempt(String studentName,String studentId,String topic,int totalQuestions,int score) {
        this.studentName=studentName; this.studentId=studentId; this.topic=topic; this.totalQuestions=totalQuestions; this.score=score;
        this.percentage=totalQuestions==0?0:(score*100.0/totalQuestions); this.attemptedAt=LocalDateTime.now();
    }
    public Long getId(){return id;} public String getStudentName(){return studentName;} public String getStudentId(){return studentId;}
    public String getTopic(){return topic;} public int getTotalQuestions(){return totalQuestions;} public int getScore(){return score;}
    public double getPercentage(){return percentage;} public LocalDateTime getAttemptedAt(){return attemptedAt;}
}
