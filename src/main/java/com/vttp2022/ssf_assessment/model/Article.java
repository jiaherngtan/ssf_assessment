package com.vttp2022.ssf_assessment.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Article {

    private String id;
    private BigDecimal publishedOn;
    private String title;
    private String url;
    private String imageUrl;
    private String body;
    private String tags;
    private String categories;
    private List<String> articlesList;
    private List<String> selectedArticlesList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(BigDecimal publishedOn) {
        this.publishedOn = publishedOn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public List<String> getArticlesList() {
        return articlesList;
    }

    public void setArticlesList(List<String> articlesList) {
        this.articlesList = articlesList;
    }

    public List<String> getSelectedArticlesList() {
        return selectedArticlesList;
    }

    public void setSelectedArticlesList(List<String> selectedArticlesList) {
        this.selectedArticlesList = selectedArticlesList;
    }

    public static final Logger logger = LoggerFactory.getLogger(Article.class);

    // method that return a list of articles
    public static List<Article> createJsonGetArticles(String json) throws IOException {

        List<Article> articlesList = new LinkedList<>();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject jo = jr.readObject();
            // logger.info(">>> json Object: " + jo);
            JsonArray jsonArr = jo.getJsonArray("Data");
            // logger.info(">>> json Array: " + jsonArr);

            // loop through "Data" json objects and extract the desired info
            for (int i = 0; i < jsonArr.size(); i++) {
                // generate new article
                Article article = new Article();

                JsonObject item = jsonArr.getJsonObject(i);
                String id = item.getString("id");
                BigDecimal publishedOn = item.getJsonNumber("published_on").bigDecimalValue();
                String title = item.getString("title");
                String url = item.getString("url");
                String imageUrl = item.getString("imageurl");
                String body = item.getString("body");
                String tags = item.getString("tags");
                String categories = item.getString("categories");

                article.setId(id);
                article.setPublishedOn(publishedOn);
                article.setTitle(title);
                article.setUrl(url);
                article.setImageUrl(imageUrl);
                article.setBody(body);
                article.setTags(tags);
                article.setCategories(categories);

                logger.info(">>> id: " + article.getId());

                // add article to list
                articlesList.add(article);
                // logger.info(">>> articlesList: " + articlesList);
            }
            logger.info(">>> articlesList: " + articlesList);
            // Articles articles = new Articles();
            // articles.setArticlesList(articlesList);
        }

        return articlesList;
    }

}
