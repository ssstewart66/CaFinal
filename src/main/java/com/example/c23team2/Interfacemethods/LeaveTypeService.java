package com.example.c23team2.Interfacemethods;

import com.example.c23team2.Models.LeaveType;

import java.util.List;
import java.util.Optional;

public interface LeaveTypeService {
    List<LeaveType> findAllLeaveTypes();
    LeaveType createLeaveType(LeaveType leaveType);
    void deleteLeaveType(LeaveType leaveType);
    Optional<LeaveType> findLeaveTypeById(int id);
    LeaveType updateLeaveType(LeaveType leaveType);
    List<LeaveType> findLeaveTypesByIds(List<Integer> ids);
}
