package ru.itis.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.services.UsersService;

@Controller
@RequestMapping("/posts/personal")
@RequiredArgsConstructor
public class PersonalPostsController {

    private final UsersService usersService;

    @GetMapping("/moderated")
    public String getPersonalModeratedPosts(Model model, Authentication auth) {
        String email = (String) auth.getPrincipal();
        model.addAttribute("posts", usersService.getModeratedPosts(email));
        return "persModeratedPosts";
    }

    @GetMapping("/unmoderated")
    public String getPersonalUnmoderatedPosts(Model model, Authentication auth) {
        String email = (String) auth.getPrincipal();
        model.addAttribute("posts", usersService.getUnmoderatedPosts(email));
        return "persUnmoderatedPosts";
    }
}
