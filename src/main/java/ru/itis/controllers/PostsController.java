package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.ModeratedPostDto;
import ru.itis.dto.PostRequestDto;
import ru.itis.models.ModeratedPost;
import ru.itis.services.PostService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostService service;

    @GetMapping("/page")
    public List<ModeratedPostDto> getPostsWithPagination(@RequestParam(required = false) Integer lastPageElement,
                                                         @RequestParam(required = false) Integer pagingLimit) {
        lastPageElement = lastPageElement == null ? 0 : lastPageElement;
        Pageable pages;

        if (pagingLimit == null) {
            pagingLimit = 20;
        }

        pages = PageRequest.of(lastPageElement, lastPageElement + pagingLimit);

        Page<ModeratedPost> posts = service.getWithPagination(lastPageElement, pagingLimit);
        return posts.stream().map(ModeratedPostDto::from).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ModeratedPostDto> createPost(@RequestBody PostRequestDto post, Principal principal) {
        ModeratedPostDto dto = ModeratedPostDto.from(service.save(post));
        return ResponseEntity.ok(dto);
    }


}
