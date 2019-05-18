package com.ak.work.server.controller;

import com.ak.work.server.entity.Credentials;
import com.ak.work.server.entity.User;
import com.ak.work.server.entity.VisitHistory;
import com.ak.work.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.urn}/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/authentication")
    public ResponseEntity<User> checkIfAuthenticated(@RequestBody Credentials credentials) {
        log.info("User try to login.");
        User user = service.findUserByUsernameOrEmailAndPassword(
                credentials.getLogin(), credentials.getLogin(), credentials.getPassword());
        if (user != null) {
            log.info("In system as {}.", user.getUsername());
        } else {
            log.warn("User not found.");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<User> registration(@RequestBody User user) {
        return new ResponseEntity<>(service.save(user), HttpStatus.OK);
    }

    @GetMapping("/experts")
    public ResponseEntity<List<User>> findAllExerts() {
        return new ResponseEntity<>(service.findAllExperts(), HttpStatus.OK);
    }

    @GetMapping("/{id}/visitHistory")
    public ResponseEntity<List<VisitHistory>> userVisitHistory(
            @PathVariable(name = "id") Integer userId) {
        return new ResponseEntity<>(service.getUserVisitHistory(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}/addVisitHistory")
    public ResponseEntity<VisitHistory> saveVisitHistory(
            @PathVariable(name = "id") Integer userId) {
        return new ResponseEntity<>(service.saveVisit(userId), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
}
