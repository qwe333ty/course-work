package com.ak.work.server.service;

import com.ak.work.server.entity.User;
import com.ak.work.server.entity.VisitHistory;

import java.util.List;

public interface UserService {

    User save(User user);

    Iterable<User> saveAll(Iterable<User> users);

    User findUserByUsernameOrEmailAndPassword(String username, String email, String password);

    List<User> findAllExperts();

    List<VisitHistory> getUserVisitHistory(Integer userId);

    List<User> getAll();

    VisitHistory saveVisit(Integer userId);

    void changeUserStatus(Integer userId, Boolean status);
}
