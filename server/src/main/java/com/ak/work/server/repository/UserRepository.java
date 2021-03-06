package com.ak.work.server.repository;

import com.ak.work.server.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("select u from User u where u.email = ?2 or u.username = ?1")
    User findUserByUsernameOrEmailAndPassword(String username, String email);

    @Modifying
    @Query(value = "update users set blocked = ?2 where id = ?1", nativeQuery = true)
    void updateUserStatus(Integer userId, Boolean status);
}
