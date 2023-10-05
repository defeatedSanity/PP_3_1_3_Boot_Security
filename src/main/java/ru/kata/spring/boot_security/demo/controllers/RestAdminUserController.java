package ru.kata.spring.boot_security.demo.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.PersonErrorResponse;
import ru.kata.spring.boot_security.demo.util.PersonNotCreatedException;
import ru.kata.spring.boot_security.demo.util.PersonNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/rest/users")
public class RestAdminUserController {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public RestAdminUserController(ModelMapper modelMapper, UserService userService, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return userService.findAll().stream().map(this::convertToUserDTO).collect(Collectors.toList());
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable("id") Long id) {
        return convertToUserDTO(userService.findById(id));

    }

    @PatchMapping()
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid UserDTO userDTO,
                                             BindingResult bindingResult) {
        check(bindingResult);
        userService.update(convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid UserDTO userDTO,
                                          BindingResult bindingResult) {
        check(bindingResult);
        userService.add(convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private User convertToUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return modelMapper.map(userDTO, User.class);
    }

    private void check (BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            errorMessage.append("{");
            errors.forEach(e -> errorMessage
                    .append('"')
                    .append(e.getField())
                    .append('"')
                    .append(": ")
                    .append('"')
                    .append(e.getDefaultMessage())
                    .append('"')
                    .append(","));
            errorMessage.deleteCharAt(errorMessage.length() - 1);
            errorMessage.append("}");
            throw new PersonNotCreatedException(errorMessage.toString());
        }
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this id was not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }




}
