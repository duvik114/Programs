package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

import java.util.List;

public interface ArticleRepository {
    Article findArticle(long id);
    List<Article> findAll();
    List<Article> findAllUserArticles(long userId);
    void save(Article article);
    void update(Article article);
}
