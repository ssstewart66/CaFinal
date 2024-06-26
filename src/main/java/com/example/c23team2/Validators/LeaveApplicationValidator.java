package com.example.c23team2.Validators;

import com.example.c23team2.Interfacemethods.LeaveApplicationService;
import com.example.c23team2.Interfacemethods.PublicHolidayService;
import com.example.c23team2.Models.LeaveApplication;
import com.example.c23team2.Models.User;
import com.example.c23team2.Services.LeaveApplicationServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
public class LeaveApplicationValidator implements Validator {
    @Autowired
    private LeaveApplicationServiceImpl leaveApplicationService;

    @Autowired
    private PublicHolidayService publicHolidayService;

    @Autowired
    private HttpSession session;

    @Override
    public boolean supports(Class<?> clazz) {
        return LeaveApplication.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LeaveApplication leaveApplication = (LeaveApplication) target;
        // 从 session 中获取 user
        User user = (User) session.getAttribute("user");
        leaveApplication.setUser(user);

        // 验证必填项
        if (leaveApplication.getReason() == null || leaveApplication.getReason().isEmpty()) {
            errors.rejectValue("reason", "reason.empty", "Reason is required.");
        }

        if (leaveApplication.getLeaveType() == null) {
            errors.rejectValue("leaveType", "leaveType.empty", "Leave type is required.");
        }

        if (leaveApplication.getStart_date() == null) {
            errors.rejectValue("start_date", "start_date.empty", "Start date is required.");
        }

        if (leaveApplication.getEnd_date() == null) {
            errors.rejectValue("end_date", "end_date.empty", "End date is required.");
        }

        // 验证 start_date 是否在 end_date 之后
        if (leaveApplication.getStart_date() != null && leaveApplication.getEnd_date() != null) {
            if (leaveApplication.getStart_date().isAfter(leaveApplication.getEnd_date())) {
                errors.rejectValue("start_date", "start_date.invalid", "Start date must be before end date.");
                return; // 一旦发现错误，直接返回，避免调用服务层方法
            }
        }

        // 检查开始日期和结束日期是否为工作日
        if (leaveApplication.getStart_date() != null) {
            if (leaveApplicationService.isNonWorkingDay(leaveApplication.getStart_date())) {
                errors.rejectValue("start_date", "start_date.invalid", "Start date must be a working day.");
            }
        }

        if (leaveApplication.getEnd_date() != null) {
            if (leaveApplicationService.isNonWorkingDay(leaveApplication.getEnd_date())) {
                errors.rejectValue("end_date", "end_date.invalid", "End date must be a working day.");
            }
        }

        // 验证开始日期和结束日期不能在过去
        LocalDate today = LocalDate.now();
        if (leaveApplication.getStart_date() != null && leaveApplication.getStart_date().isBefore(today)) {
            errors.rejectValue("start_date", "start_date.past", "Start date cannot be in the past.");
        }

        // 添加日期冲突验证
        if (leaveApplication.getStart_date() != null && leaveApplication.getEnd_date() != null) {
            if (leaveApplication.getStart_date().isAfter(leaveApplication.getEnd_date())) {
                errors.rejectValue("start_date", "start_date.invalid", "Start date must be before end date.");
            } else {
                // 检查是否有重叠的请假申请
                List<LeaveApplication> existingLeaveApplications = leaveApplicationService.findLeaveApplicationsByUserId(user.getId());

                for (LeaveApplication existingLeave : existingLeaveApplications) {
                    if (!Objects.equals(leaveApplication.getId(), existingLeave.getId()) && leaveApplicationService.datesOverlap(leaveApplication, existingLeave)) {
                        errors.rejectValue("start_date", "start_date.overlap", "There is an overlapping leave application.");
                        break;
                    }
                }
            }
        }

        if (leaveApplication.getLeaveType() != null && leaveApplication.getStart_date() != null && leaveApplication.getEnd_date() != null) {

            int requestedDays = leaveApplicationService.calculateTotalDays(leaveApplication.getStart_date(), leaveApplication.getEnd_date());
            int realdays;
            if (requestedDays <= 14) {
                realdays = leaveApplicationService.calculateWorkingDays(leaveApplication.getStart_date(), leaveApplication.getEnd_date(), publicHolidayService.getPublicHolidays());
            } else {
                realdays = requestedDays;

            }

            if (leaveApplication.getLeaveType().getId() == 1 || leaveApplication.getLeaveType().getId() == 2) {
                if (realdays > user.getAnnual_leave_entitlement_last()) {
                    errors.rejectValue("leaveType", "leaveType.insufficient", "Insufficient annual leave balance.");
                }
            }
            if (leaveApplication.getLeaveType().getId() == 3 && requestedDays > user.getMedical_leave_entitlement_last()) {
                {
                    errors.rejectValue("leaveType", "leaveType.insufficient", "Insufficient medical leave balance.");
                }
            }
        }
    }
}