package com.example.c23team2.Interfacemethods;

import com.example.c23team2.Models.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findUser(Integer userId);
    User createUser(User user);
    User changeUser(User user);
    void removeUser(User user);
    User authenticate(String account, String pwd);
    User findByAccount(String account);
    void updateUser(User user);
}
