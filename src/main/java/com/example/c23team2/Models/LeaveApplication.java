package com.example.c23team2.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LeaveApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonBackReference
    private User user;

    @ManyToOne
    private User approved_by;

    @ManyToOne
    private LeaveType leaveType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start_date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end_date;

    @Column(name="status", columnDefinition = "ENUM('APPLIED', 'APPROVED', 'REJECTED', 'CANCEL', 'UPDATED', 'DELETED')")
    @Enumerated(EnumType.STRING)
    private LeaveApplicationStatusEnum status;

    private String reason;
    private String work_dissemination;
    private String contact_details;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public LeaveApplication() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public LeaveApplicationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(LeaveApplicationStatusEnum status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getWork_dissemination() {
        return work_dissemination;
    }

    public void setWork_dissemination(String work_dissemination) {
        this.work_dissemination = work_dissemination;
    }

    public String getContact_details() {
        return contact_details;
    }

    public void setContact_details(String contact_details) {
        this.contact_details = contact_details;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public User getApproved_by() {
        return approved_by;
    }

    public void setApproved_by(User approved_by) {
        this.approved_by = approved_by;
    }

    @Transient
    public int getUserId() {
        return user != null ? user.getId() : null;
    }
}