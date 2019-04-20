package com.ak.work.server.service;

import com.ak.work.server.entity.User;

public interface UserService {

    User save(User user);

    Iterable<User> saveAll(Iterable<User> users);

    User findUserByUsernameOrEmailAndPassword(String username, String email, String password);
}
