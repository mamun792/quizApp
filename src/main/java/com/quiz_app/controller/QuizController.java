package com.quiz_app.controller;

import com.quiz_app.model.QuestionList;
import com.quiz_app.model.Result;
import com.quiz_app.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class QuizController {
    private final QuizService quizService;
    private Result result;
    private boolean submitted;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
        result = new Result();
        submitted = false;
    }

    @GetMapping("/")
    public String getHomePage() {
        return "homePage";
    }

    @PostMapping("/quiz")
    public String Quiz(@RequestParam("username") String username, RedirectAttributes redirectAttributes, Model model) {
        if (username.equals(" ") || username.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Username cannot be empty");
            return "redirect:/";
        }
        submitted = false;
       result=new Result();
        result.setUsername(username);
        System.out.println(result.getUsername());
        QuestionList questionList = new QuestionList();
        questionList.setQuestions(quizService.getQuestion());

        model.addAttribute("qForm", questionList);
        return "Quiz";
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute QuestionList questionList, Model model) {

        if (!submitted) {
//            int totalCorrect = quizService.getResult(questionList);

            result.setTotalCorrect( quizService.getResult(questionList.getQuestions()));
            quizService.saveResult(result);
            submitted = true;

        }

        model.addAttribute("result", result);
        return "Result";
    }

    @GetMapping("/scoreboard")
    public  String Scoreboard(Model model){
        List<Result> results= quizService.getTopScore();
        System.out.println(results.toString());
        model.addAttribute("results",results);
        return  "Scoreboard";
    }

}
