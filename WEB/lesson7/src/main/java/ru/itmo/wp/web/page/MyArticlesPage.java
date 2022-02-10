package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

public class MyArticlesPage {
    private final UserService userService = new UserService();
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("message", "Dear incognito, please login to website");
            throw new RedirectException("/enter");
        }
        view.put("articles", articleService.findAllUserArticles(user.getId()));
    }

    private void hideOrShow(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("message", "Dear incognito, please login to website");
            throw new RedirectException("/enter");
        }
        long articleId;
        try {
            articleId = Long.parseLong(request.getParameter("id"));
        } catch (NullPointerException e) {
            request.getSession().setAttribute("message", "Article ID is required");
            return;
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("message", "Article ID must be a number");
            return;
        }

        String valueString = request.getParameter("value");
        if (!valueString.equals("Hide") && !valueString.equals("Show")) {
            request.getSession().setAttribute("message", "Article ID must be a number");
            return;
        }
        boolean value = valueString.equals("Show");

        Article article = articleService.findArticle(articleId);
        if (user.getId() != article.getUserId()) {
            request.getSession().setAttribute("message", "You are forbidden to change that parameter");
            view.put("hidden", article.getHidden() ? "Show" : "Hide");
        } else {
            if (value != article.getHidden()) {
                view.put("hidden", article.getHidden() ? "Show" : "Hide");
            } else {
                article.setHidden(!article.getHidden());
                articleService.update(article);
                view.put("hidden", article.getHidden() ? "Show" : "Hide");
            }
        }
    }
}
