package com.example.c23team2.Controllers;

import com.example.c23team2.Interfacemethods.LeaveApplicationService;
import com.example.c23team2.Models.LeaveApplication;
import com.example.c23team2.Services.LeaveApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private LeaveApplicationServiceImpl leaveApplicationService;

    @GetMapping
    public List<LeaveApplication> generateReport(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return leaveApplicationService.getLeaveApplicationsBetweenDates(startDate, endDate);
    }
}