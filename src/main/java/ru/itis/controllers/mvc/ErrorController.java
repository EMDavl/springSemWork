package ru.itis.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/error")
public class ErrorController {
    @GetMapping
    public String getErrorPage(@RequestParam("message") String message, Model model) {
        model.addAttribute("msg", message);
        return "error";
    }
}
