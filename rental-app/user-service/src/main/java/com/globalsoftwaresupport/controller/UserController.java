package com.globalsoftwaresupport.controller;

import com.globalsoftwaresupport.model.PatchUserRequest;
import com.globalsoftwaresupport.model.User;
import com.globalsoftwaresupport.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") String userId,
                       @RequestBody PatchUserRequest request) {
        var user = Optional.ofNullable(userId)
                .map(u -> Long.valueOf(userId))
                .map(service::getUser)
                .orElseThrow();

        service.update(user, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String userId) {
        var user = Optional.ofNullable(userId)
                .map(u -> Long.valueOf(userId))
                .map(service::getUser)
                .orElseThrow();

        service.delete(user.getUserId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody User user) {
        service.create(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") String userId) {
        return Optional.ofNullable(userId)
                .map(u -> Long.valueOf(userId))
                .map(service::getUser)
                .orElseThrow();
    }
}
