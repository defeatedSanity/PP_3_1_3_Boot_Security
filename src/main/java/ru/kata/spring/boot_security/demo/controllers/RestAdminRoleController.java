package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;
import java.util.List;


@RestController
@RequestMapping("/admin/rest/roles")
public class RestAdminRoleController {

    private final RoleService roleService;


@Autowired
    public RestAdminRoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping
    public List<Role> getAll() {
        return roleService.getAll();
    }
}
