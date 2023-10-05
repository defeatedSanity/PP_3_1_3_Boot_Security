package ru.kata.spring.boot_security.demo.dto;

import org.springframework.format.annotation.DateTimeFormat;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class UserDTO {

    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 20, message = "name should be from 2 to 20")
    private String name;
    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 1, max = 20, message = "Name should be from 2 to 20")
    private String surname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date should not be empty.")
    private Date birthdate;
    @NotEmpty(message = "password should not be empty")
    private String password;

    @NotEmpty(message = "username should not be empty")
    @Size(min = 1, max = 20, message = "username should be from 4 to 20")
    private String username;



    private Collection<Role> roles = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }


    public String getPureRoles() {
        return !roles.isEmpty()? roles
                .stream()
                .map(Role::getName)
                .map(r -> r.substring(5))
                .reduce("", (s1, s2) -> s1 + ", " + s2)
                .substring(1) : "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
