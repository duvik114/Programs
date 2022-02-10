package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class IndexPage extends Page {
    @Override
    void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    @Override
    void after(HttpServletRequest request, Map<String, Object> view) {
        super.after(request, view);
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operation
    }
}