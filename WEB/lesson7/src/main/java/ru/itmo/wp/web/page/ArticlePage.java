package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class ArticlePage {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "Dear incognito, please login to website");
            throw new RedirectException("/enter");
        }
    }

    private void createArticle(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "Dear incognito, please login to website");
            throw new RedirectException("/enter");
        }
        Article article = new Article();
        article.setUserId(((User) request.getSession().getAttribute("user")).getId());
        article.setTitle(request.getParameter("title"));
        article.setText(request.getParameter("text"));
        article.setHidden(false);
        articleService.validateArticle(article);
        articleService.save(article);

        request.getSession().setAttribute("message", "You are successfully publish your article!");
        throw new RedirectException("/index");
    }
}
