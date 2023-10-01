package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/index")
public class IndexController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public IndexController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    @GetMapping()
    public String index (Model model) {
        Optional<User> admin = Optional.ofNullable(userService.findByUsername("admin"));
        Optional<User> user = Optional.ofNullable(userService.findByUsername("user"));
        admin.ifPresent(a -> {
            if (!passwordEncoder.matches("admin", a.getPassword() )) {
                a.setPassword("unknown");
            }
        });
        model.addAttribute("admin", admin);
        user.ifPresent(u -> {
            model.addAttribute(user);
            if (!passwordEncoder.matches("user", u.getPassword() )) {
                u.setPassword("unknown");
            }
        });
        model.addAttribute("user", user);
        return "index";
    }
}
