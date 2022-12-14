package com.vttp2022.ssf_assessment.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.vttp2022.ssf_assessment.model.Article;

@Service
public class NewsService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    private static String URL = "https://min-api.cryptocompare.com/data";
    private static String apiKey = System.getenv("CRYPTO_COMPARE_API_KEY");

    public Optional<List<Article>> getArticles() {

        String url = UriComponentsBuilder.fromUriString(URL + "/" + "v2/news/?lang=EN&")
                .queryParam("api_key", apiKey)
                .toUriString();

        logger.info(">>> URL: " + url);

        List<Article> articlesList = new LinkedList<>();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            resp = template.getForEntity(url, String.class);
            articlesList = Article.createJsonGetArticles(resp.getBody());
            return Optional.of(articlesList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // redis service method
    public void saveArticles(List<Article> articlesList) {

        logger.info(">>> articles to be saved on redis: " + articlesList);

        for (Article a : articlesList) {
            redisTemplate.opsForValue().set(a.getId(), a);
        }
    }

    // redis service method
    public Article getArticleById(String id) {

        Article article = (Article) redisTemplate.opsForValue().get(id);

        return article;
    }

}
