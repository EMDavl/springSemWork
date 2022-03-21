package ru.itis.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PostsLineController {

    @GetMapping
    public String getAllPosts(){
        return "welcome";
    }
}
