package com.example.quizapp.controller;

import com.example.quizapp.model.QuizAttempt;
import com.example.quizapp.model.QuizQuestion;
import com.example.quizapp.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.HashMap;
import java.util.Map;

@Controller
public class QuizController {
    private final QuizService service;
    public QuizController(QuizService service){this.service=service;}

    @GetMapping("/") public String home(){ return "redirect:/login"; }
    @GetMapping("/login") public String login(){ return "login"; }
    @PostMapping("/login") public String doLogin(@RequestParam String name, @RequestParam String userId, @RequestParam String role, RedirectAttributes flash){
        if(name.isBlank() || userId.isBlank()){ flash.addFlashAttribute("error","Enter your name and ID."); return "redirect:/login"; }
        if("admin".equalsIgnoreCase(role)) return "redirect:/admin?name="+java.net.URLEncoder.encode(name,java.nio.charset.StandardCharsets.UTF_8);
        return "redirect:/student?name="+java.net.URLEncoder.encode(name,java.nio.charset.StandardCharsets.UTF_8)+"&userId="+java.net.URLEncoder.encode(userId,java.nio.charset.StandardCharsets.UTF_8);
    }
    @GetMapping("/student") public String student(@RequestParam(defaultValue="Student") String name,@RequestParam(defaultValue="STU1001") String userId,Model model){
        model.addAttribute("name",name); model.addAttribute("userId",userId); model.addAttribute("topics",service.topics());
        model.addAttribute("questions",service.allQuestions().size()); model.addAttribute("attempts",service.studentAttempts(userId));
        model.addAttribute("attemptCount",service.studentAttempts(userId).size());
        double avg=service.studentAttempts(userId).stream().mapToDouble(QuizAttempt::getPercentage).average().orElse(0);
        model.addAttribute("average",avg); return "student";
    }
    @GetMapping("/quiz") public String quiz(@RequestParam String name,@RequestParam String userId,@RequestParam(required=false) String topic,Model model){
        var questions=service.questionsForTopic(topic); if(questions.isEmpty()){ return "redirect:/student?name="+enc(name)+"&userId="+enc(userId); }
        model.addAttribute("name",name); model.addAttribute("userId",userId); model.addAttribute("topic",topic==null?"All Topics":topic); model.addAttribute("questions",questions); return "quiz";
    }
    @PostMapping("/quiz/submit") public String submit(@RequestParam String name,@RequestParam String userId,@RequestParam(required=false) String topic,@RequestParam Map<String,String> form,RedirectAttributes flash){
        Map<String,String> answers=new HashMap<>(); form.forEach((k,v)->{if(k.startsWith("q_")) answers.put(k.substring(2),v);});
        QuizAttempt result=service.submit(name,userId,topic,answers);
        return "redirect:/result?id="+result.getId()+"&name="+enc(name)+"&userId="+enc(userId);
    }
    @GetMapping("/result") public String result(@RequestParam Long id,@RequestParam String name,@RequestParam String userId,Model model){
        QuizAttempt a=service.allAttempts().stream().filter(x->x.getId().equals(id)).findFirst().orElseThrow();
        model.addAttribute("result",a); model.addAttribute("name",name); model.addAttribute("userId",userId); return "result";
    }
    @GetMapping("/admin") public String admin(@RequestParam(defaultValue="Administrator") String name,Model model){
        model.addAttribute("name",name); model.addAttribute("questions",service.allQuestions()); model.addAttribute("attempts",service.allAttempts());
        model.addAttribute("questionCount",service.questionCount()); model.addAttribute("attemptCount",service.attemptCount());
        model.addAttribute("average",service.averageScore()); model.addAttribute("passCount",service.passCount()); model.addAttribute("topicCounts",service.topicAttemptCounts());
        model.addAttribute("newQuestion",new QuizQuestion()); return "admin";
    }
    @PostMapping("/admin/questions") public String addQuestion(@ModelAttribute QuizQuestion newQuestion,RedirectAttributes flash){
        if(newQuestion.getCorrectOption()==null || !newQuestion.getCorrectOption().matches("(?i)[ABCD]")) { flash.addFlashAttribute("adminError","Correct option must be A, B, C, or D."); return "redirect:/admin"; }
        service.addQuestion(newQuestion); flash.addFlashAttribute("adminMessage","Question added successfully."); return "redirect:/admin";
    }
    @PostMapping("/admin/questions/{id}/delete") public String deleteQuestion(@PathVariable Long id,RedirectAttributes flash){ service.deleteQuestion(id); flash.addFlashAttribute("adminMessage","Question deleted."); return "redirect:/admin"; }
    private String enc(String s){return java.net.URLEncoder.encode(s,java.nio.charset.StandardCharsets.UTF_8);}
}
