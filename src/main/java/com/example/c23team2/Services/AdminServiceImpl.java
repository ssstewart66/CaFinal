package com.example.c23team2.Services;

import com.example.c23team2.Interfacemethods.AdminService;
import com.example.c23team2.Models.User;
import com.example.c23team2.Repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<User> findAllUsers() {
        return adminRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(int id) {
        return adminRepository.findById(id);
    }

    @Override
    @Transactional
    public User updateUser(User user){
        return adminRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user){
        adminRepository.delete(user);
    }

}