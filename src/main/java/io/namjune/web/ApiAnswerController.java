package io.namjune.web;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.namjune.domain.Answer;
import io.namjune.domain.AnswerRepository;
import io.namjune.domain.Question;
import io.namjune.domain.QuestionRepository;
import io.namjune.domain.Result;
import io.namjune.domain.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

  @Autowired
  private AnswerRepository answerRepository;

  @Autowired
  private QuestionRepository questionRepository;

  @PostMapping("")
  public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
    if (!HttpSessionUtils.isLoginUser(session)) {
      return null;
    }

    User loginUser = HttpSessionUtils.getUserFormSession(session);
    Question question = questionRepository.findOne(questionId);
    Answer answer = new Answer(loginUser, question, contents);
    question.addAnswer();
    return answerRepository.save(answer);
  }

  @DeleteMapping("/{id}")
  public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
    if (!HttpSessionUtils.isLoginUser(session)) {
      return Result.fail("로그인이 필요합니다.");
    }

    Answer answer = answerRepository.findOne(id);
    User loginUser = HttpSessionUtils.getUserFormSession(session);
    if (!answer.isSameWriter(loginUser)) {
      return Result.fail("작성자만 삭제할 수 있습니다.");
    }

    answerRepository.delete(id);
    Question question = questionRepository.findOne(questionId);
    question.deleteAnswer();
    questionRepository.save(question);
    return Result.ok();
  }
}
