package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;


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
    public String index (Model model, Principal principal) {
        loadModel(model, principal);
        return "users/all";
    }
    @GetMapping("/new")
    public String add (Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAll());
        return "users/new";
    }
    @PostMapping("/")
    public String user(@ModelAttribute(name = "newUser") @Valid User newUser, BindingResult bindingResult, Model model, Principal principal) {
        if(bindingResult.hasErrors()) {
            loadModel(model, principal);
            return "users/all";
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        try {
            userService.add(newUser);
        } catch (DataIntegrityViolationException e) {
            loadModel(model, principal);
            return "users/all";
        }

        return MAIN_PAGE;
    }
    @DeleteMapping ("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return MAIN_PAGE;
    }
    @PatchMapping ("/{id}")
    public String update(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model, Principal principal) {

        if(bindingResult.hasErrors()) {
            loadModel(model, principal);
            return "users/all";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userService.update(user);
        } catch (DataIntegrityViolationException e) {
            loadModel(model, principal);
            return "users/all";
        }
        return MAIN_PAGE;
    }
    @GetMapping("/{id}/patch")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.getAll());
        return "users/edit";
    }

    private void loadModel(Model model, Principal principal) {
        model.addAttribute("roles", roleService.getAll());
        model.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("today", new Date());
        model.addAttribute("newUser", new User());
    }
}
