package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAll();
    void add(Role role);
    void delete (Long id);
    Role getById(Long id);
    void update(Role role);

    Role findByName(String role);
}
