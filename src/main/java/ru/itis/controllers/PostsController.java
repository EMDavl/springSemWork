package ru.itis.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.ModeratedPostDto;
import ru.itis.dto.PostRequestDto;
import ru.itis.services.PostService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostService service;

    @GetMapping()
    public Page<ModeratedPostDto> getPostsWithPagination(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                         @RequestParam(name = "size", defaultValue = "10") Integer size) {

        return service.getWithPagination(page, size).map(ModeratedPostDto::from);
    }

    @PostMapping
    public ResponseEntity<ModeratedPostDto> createPost(@RequestBody PostRequestDto post) {
        ModeratedPostDto dto = ModeratedPostDto.from(service.save(post));
        return ResponseEntity.ok(dto);
    }

    @SneakyThrows
    @DeleteMapping
    public void deletePost(Long postId, HttpServletResponse response) {
        if (service.postExist(postId)) {
            service.delete(postId);
            response.sendRedirect("/posts");
        }
        response.sendError(404, "Post not found");
    }
}
