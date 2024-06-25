package com.example.c23team2.Services;

import com.example.c23team2.Interfacemethods.ManagerService;
import com.example.c23team2.Models.CompensationLeave;
import com.example.c23team2.Models.LeaveApplication;
import com.example.c23team2.Models.LeaveApplicationStatusEnum;
import com.example.c23team2.Repositories.CompensationLeaveRepository;
import com.example.c23team2.Repositories.LeaveApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerServiceImpl implements ManagerService {

    private LeaveApplicationRepository leaveApplicationRepository;
    private CompensationLeaveRepository compensationLeaveRepository;

    @Autowired
    public ManagerServiceImpl(LeaveApplicationRepository leaveApplicationRepository, CompensationLeaveRepository compensationLeaveRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.compensationLeaveRepository = compensationLeaveRepository;
    }

    public List<LeaveApplication> getLeaveApplicationsByStatus(LeaveApplicationStatusEnum status) {
        return leaveApplicationRepository.findLeaveApplicationByStatus(status);
    }

    public Optional<LeaveApplication> getLeaveApplicationById(int id) {
        return leaveApplicationRepository.findById(id);
    }

    public List<CompensationLeave> getCompensationLeaveByStatus(LeaveApplicationStatusEnum status){
        return compensationLeaveRepository.findCompensationLeaveByStatus(status);
    }

    public Optional<CompensationLeave> getCompensationLeaveById(int id){
        return compensationLeaveRepository.findById(id);
    }

    @Transactional
    public boolean saveLeaveApplication(LeaveApplication leaveApplication) {
        if(leaveApplicationRepository.save(leaveApplication)==null){
            return false;
        }
        return true;
    }

    @Transactional
    public boolean saveCompensationLeave(CompensationLeave compensationLeave){
        if(compensationLeaveRepository.save(compensationLeave)==null){
            return false;
        }
        return true;
    }

    @Override
    public List<LeaveApplication> findByApprovedByIdAndStatus(int approvedById, LeaveApplicationStatusEnum status) {
        return leaveApplicationRepository.findByApprovedByIdAndStatus(approvedById, status);
    }

    @Override
    public List<CompensationLeave> findByApprovedByIdAndStatus1(int approvedById, LeaveApplicationStatusEnum status){
        return compensationLeaveRepository.findByApprovedByIdAndStatus(approvedById, status);
    }

}
