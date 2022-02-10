package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/post/{id}")
    public String postGet(Model model, @PathVariable String id) {
        long idLong = 0;
        try {
            idLong = Long.parseLong(id);
        } catch (NumberFormatException e) {
            model.addAttribute("message", "Post id must be a number");
        }
        Post post = postService.findById(idLong);
        if (post == null) {
            model.addAttribute("message", "Cannot find post with that id");
            return "redirect:/posts";
        }
        model.addAttribute("post", postService.findById(idLong));
        model.addAttribute("comment", new Comment());
        return "PostPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/post/{id}")
    public String postPost(@Valid @ModelAttribute("comment") Comment comment,
                           BindingResult bindingResult,
                           HttpSession httpSession,
                           @PathVariable String id,
                           Model model) {
        long idLong = 0;
        try {
            idLong = Long.parseLong(id);
        } catch (NumberFormatException e) {
            model.addAttribute("message", "Post id must be a number");
            return "PostPage";
        }
        Post post = postService.findById(idLong);
        if (post == null) {
            model.addAttribute("message", "Cannot find post with that id");
            return "redirect:/posts";
        }
        model.addAttribute("post", postService.findById(idLong));

        if (bindingResult.hasErrors()) {
            return "PostPage";
        }

        comment.setUser(getUser(httpSession));
        if (comment.getUser() == null) {
            model.addAttribute("message", "Only authorised users allowed to write comments");
            return "PostPage";
        }
        postService.writeComment(postService.findById(idLong), comment);
        putMessage(httpSession, "You published new comment");
        comment.setText(""); //
        model.addAttribute("comment", comment);
        return "PostPage";
    }
}
