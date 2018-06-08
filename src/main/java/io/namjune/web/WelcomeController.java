package io.namjune.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
  @GetMapping("/helloworld")
  public String welcome(String name, int age, Model model) {
    model.addAttribute("name", name);
    model.addAttribute("age", age);
    return "welcome";
  }
}
