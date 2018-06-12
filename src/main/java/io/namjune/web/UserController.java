package io.namjune.web;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import io.namjune.domain.User;
import io.namjune.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/loginForm")
  public String loginForm() {
    return "/user/login";
  }

  @PostMapping("/login")
  public String login(String userId, String password, HttpSession session) {
    User user = userRepository.findByUserId(userId);

    if (user == null) {
      System.out.println("Login Failure !");
      return "redirect:/users/loginForm";
    }

    if (!user.matchPassword(password)) {
      System.out.println("Login Failure !");
      return "redirect:/users/loginForm";
    }

    System.out.println("Login Success !");
    session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);

    return "redirect:/";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
    return "redirect:/";
  }

  @GetMapping("/form")
  public String form() {
    return "/user/form";
  }

  @PostMapping("")
  public String create(User user) {
    userRepository.save(user);
    System.out.println(user);
    return "redirect:/users";
  }

  @GetMapping("")
  public String list(Model model) {
    model.addAttribute("users", userRepository.findAll());
    return "/user/list";
  }

  @GetMapping("{id}/form")
  public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
    if (HttpSessionUtils.isLoginUser(session)) {
      return "redirect:/users/loginForm";
    }

    User sessionUser = HttpSessionUtils.getUserFormSession(session);

    if (!sessionUser.matchId(id)) {
      throw new IllegalStateException("You can't update another user");
    }

    model.addAttribute("user", userRepository.findOne(id));
    return "/user/updateForm";
  }

  @PutMapping("/{id}")
  public String update(@PathVariable Long id, User updateUser, HttpSession session) {
    if (HttpSessionUtils.isLoginUser(session)) {
      return "redirect:/users/loginForm";
    }

    User sessionUser = HttpSessionUtils.getUserFormSession(session);

    if (!sessionUser.matchId(id)) {
      throw new IllegalStateException("You can't update another user");
    }

    User user = userRepository.findOne(id);
    user.update(updateUser);
    userRepository.save(user);
    return "redirect:/users";
  }
}
