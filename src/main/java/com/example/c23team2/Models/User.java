package com.example.c23team2.Models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "account must be provided")
    private String account;

    private String password;

    private String username;

    private Integer role;

    private Integer department;

    private String email;

    private Integer LeaveApproverid;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<LeaveApplication> leaveApplications;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<CompensationLeave> compensationLeaves;

    @OneToMany(mappedBy = "approved_by")
    private List<CompensationLeave> approvedCompensationLeaves;

    @OneToMany(mappedBy = "approved_by")
    private List<LeaveApplication> approvedLeaveApplications;

    private int annual_leave_entitlement;
    private int annual_leave_entitlement_last;
    private int medical_leave_entitlement;
    private int medical_leave_entitlement_last;
    private double compensation_leave_balance;
    private double compensation_leave_balance_last;

    public User(){}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull(message = "account must be provided") String getAccount() {
        return account;
    }

    public void setAccount(@NotNull(message = "account must be provided") String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLeaveApproverid() {
        return LeaveApproverid;
    }

    public void setLeaveApproverid(Integer leaveApproverid) {
        LeaveApproverid = leaveApproverid;
    }

    public List<LeaveApplication> getLeaveApplications() {
        return leaveApplications;
    }

    public void setLeaveApplications(List<LeaveApplication> leaveApplications) {
        this.leaveApplications = leaveApplications;
    }

    public List<CompensationLeave> getCompensationLeaves() {
        return compensationLeaves;
    }

    public void setCompensationLeaves(List<CompensationLeave> compensationLeaves) {
        this.compensationLeaves = compensationLeaves;
    }

    public List<CompensationLeave> getApprovedCompensationLeaves() {
        return approvedCompensationLeaves;
    }

    public void setApprovedCompensationLeaves(List<CompensationLeave> approvedCompensationLeaves) {
        this.approvedCompensationLeaves = approvedCompensationLeaves;
    }

    public List<LeaveApplication> getApprovedLeaveApplications() {
        return approvedLeaveApplications;
    }

    public void setApprovedLeaveApplications(List<LeaveApplication> approvedLeaveApplications) {
        this.approvedLeaveApplications = approvedLeaveApplications;
    }

    public int getAnnual_leave_entitlement() {
        return annual_leave_entitlement;
    }

    public void setAnnual_leave_entitlement(int annual_leave_entitlement) {
        this.annual_leave_entitlement = annual_leave_entitlement;
    }

    public int getAnnual_leave_entitlement_last() {
        return annual_leave_entitlement_last;
    }

    public void setAnnual_leave_entitlement_last(int annual_leave_entitlement_last) {
        this.annual_leave_entitlement_last = annual_leave_entitlement_last;
    }

    public int getMedical_leave_entitlement() {
        return medical_leave_entitlement;
    }

    public void setMedical_leave_entitlement(int medical_leave_entitlement) {
        this.medical_leave_entitlement = medical_leave_entitlement;
    }

    public int getMedical_leave_entitlement_last() {
        return medical_leave_entitlement_last;
    }

    public void setMedical_leave_entitlement_last(int medical_leave_entitlement_last) {
        this.medical_leave_entitlement_last = medical_leave_entitlement_last;
    }

    public double getCompensation_leave_balance() {
        return compensation_leave_balance;
    }

    public void setCompensation_leave_balance(double compensation_leave_balance) {
        this.compensation_leave_balance = compensation_leave_balance;
    }

    public double getCompensation_leave_balance_last() {
        return compensation_leave_balance_last;
    }

    public void setCompensation_leave_balance_last(double compensation_leave_balance_last) {
        this.compensation_leave_balance_last = compensation_leave_balance_last;
    }
}
