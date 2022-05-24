package ru.itis.controllers.mvc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.PostUpdateDto;
import ru.itis.services.PostsService;
import ru.itis.services.UsersService;

@Controller
@RequestMapping("/posts/personal")
@RequiredArgsConstructor
@Slf4j
public class PersonalPostsController {

    private final PostsService postsService;
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

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePost(@RequestParam(name = "id") Long postId, Authentication auth) {
        String email = (String) auth.getPrincipal();
        if (postsService.delete(postId, email)) {
            return ResponseEntity.ok("Deleted");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Post not exist or you are not author");
        }
    }

    @GetMapping("/edit")
    public String getEditPage(@RequestParam("id") Long postId, Model model, Authentication auth) {
        if (!postsService.isValidInput(postId, (String) auth.getPrincipal())) {
            return "redirect:/feed";
        }
        model.addAttribute("post", PostUpdateDto.from(postsService.findById(postId)));
        return "postUpdate";
    }

    @PostMapping("/edit")
    public String editPost(PostUpdateDto post, Authentication auth) {
        if (postsService.update(post, (String) auth.getPrincipal())) {
            return "redirect:/posts/personal/unmoderated";
        }
        return "redirect:/feed";
    }
}
