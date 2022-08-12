package com.vttp2022.ssf_assessment.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.vttp2022.ssf_assessment.model.Article;
import com.vttp2022.ssf_assessment.service.NewsService;

@Controller
public class NewsController {

    public static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService ns;

    @GetMapping("/")
    public String generateNewsPage(Model model) {

        Optional<List<Article>> optArt = ns.getArticles();
        if (optArt.isEmpty()) {
            model.addAttribute("articlesList", new LinkedList<Article>());
            return "index";
        }
        model.addAttribute("articlesList", ns.getArticles().get());
        logger.info(">>> articlesList to be displayed: " + ns.getArticles().get());

        return "index";
    }

    @PostMapping("/articles")
    public String selectedArticles(@RequestBody(required = true) String[] id) {

        logger.info("output of form submission >>> " + id);
        // ns.saveArticles(articles);
        return "redirect:/";
    }

}