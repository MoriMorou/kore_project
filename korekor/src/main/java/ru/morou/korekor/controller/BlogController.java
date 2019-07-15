package ru.morou.korekor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {

    @GetMapping("/blog")
    public String blog(){
        return "blog";
    }

    @GetMapping("/blog-detail")
    public String blogDetails(){
        return "blog-detail";
    }
}
