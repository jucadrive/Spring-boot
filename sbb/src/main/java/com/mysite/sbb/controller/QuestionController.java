package com.mysite.sbb.controller;

import com.mysite.sbb.entity.AnswerForm;
import com.mysite.sbb.entity.Question;
import com.mysite.sbb.entity.QuestionForm;
import com.mysite.sbb.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<Question> paging = questionService.getList(page);

        model.addAttribute("paging", paging);
        return "question_list";
    }


    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id, AnswerForm answerForm) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);

        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }
    
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "question_form";
        }
        questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list";   // 질문 저장 후 질문 목록으로 이동
    }
}

