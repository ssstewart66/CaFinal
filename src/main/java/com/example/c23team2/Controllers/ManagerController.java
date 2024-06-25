package com.example.c23team2.Controllers;

import com.example.c23team2.Interfacemethods.AdminService;
import com.example.c23team2.Interfacemethods.LeaveApplicationService;
import com.example.c23team2.Interfacemethods.ManagerService;
import com.example.c23team2.Models.CompensationLeave;
import com.example.c23team2.Models.LeaveApplication;
import com.example.c23team2.Models.LeaveApplicationStatusEnum;
import com.example.c23team2.Models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    public ManagerController(ManagerService managerService, AdminService adminService, LeaveApplicationService leaveApplicationService) {
        this.managerService = managerService;
        this.adminService = adminService;
        this.leaveApplicationService = leaveApplicationService;
    }

    @GetMapping("/status/{status}/approved/{approvedById}")
    public ResponseEntity<List<LeaveApplication>> getLeaveApplicationByStatusAndApprovedById(@PathVariable LeaveApplicationStatusEnum status, @PathVariable int approvedById) {
        List<LeaveApplication> leaveApplications = managerService.findByApprovedByIdAndStatus(approvedById, status);
        return ResponseEntity.ok(leaveApplications);
    }


    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable int id) {
        return adminService.findUserById(id).get();
    }

    @GetMapping("/status/{s}")
    public List<LeaveApplication> getLeaveApplicationByStatus(@PathVariable LeaveApplicationStatusEnum s){
        List<LeaveApplication> res = managerService.getLeaveApplicationsByStatus(s);
        return res;
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateLeaveApplicationStatus(@PathVariable int id, @RequestBody LeaveApplication updatedApplication){
        Optional<LeaveApplication> leaveApplicationOpt = managerService.getLeaveApplicationById(id);
        if(leaveApplicationOpt.isPresent()){
            LeaveApplication leaveApplication = leaveApplicationOpt.get();

            LeaveApplicationStatusEnum oldStatus = leaveApplication.getStatus();

            leaveApplication.setStatus(updatedApplication.getStatus());
            managerService.saveLeaveApplication(leaveApplication);

            if (oldStatus != updatedApplication.getStatus()) {
                sendStatusChangeEmail(leaveApplication);
            }

            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/compensation/status/{status}")
    public List<CompensationLeave> getCompensationLeavesByStatus(@PathVariable LeaveApplicationStatusEnum status) {
        return managerService.getCompensationLeaveByStatus(status);
    }

    @PutMapping("/compensation/{id}/status")
        public ResponseEntity<Void> updateCompensationLeaveStatus(@PathVariable int id, @RequestBody CompensationLeave compensationLeave) {
        Optional<CompensationLeave> compensationLeaveOpt = managerService.getCompensationLeaveById(id);
        if(compensationLeaveOpt.isPresent()){
            CompensationLeave compensationLeave1 = compensationLeaveOpt.get();

            LeaveApplicationStatusEnum oldStatus = compensationLeave1.getStatus();

            compensationLeave1.setStatus(compensationLeave.getStatus());
            managerService.saveCompensationLeave(compensationLeave1);

            if (oldStatus != compensationLeave.getStatus()) {
                //sendStatusChangeEmail(compensationLeave1);
            }

            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    private void sendStatusChangeEmail(LeaveApplication leaveApplication) {
        User user = leaveApplication.getUser();
        String email = user.getEmail();
        String subject = "Leave Application Status Changed";
        String text = "Dear " + user.getUsername() + ",\n\nYour leave application status has been changed to " + leaveApplication.getStatus() + ".\n\nBest regards,\nYour Company";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("JKY2503212461@outlook.com");

        mailSender.send(message);
    }
/*
    private void sendStatusChangeEmail(CompensationLeave compensationLeave) {
        User user = compensationLeave.getUser();
        String email = user.getEmail();
        String subject = "Compensation Leave Status Changed";
        String text = "Dear " + user.getUsername() + ",\n\nYour compensation leave status has been changed to " + compensationLeave.getStatus() + ".\n\nBest regards,\nYour Company";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
    */
}