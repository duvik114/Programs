package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.lesson8.domain.User;
import ru.itmo.wp.lesson8.form.UserDisabledCredentials;
import ru.itmo.wp.lesson8.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/users/all")
    public String usersPost(UserDisabledCredentials userDisabledCredentials,
                            BindingResult bindingResult,
                            HttpSession httpSession,
                            Model model) {
        if (bindingResult.hasErrors()) {
            return "UsersPage";
        }

        userService.updateDisabled(userDisabledCredentials);
        setMessage(httpSession, "User status is changed");
        List<User> users = userService.findAll();

        /*shit*/
        users.stream().filter(i -> i.getId() == userDisabledCredentials.getId()).findAny().orElseThrow().
                setDisabled(userDisabledCredentials.getIsDisabled().equals("Disable")); //
        /*shit*/

        model.addAttribute("users", users);
        return "UsersPage";
    }
}
