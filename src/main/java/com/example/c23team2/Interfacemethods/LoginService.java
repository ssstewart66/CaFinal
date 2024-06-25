package com.example.c23team2.Interfacemethods;

import com.example.c23team2.Models.User;

import java.util.List;

public interface LoginService {
    List<User> findAllUsers();
    User findUser(Integer userId);
    User createUser(User user);
    User changeUser(User user);
    void removeUser(User user);
    User authenticate(String account, String pwd);
}
