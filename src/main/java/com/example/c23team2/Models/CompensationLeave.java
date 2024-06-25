package com.example.c23team2.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class CompensationLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //@NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;
    //@NotNull(message = "End date is required")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate endDate;
    //@NotNull(message = "Start period is required")
    private String startPeriod;  // "MORNING", "AFTERNOON", "FULL_DAY"
    //@NotNull(message = "End period is required")
    private String endPeriod;// "MORNING", "AFTERNOON", "FULL_DAY"
    @Min(value = 0, message = "Hours worked must be greater than or equal to 0")
    private double hours_worked;
    private double Leave_days;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;


    @Column(name = "status", columnDefinition = "ENUM('APPLIED', 'APPROVED', 'REJECTED', 'CANCEL', 'UPDATED', 'DELETED')")
    @Enumerated(EnumType.STRING)
    private LeaveApplicationStatusEnum status;//（1:applied、2:approved、3:rejected ...)
    @ManyToOne
    private User approved_by;

    private LocalDateTime create_at;
    private LocalDateTime update_at;
    private String reason;
    private String contact_details;
    private String work_dissemination;

    public CompensationLeave() {}


    @PrePersist
    protected void onCreate() {
        this.create_at = LocalDateTime.now();
        this.update_at = LocalDateTime.now();
        if (this.startDate == null) {
            this.startDate = LocalDate.now();
        }
        if (this.endDate == null) {
            this.endDate = LocalDate.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.update_at = LocalDateTime.now();
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "Start date is required") @FutureOrPresent(message = "Start date must be today or in the future") LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull(message = "Start date is required") @FutureOrPresent(message = "Start date must be today or in the future") LocalDate startDate) {
        this.startDate = startDate;
    }

    public @NotNull(message = "End date is required") @FutureOrPresent(message = "Start date must be today or in the future") LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull(message = "End date is required") @FutureOrPresent(message = "Start date must be today or in the future") LocalDate endDate) {
        this.endDate = endDate;
    }

    public @NotNull(message = "Start period is required") String getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(@NotNull(message = "Start period is required") String startPeriod) {
        this.startPeriod = startPeriod;
    }

    public @NotNull(message = "End period is required") String getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(@NotNull(message = "End period is required") String endPeriod) {
        this.endPeriod = endPeriod;
    }

    @Min(value = 0, message = "Hours worked must be greater than or equal to 0")
    public double getHours_worked() {
        return hours_worked;
    }

    public void setHours_worked(@Min(value = 0, message = "Hours worked must be greater than or equal to 0") double hours_worked) {
        this.hours_worked = hours_worked;
    }

    public LeaveApplicationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(LeaveApplicationStatusEnum status) {
        this.status = status;
    }

    public User getApproved_by() {
        return approved_by;
    }

    public void setApproved_by(User approved_by) {
        this.approved_by = approved_by;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public LocalDateTime getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(LocalDateTime update_at) {
        this.update_at = update_at;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getContact_details() {
        return contact_details;
    }

    public void setContact_details(String contact_details) {
        this.contact_details = contact_details;
    }

    public String getWork_dissemination() {
        return work_dissemination;
    }

    public void setWork_dissemination(String work_dissemination) {
        this.work_dissemination = work_dissemination;
    }

    public double getLeave_days() {
        return Leave_days;
    }

    public void setLeave_days(double leave_days) {
        Leave_days = leave_days;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
