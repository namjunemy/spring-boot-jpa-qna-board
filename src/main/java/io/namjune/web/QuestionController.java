package io.namjune.web;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import io.namjune.domain.Question;
import io.namjune.domain.QuestionRepository;
import io.namjune.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

  @Autowired
  private QuestionRepository questionRepository;

  @GetMapping("/form")
  public String form(HttpSession session) {
    if (!HttpSessionUtils.isLoginUser(session)) {
      return "/users/loginForm";
    }

    return "/qna/form";
  }

  @PostMapping("")
  public String create(HttpSession session, String title, String contents) {
    if (!HttpSessionUtils.isLoginUser(session)) {
      return "/users/loginForm";
    }

    User sessionUser = HttpSessionUtils.getUserFormSession(session);
    Question newQuestion = new Question(sessionUser, title, contents);
    questionRepository.save(newQuestion);

    return "redirect:/";
  }
}
