package ru.itis.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.services.UsersService;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileMvcController {

    private final UsersService usersService;

    @GetMapping
    public String getPage(Authentication auth, Model model) {

        String email = (String) auth.getPrincipal();
        return usersService.getProfile(email, model);

    }

}