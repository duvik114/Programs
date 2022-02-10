package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.RegisterCredentials;
import ru.itmo.wp.form.validator.RegisterCredentialsValidator;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;
    private final RegisterCredentialsValidator registerCredentialsValidator;

    public UserController(JwtService jwtService, UserService userService, RegisterCredentialsValidator registerCredentialsValidator) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.registerCredentialsValidator = registerCredentialsValidator;
    }

    @InitBinder("registerCredentials")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(registerCredentialsValidator);
    }

    @GetMapping("users")
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @PostMapping("users")
    public User registerUser(@RequestBody @Valid RegisterCredentials registerCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        return userService.save(registerCredentials);
    }

    @GetMapping("users/auth")
    public User findUserByJwt(@RequestParam String jwt) {
        return jwtService.find(jwt);
    }
}
