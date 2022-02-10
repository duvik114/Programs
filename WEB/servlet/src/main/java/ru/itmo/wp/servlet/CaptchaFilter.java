package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {

    private final String root = "C:\\Users\\duvik\\IdeaProjects\\WEEB\\servlet\\src\\main\\webapp\\static";

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();

        if (session.getAttribute("captcha_ok") == null) {
            session.setAttribute("captcha_ok", false);
        }
        if (request.getRequestURI().startsWith("/img/captcha.png")) {
            chain.doFilter(request, response);
        } else if (request.getMethod().equals("POST") && request.getParameter("captcha_input_number") != null) {
            if ((boolean) session.getAttribute("captcha_ok")) {
                response.setHeader("Location", request.getRequestURI());
                response.setStatus(302);
            } else {
                try {
                    Integer.parseInt(request.getParameter("captcha_input_number"));
                } catch (NumberFormatException e) {
                    generateCaptchaNumber(session);
                    doCaptcha(request, response, session);
                    return;
                }
                if (Integer.parseInt(request.getParameter("captcha_input_number")) == (int) session.getAttribute("captcha_number")) {
                    session.setAttribute("captcha_ok", true);
                    response.setHeader("Location", request.getRequestURI());
                    response.setStatus(302);
                } else {
                    generateCaptchaNumber(session);
                    doCaptcha(request, response, session);
                }
            }
        } else if (request.getMethod().equals("GET") && !(boolean) session.getAttribute("captcha_ok")) {
            doCaptcha(request, response, session);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void doCaptcha(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if (session.getAttribute("is_captchured") == null) {
            session.setAttribute("is_captchured", true);
            generateCaptchaNumber(session);
        }

        response.setContentType("text/html");
        OutputStream outputStream = response.getOutputStream();
        File file = new File(root + "/captcha.html");
        if (!file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        Files.copy(file.toPath(), outputStream);
        outputStream.flush();
        outputStream.close();
    }

    private void generateCaptchaNumber(HttpSession session) throws IOException {
        File imgFile = new File(root + "/img/captcha.png");
        if (!imgFile.isFile()) {
            throw new IOException("Can't create img with captcha");
        }

        int number = new Random().nextInt(900) + 100;
        session.setAttribute("captcha_number", number);
        FileOutputStream fileOutputStream = new FileOutputStream(imgFile);
        fileOutputStream.write(ImageUtils.toPng(String.valueOf(number)));
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
