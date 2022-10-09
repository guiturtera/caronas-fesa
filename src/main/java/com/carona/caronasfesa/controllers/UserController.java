package com.carona.caronasfesa.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.carona.caronasfesa.entities.User;
import com.carona.caronasfesa.repositories.UserRepository;


@RestController
public class UserController {
    @Autowired
    UserRepository user;
    
    @GetMapping("/user")
    public List<User> getAllUsers(@RequestParam(name="nameContains", defaultValue="") String name) {
        return user.findAll();
    }
    
    @GetMapping("/user/{userId}")
    public User getAllUsers(@PathVariable("userId") Integer userId, HttpServletResponse response) {
        Optional<User> foundUser = user.findById(userId);
        if (!foundUser.isPresent()) {
            response.setStatus(404);
            return null;
        }
        return foundUser.get();
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable("userId") Integer userId) {
        Optional<User> userToDelete = user.findById(userId);
        if (userToDelete.isPresent()) {
            user.delete(userToDelete.get());
        }
    }
    
    @PostMapping(path="/user")
    public User newEmployee(@RequestBody User newUser) {
        return user.saveAndFlush(newUser);
    }
    
    @PutMapping("/user/{userId}")
    public User replaceUser(@RequestBody User newUser, @PathVariable("userId") Integer userId, HttpServletResponse response) {
        Optional<User> userToDelete = user.findById(userId);
        if (!userToDelete.isPresent()) {
            response.setStatus(204);
            return null;
        } else {
            newUser.setId(userId);
            return user.save(newUser);
        }
    }
}
