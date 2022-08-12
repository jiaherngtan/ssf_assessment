package com.vttp2022.ssf_assessment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vttp2022.ssf_assessment.model.Article;
import com.vttp2022.ssf_assessment.service.NewsService;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(path = "/news", consumes = "application/json", produces = "application/json")
public class NewsRESTController {

    private static final Logger logger = LoggerFactory.getLogger(NewsRESTController.class);

    @Autowired
    NewsService ns;

    @GetMapping(path = "/{id}")
    public ResponseEntity<String> getArticleById(@PathVariable String id) {

        try {
            final Article article = ns.getArticleById(id);

            final JsonObject resp = Json.createObjectBuilder()
                    .add("id", article.getId())
                    .add("title", article.getTitle())
                    .add("body", article.getBody())
                    .add("published_on", article.getPublishedOn())
                    .add("url", article.getUrl())
                    .add("imageurl", article.getImageUrl())
                    .add("tags", article.getTags())
                    .add("categories", article.getCategories())
                    .build();

            logger.info(">>> json resp to string: " + resp.toString());

            return ResponseEntity.ok(resp.toString());
        } catch (Exception e) {

            JsonObject errResp = Json.createObjectBuilder()
                    .add("error", "Cannot find news article " + id)
                    .build();

            return ResponseEntity.internalServerError().body(errResp.toString());
        }
    }
}
