package com.example.c23team2.Repositories;

import com.example.c23team2.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository  extends JpaRepository<User, Integer> {
}
