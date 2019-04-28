package com.ak.work.server.service;

import com.ak.work.server.entity.User;

import java.util.List;

public interface UserService {

    User save(User user);

    Iterable<User> saveAll(Iterable<User> users);

    User findUserByUsernameOrEmailAndPassword(String username, String email, String password);

    List<User> findAllExperts();
}
