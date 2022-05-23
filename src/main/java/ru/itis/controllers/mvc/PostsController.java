package ru.itis.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.PostCreationDto;
import ru.itis.dto.PostDto;
import ru.itis.services.PostsService;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService service;

    @GetMapping("/create")
    public String getPage() {
        return "postsCreation";
    }

    @PostMapping("/create")
    public String createPost(PostCreationDto post, Authentication auth) {
        String userEmail = (String) auth.getPrincipal();

        post.setTags(Arrays.stream(post.getTagsString().split(";")).map(String::trim).collect(Collectors.toList()));

        service.create(post, userEmail);

        return "redirect:/posts/personal/unmoderated";
    }

    @GetMapping("/{postId}")
    public String getPost(@PathVariable("postId") Long postId, Model model) {
        model.addAttribute("post", PostDto.from(service.findById(postId)));
        return "post";
    }
}