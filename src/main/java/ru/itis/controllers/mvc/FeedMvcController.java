package ru.itis.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.services.FeedService;

@Controller
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedMvcController {

    private final FeedService feedService;

    @GetMapping()
    public String getPage(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                          Model model) {

        model.addAttribute("postPage", feedService.getPosts(page));
        return "feed";
    }
}
