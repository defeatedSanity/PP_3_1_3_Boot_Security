package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    void add(User user);
    void delete (Long id);
    User findById(Long id);
    User findByUsername(String username);
    void update(User user);



}
