package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.lesson8.service.UserService;

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public String user(Model model, @PathVariable String id) {
        long idLong = 0;
        try {
            idLong = Long.parseLong(id);
        } catch (NumberFormatException e) {
            model.addAttribute("message", "User id must be a number");
        }
        model.addAttribute("user", userService.findById(idLong));
        return "UserPage";
    }
}
