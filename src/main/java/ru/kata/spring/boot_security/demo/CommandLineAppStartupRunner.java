package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Date;
import java.util.List;

//Метод для добавления админа в пустую базу
@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CommandLineAppStartupRunner(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String...args) throws Exception {
        User admin = new User();
        admin.setName("Ivan");
        admin.setSurname("Ivanov");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setUsername("admin");
        admin.setBirthdate(new Date());
        admin.setRoles(List.of(new Role("ROLE_ADMIN"), new Role("ROLE_USER")));
        userService.add(admin);
    }
}
