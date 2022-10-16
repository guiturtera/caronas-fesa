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
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.carona.caronasfesa.entities.User;
import com.carona.caronasfesa.repositories.UserRepository;


@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    
    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(name="nameContains", defaultValue="") String name) {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getAllUsers(@PathVariable("userId") Integer userId, HttpServletResponse response) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + userId));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userId") Integer userId) {
        Optional<User> userToDelete = userRepository.findById(userId);
        if (userToDelete.isPresent()) {
            userRepository.delete(userToDelete.get());
        }
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping(path="/user")
    public ResponseEntity<User> newUser(@RequestBody User newUser) {
        return new ResponseEntity<>(userRepository.saveAndFlush(newUser), HttpStatus.CREATED);
    }
    
    @PutMapping("/user/{userId}")
    public ResponseEntity<User> replaceUser(@RequestBody User newUser, @PathVariable("userId") Integer userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + userId));

        user.setName(newUser.getName());
        
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }
}
