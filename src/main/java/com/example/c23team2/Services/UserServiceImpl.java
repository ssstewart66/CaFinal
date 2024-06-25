package com.example.c23team2.Services;

import com.example.c23team2.Interfacemethods.UserService;
import com.example.c23team2.Models.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.example.c23team2.Repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

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
    @Override
    public User findByAccount(String account){
        return userRepository.findByAccount(account);
    }
    @Override
    public void updateUser(User user) { userRepository.save(user);}

}
