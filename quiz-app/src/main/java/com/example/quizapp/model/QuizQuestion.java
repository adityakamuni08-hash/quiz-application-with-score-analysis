package com.example.quizapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class QuizQuestion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String questionText;
    @NotBlank private String optionA;
    @NotBlank private String optionB;
    @NotBlank private String optionC;
    @NotBlank private String optionD;
    @NotBlank private String correctOption;
    @NotBlank private String topic;

    public QuizQuestion() {}
    public QuizQuestion(String questionText, String optionA, String optionB, String optionC, String optionD, String correctOption, String topic) {
        this.questionText=questionText; this.optionA=optionA; this.optionB=optionB; this.optionC=optionC; this.optionD=optionD; this.correctOption=correctOption; this.topic=topic;
    }
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getQuestionText(){return questionText;} public void setQuestionText(String v){questionText=v;}
    public String getOptionA(){return optionA;} public void setOptionA(String v){optionA=v;}
    public String getOptionB(){return optionB;} public void setOptionB(String v){optionB=v;}
    public String getOptionC(){return optionC;} public void setOptionC(String v){optionC=v;}
    public String getOptionD(){return optionD;} public void setOptionD(String v){optionD=v;}
    public String getCorrectOption(){return correctOption;} public void setCorrectOption(String v){correctOption=v;}
    public String getTopic(){return topic;} public void setTopic(String v){topic=v;}
}
