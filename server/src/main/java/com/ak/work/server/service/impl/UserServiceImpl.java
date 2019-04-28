package com.ak.work.server.service.impl;

import com.ak.work.server.entity.Expert;
import com.ak.work.server.entity.User;
import com.ak.work.server.repository.UserRepository;
import com.ak.work.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public Iterable<User> saveAll(Iterable<User> users) {
        return repository.saveAll(users);
    }

    @Override
    public User findUserByUsernameOrEmailAndPassword(String username, String email, String password) {
        return repository.findUserByUsernameOrEmailAndPassword(username, email, password);
    }

    @Override
    public List<User> findAllExperts() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .filter(user -> user instanceof Expert)
                .collect(Collectors.toList());
    }
}
