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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vttp2022.ssf_assessment.model.Article;
import com.vttp2022.ssf_assessment.model.ArticleList;
import com.vttp2022.ssf_assessment.service.NewsService;

@Controller
public class NewsController {

    public static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService ns;

    @Autowired
    private ArticleList al;

    @GetMapping("/")
    public String generateNewsPage(Model model) {

        Optional<List<Article>> optArt = ns.getArticles();
        if (optArt.isEmpty()) {
            model.addAttribute("articlesList", new LinkedList<Article>());
            return "index";
        }

        List<Article> articlesList = optArt.get();
        al.setArticlesList(articlesList);

        model.addAttribute("articlesList", articlesList);
        model.addAttribute("article", new Article());
        logger.info(">>> articlesList to be displayed: " + articlesList);

        return "index";
    }

    @PostMapping("/articles")
    public String selectedArticles(@ModelAttribute Article a) {

        List<String> selectedListId = a.getSelectedArticlesList();
        logger.info("output of form submission >>> " + selectedListId);
        List<String> allListId = new LinkedList<>();
        List<Article> allList = al.getArticlesList();
        logger.info(">>> all list: " + allList);
        for (Article article : allList) {
            allListId.add(article.getId());
        }
        logger.info("all list ids >>> " + allListId);
        List<Article> selectedList = new LinkedList<>();

        logger.info(">>> " + selectedListId.size());
        logger.info(">>> " + allList.size());

        for (int i = 0; i < selectedListId.size(); i++) {
            for (int j = 0; j < allList.size(); j++) {
                if (selectedListId.get(i) == allList.get(j).getId()) {
                    logger.info(">>> to add: " + allList.get(j));
                    selectedList.add(allList.get(j));
                }
            }
        }

        logger.info(">>> selected list: " + selectedList);
        ns.saveArticles(selectedList);

        return "redirect:/?";
    }

}
