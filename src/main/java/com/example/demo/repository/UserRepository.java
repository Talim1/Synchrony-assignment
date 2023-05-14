package com.example.demo.repository;


import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = :username and u.password = :password")
    Optional<User> findUserByUserNameAndPassword(@Param("username") String userName,
                                                @Param("password") String password);

    @Modifying
    @Query("update User u set u.authenticated = true where u.username = :username")
    int authenticateUser(@Param("username") String userName);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findUserByUserName(@Param("username") String userName);
}
