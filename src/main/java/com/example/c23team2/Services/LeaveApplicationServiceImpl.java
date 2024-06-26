package com.example.c23team2.Services;

import com.example.c23team2.Interfacemethods.LeaveApplicationService;
import com.example.c23team2.Interfacemethods.PublicHolidayService;
import com.example.c23team2.Models.LeaveApplication;
import com.example.c23team2.Models.LeaveApplicationStatusEnum;
import com.example.c23team2.Models.PublicHoliday;
import com.example.c23team2.Repositories.LeaveApplicationRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaveApplicationServiceImpl implements LeaveApplicationService {
    @Resource
    private LeaveApplicationRepository leaveApplicationRepository;
    @Autowired
    private PublicHolidayService publicHolidayService;

    @Override
    @Transactional
    public boolean saveLeaveApplication(LeaveApplication leaveApplication) {
        if(leaveApplicationRepository.save(leaveApplication)==null){
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public List<LeaveApplication> findAllLeaveApplications(){
        return leaveApplicationRepository.findAll();
    }

    @Override
    @Transactional
    public LeaveApplication createApplyLeave(LeaveApplication leaveApplication){
        return leaveApplicationRepository.save(leaveApplication);
    }

    @Override
    @Transactional
    public LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication){
        return leaveApplicationRepository.save(leaveApplication);
    }

    @Override
    @Transactional
    public void deleteLeaveApplication(LeaveApplication leaveApplication){
        leaveApplicationRepository.delete(leaveApplication);
    }

    @Override
    @Transactional
    public LeaveApplication findLeaveApplicationById(int id){
        return leaveApplicationRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public List<LeaveApplication> findLeaveApplicationByStatus(LeaveApplicationStatusEnum status){
        return leaveApplicationRepository.findLeaveApplicationByStatus(status);
    }

    @Override
    @Transactional
    public List<LeaveApplication> findLeaveApplicationsByUserId(int userId){
        return leaveApplicationRepository.findLeaveApplicationsByUserId(userId);
    }

    public boolean isAnnualLeaveEligible(LeaveApplication leaveApplication, List<PublicHoliday> publicHolidays) {
        LocalDate startDate = leaveApplication.getStart_date();
        LocalDate endDate = leaveApplication.getEnd_date();

        if (isNonWorkingDay(startDate) || isNonWorkingDay(endDate)) {
            return false;
        }
        return true;
    }

    public boolean isNonWorkingDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }
        // 检查是否为公共假日
        List<PublicHoliday> publicHolidays = publicHolidayService.getPublicHolidays();
        for (PublicHoliday holiday : publicHolidays) {
            if (holiday.getHoliday_date().equals(date)) {
                return true;
            }
        }
        return false;
    }

    public int calculateTotalDays(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException(start + " < " + end);
        }
        // Use Math.toIntExact to safely convert long to int
        return Math.toIntExact(start.datesUntil(end.plusDays(1)).count());
    }

    public int calculateWorkingDays(LocalDate start, LocalDate end, List<PublicHoliday> publicHolidays) {
        Set<LocalDate> holidays = publicHolidays.stream()
                .map(PublicHoliday::getHoliday_date)
                .collect(Collectors.toSet());

        long workingDaysCount = start.datesUntil(end.plusDays(1))
                .filter(date -> !isNonWorkingDay(date) && !holidays.contains(date))
                .count();

        // Use Math.toIntExact to safely convert long to int
        return Math.toIntExact(workingDaysCount);
    }

    public boolean datesOverlap(LeaveApplication newLeave, LeaveApplication existingLeave) {
        if (existingLeave.getStatus() == LeaveApplicationStatusEnum.CANCEL || existingLeave.getStatus() == LeaveApplicationStatusEnum.REJECTED) {
            return false;
        }
        return !newLeave.getEnd_date().isBefore(existingLeave.getStart_date()) && !newLeave.getStart_date().isAfter(existingLeave.getEnd_date());
    }

    @Override
    @Transactional
    public Page<LeaveApplication> findLeaveApplicationsByUserIdOrderByUpdatedAtDesc(int userId, Pageable pageable) {
        return leaveApplicationRepository.findLeaveApplicationsByUserIdOrderByUpdatedAtDesc(userId, pageable);
    }

    public List<LeaveApplication> getLeaveApplicationsBetweenDates(LocalDate startDateStr, LocalDate endDateStr) {
        return leaveApplicationRepository.findLeaveApplicationsBetweenDates(startDateStr, endDateStr);
    }

}
