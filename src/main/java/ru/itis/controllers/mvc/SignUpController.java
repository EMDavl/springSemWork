package ru.itis.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.dto.SignUpDto;
import ru.itis.services.UsersService;

@Controller
@RequestMapping("/signUp")
@RequiredArgsConstructor
public class SignUpController {

    private final UsersService usersService;

    @GetMapping
    public String getPage() {
        return "signUp";
    }

    @GetMapping("/confirm")
    public String confirmAccount(@RequestParam("code") String code, Model model) {
        if (code == null) {
            model.addAttribute("message", "Code not provided");
        }

        usersService.confirm(code, model);
        return "confirm";
    }

    @PostMapping
    public String signUp(SignUpDto credentials) {
        return usersService.signUp(credentials);
    }
}