package ru.itis.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.PostCreationDto;
import ru.itis.services.UsersService;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts/create")
@RequiredArgsConstructor
public class PostsCrudController {

    private final UsersService service;

    @GetMapping
    public String getPage() {
        return "postsCreation";
    }

    @PostMapping()
    public String createPost(PostCreationDto post, Authentication auth) {
        String userEmail = (String) auth.getPrincipal();

        post.setTags(Arrays.stream(post.getTagsString().split(";")).map(String::trim).collect(Collectors.toList()));
        service.createPost(post, userEmail);
        return "redirect:/posts/personal/unmoderated";
    }
}
