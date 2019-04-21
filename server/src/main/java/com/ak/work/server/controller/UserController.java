package com.ak.work.server.controller;

import com.ak.work.server.entity.Credentials;
import com.ak.work.server.entity.User;
import com.ak.work.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.urn}/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/authentication")
    public ResponseEntity<User> checkIfAuthenticated(@RequestBody Credentials credentials) {
        User user = service.findUserByUsernameOrEmailAndPassword(
                credentials.getLogin(), credentials.getLogin(), credentials.getPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<User> registration(@RequestBody User user) {
        return new ResponseEntity<>(service.save(user), HttpStatus.OK);
    }
}
