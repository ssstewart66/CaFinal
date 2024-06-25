package com.example.c23team2.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.c23team2.Models.CompensationLeave;
import com.example.c23team2.Models.User;
import com.example.c23team2.Repositories.CompensationLeaveRepository;
import com.example.c23team2.Repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CompensationLeaveImpl implements CompensationLeaveInterface {
    @Autowired
    private CompensationLeaveRepository compensationLeaveRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public List<CompensationLeave> findAllCompensationLeaves() {
        return compensationLeaveRepository.findAll();
    }

    @Override
    @Transactional
    public CompensationLeave findCompensationLeaveById(Integer id) {
        return compensationLeaveRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public CompensationLeave createCompensationLeave(CompensationLeave compensationLeave) {
        return compensationLeaveRepository.save(compensationLeave);
    }

    @Override
    @Transactional
    public CompensationLeave updateCompensationLeave(CompensationLeave compensationLeave) {
        return compensationLeaveRepository.save(compensationLeave);
    }

    @Override
    @Transactional
    public void deleteCompensationLeave(CompensationLeave compensationLeave) {
        compensationLeaveRepository.delete(compensationLeave);
    }

    @Override
    @Transactional
    public List<CompensationLeave> findLeavesEndingAfter(Integer userId,LocalDate startDate) {
        return compensationLeaveRepository.findLeavesEndingAfter(userId,startDate);
    }

    @Override
    @Transactional
    public List<CompensationLeave> findConflictingLeaves(Integer userId,Integer leaveId,LocalDate startDate,LocalDate endDate) {
        return compensationLeaveRepository.findConflictingLeaves(userId,leaveId,startDate,endDate);
    }

    @Override
    @Transactional
    public List<CompensationLeave> findAllCompensationLeavesByUser(User user){
        return compensationLeaveRepository.findAllByUser(user);
    }


    @Override
    @Transactional
    public double calculateCompensationLeave(Integer userId) {

        User user = userRepository.findById(userId).get(); // 假设你已经有 userService

        List<CompensationLeave> histories = compensationLeaveRepository.findAllByUser(user);

        double totalHoursWorked = histories.stream().mapToDouble(CompensationLeave::getHours_worked).sum();

        double totalLeaveDays = histories.stream().mapToDouble(CompensationLeave::getLeave_days).sum();

        double v = (totalHoursWorked / 4) / 2 - totalLeaveDays;
        return v;
    }

    @Override
    @Transactional
    public double calculateHoursWorked(Integer userId) {
        User user = userRepository.findById(userId).get(); // 假设你已经有 userService
        List<CompensationLeave> histories = compensationLeaveRepository.findAllByUser(user);
        double totalHoursWorked = histories.stream().mapToDouble(CompensationLeave::getHours_worked).sum();
        double v = (totalHoursWorked / 4) / 2;
        return v;
    }




}
