package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin/roles/")
public class AdminRoleController {
    private final RoleService roleService;

    private final String mainPage = "redirect:/admin/roles/";

    @Autowired
    public AdminRoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String all(Model model) {
        model.addAttribute("roles", roleService.getAll());
        return "/roles/all";
    }
    @PostMapping("/")
    public String add(@ModelAttribute @Valid Role role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/roles/new";
        }
        roleService.add(role);
        return mainPage;
    }
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("role", new Role());
        return "/roles/new";
    }
    @GetMapping("/{id}/patch")
    public String update (Model model, @PathVariable(name = "id") Long id){
        model.addAttribute("role", roleService.getById(id));
        return "/roles/edit";
    }
    @PatchMapping("{id}")
    public String update (@ModelAttribute @Valid Role role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/roles/edit";
        }
        roleService.update(role);
        return mainPage;
    }
    @DeleteMapping ("{id}")
    public String delete(@PathVariable("id") Long id) {
        roleService.delete(id);
        return mainPage;
    }
}
