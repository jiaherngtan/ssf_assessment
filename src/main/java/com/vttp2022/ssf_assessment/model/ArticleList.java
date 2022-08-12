package com.vttp2022.ssf_assessment.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ArticleList {

    private List<Article> articlesList;

    public List<Article> getArticlesList() {
        return articlesList;
    }

    public void setArticlesList(List<Article> articlesList) {
        this.articlesList = articlesList;
    }

}
