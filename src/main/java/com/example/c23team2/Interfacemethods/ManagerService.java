package com.example.c23team2.Interfacemethods;

import com.example.c23team2.Models.CompensationLeave;
import com.example.c23team2.Models.LeaveApplication;
import com.example.c23team2.Models.LeaveApplicationStatusEnum;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface ManagerService {
    List<LeaveApplication> getLeaveApplicationsByStatus(LeaveApplicationStatusEnum status);

    Optional<LeaveApplication> getLeaveApplicationById(int id);

    boolean saveLeaveApplication(LeaveApplication leaveApplication);

    List<CompensationLeave> getCompensationLeaveByStatus(LeaveApplicationStatusEnum status);

    Optional<CompensationLeave> getCompensationLeaveById(int id);

    boolean saveCompensationLeave(CompensationLeave compensationLeave);

    List<LeaveApplication> findByApprovedByIdAndStatus(int approvedById, LeaveApplicationStatusEnum status);

    List<CompensationLeave> findByApprovedByIdAndStatus1(int approvedById, LeaveApplicationStatusEnum status);


}
