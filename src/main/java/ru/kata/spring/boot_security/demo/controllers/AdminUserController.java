package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin/users/")
public class AdminUserController {
    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final UserService userService;
    private static final String MAIN_PAGE = "redirect:/admin/users/";


    @Autowired
    public AdminUserController(PasswordEncoder passwordEncoder, RoleService roleService, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index (Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/all";
    }
    @GetMapping("/new")
    public String add (Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAll());
        return "users/new";
    }
    @PostMapping("/")
    public String user(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("roles", roleService.getAll());
            return "users/new";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.add(user);
        return MAIN_PAGE;
    }
    @DeleteMapping ("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return MAIN_PAGE;
    }
    @PatchMapping ("/{id}/patch")
    public String update(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("roles", roleService.getAll());
            return "users/edit";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.update(user);
        return MAIN_PAGE;
    }
    @GetMapping("/{id}/patch")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.getAll());
        return "users/edit";
    }
}
