package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class Page {

    protected final UserService userService = new UserService();
    private HttpServletRequest request;

    void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;
        User user = getUser();
        if (user != null) {
            view.put("user", user);
        }
    }

    void after(HttpServletRequest request, Map<String, Object> view) {
        view.put("userCount", userService.findCount());
    }

    void setMessage(String message) {
        if (request != null) {
            request.getSession().setAttribute("message", message);
        }
    }

    void setUser(User user) {
        if (request != null) {
            request.getSession().setAttribute("user", user);
        }
    }

    User getUser() {
        if (request == null) {
            return null;
        }
        return (User) request.getSession().getAttribute("user");
    }
}
