package ru.itis.controllers.mvc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.PostCreationDto;
import ru.itis.dto.PostDto;
import ru.itis.dto.RatingChanges;
import ru.itis.services.PostsService;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
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

    @PostMapping("/like/{postId}")
    public ResponseEntity<RatingChanges> like(@PathVariable("postId") Long postId, Authentication auth) {
        return service.like(postId, (String) auth.getPrincipal());
    }

    @PostMapping("/dislike/{postId}")
    public ResponseEntity<RatingChanges> dislike(@PathVariable("postId") Long postId, Authentication auth) {
        return service.dislike(postId, (String) auth.getPrincipal());
    }

    @GetMapping("/byTag")
    public String getByTag(@RequestParam("id") Long id, Model model) {
        model.addAttribute("postPage", service.getByTag(id));
        return "feed";
    }

    @GetMapping("/byAuthor")
    public String getByAuthor(@RequestParam("id") Long id, Model model) {
        model.addAttribute("postPage", service.getByBookAuthor(id));
        return "feed";
    }

    @GetMapping("/byBookName")
    public String getByBookName(@RequestParam("name") String name, Model model) {
        model.addAttribute("postPage", service.getByBookName(name));
        return "feed";
    }
}
