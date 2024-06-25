package com.example.c23team2.Services;

import com.example.c23team2.Models.CompensationLeave;
import com.example.c23team2.Models.User;

import java.time.LocalDate;
import java.util.List;

public interface CompensationLeaveInterface {

    List<CompensationLeave> findAllCompensationLeaves();

    CompensationLeave findCompensationLeaveById(Integer id);

    CompensationLeave createCompensationLeave(CompensationLeave compensationLeave);

    CompensationLeave updateCompensationLeave(CompensationLeave compensationLeave);

    void deleteCompensationLeave(CompensationLeave compensationLeave);

    List<CompensationLeave> findLeavesEndingAfter(Integer userId,LocalDate startDate);

    double calculateCompensationLeave(Integer userId);

    List<CompensationLeave> findAllCompensationLeavesByUser(User user);

    double calculateHoursWorked(Integer userId);

    List<CompensationLeave> findConflictingLeaves(Integer userId,Integer leaveId,LocalDate startDate,LocalDate endDate);

}