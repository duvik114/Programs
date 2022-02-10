package ru.itmo.wp.servlet;

import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StaticServlet extends HttpServlet {

    private final String root = "C:\\Users\\duvik\\IdeaProjects\\WEEB\\servlet\\src\\main\\webapp\\static";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String[] files = getFiles(uri);

        response.setContentType(getContentTypeFromName(files[0]));
        OutputStream outputStream = response.getOutputStream();
        for (String fileName : files) {
            File file = new File(root + fileName);
            if (!file.isFile())
                file = new File(getServletContext().getRealPath("/static" + fileName));

            if (!file.isFile()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
            }

            Files.copy(file.toPath(), outputStream);
        }

        outputStream.flush();
    }

    private String[] getFiles(String uri) {
        return (uri.replaceAll("\\+", "+/")).split("\\+");
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}
