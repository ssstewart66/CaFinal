package com.example.c23team2.Services;

import com.example.c23team2.Interfacemethods.LoginService;
import com.example.c23team2.Models.User;
import com.example.c23team2.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User findUser(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    @Override
    public User createUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    @Override
    public User changeUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    @Override
    public void removeUser(User user) {
        userRepository.delete(user);
    }
    @Transactional
    @Override
    public User authenticate(String account, String pwd) {
        return userRepository.findByAccountAndPassword(account, pwd);
    }

}
