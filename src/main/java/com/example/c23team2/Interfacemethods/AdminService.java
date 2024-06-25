package com.example.c23team2.Interfacemethods;

import com.example.c23team2.Models.User;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<User> findAllUsers();
    //User createUser(User user);
    void deleteUser(User user);
    Optional<User> findUserById(int id);
    User updateUser(User user);
}
