package ru.itmo.wp.servlet;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageServlet extends HttpServlet {

    private List<Object> messages = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();

        Object answer = null;
        switch (uri) {
            case "/message/auth":
                session.setAttribute("user", request.getParameter("user"));
                if (session.getAttribute("user") != null)
                    answer = session.getAttribute("user");
                break;
            case "/message/findAll":
                answer = messages;
                break;
            case "/message/add":
                if (session.getAttribute("user") != null && request.getParameter("text") != null)
                    messages.add(Map.of("user", session.getAttribute("user"), "text", request.getParameter("text")));
                break;
            default:
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                break;
        }
        response.setContentType("application/json");
        String json = new Gson().toJson(answer);
        response.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));
        response.getOutputStream().flush();
    }
}
