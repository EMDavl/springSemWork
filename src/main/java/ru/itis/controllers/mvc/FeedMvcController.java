package ru.itis.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.models.ModeratedPost;
import ru.itis.services.FeedService;

@Controller
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedMvcController {

    @Value("${pagination.default_size}")
    private Integer paginationDefaultSize;

    private final FeedService feedService;

    @GetMapping()
    public String getPage(@RequestParam(value = "page", required = false) Integer page, Model model) {

        page = page == null ? 0 : page;

        Pageable pageable = PageRequest.of(page, paginationDefaultSize);
        Page<ModeratedPost> posts = feedService.getPosts(pageable);
        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", posts.getNumber());
        return "feed";
    }
}
