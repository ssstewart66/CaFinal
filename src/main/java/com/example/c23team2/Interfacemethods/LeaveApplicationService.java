package com.example.c23team2.Interfacemethods;

import com.example.c23team2.Models.LeaveApplication;
import com.example.c23team2.Models.LeaveApplicationStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LeaveApplicationService {

    List<LeaveApplication> findAllLeaveApplications();

    LeaveApplication createApplyLeave(LeaveApplication leaveApplication);

    LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication);

    void deleteLeaveApplication(LeaveApplication leaveApplication);

    boolean saveLeaveApplication(LeaveApplication leaveApplication);

    //Optional<LeaveApplication> findLeaveApplicationById(int id);

    LeaveApplication findLeaveApplicationById(int id);

    List<LeaveApplication> findLeaveApplicationByStatus(LeaveApplicationStatusEnum status);

    List<LeaveApplication> findLeaveApplicationsByUserId(int userId);

    Page<LeaveApplication> findLeaveApplicationsByUserIdOrderByUpdatedAtDesc(int userId, Pageable pageable);

}
