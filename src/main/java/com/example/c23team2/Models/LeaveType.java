package com.example.c23team2.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int maxdays;

    @OneToMany(mappedBy = "leaveType")
    private List<LeaveApplication> leaveApplications;

    public LeaveType(){}

    public LeaveType(String name, int maxdays) {
        this.setName(name);
        this.setMaxdays(maxdays);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxdays() {
        return maxdays;
    }

    public void setMaxdays(int maxdays) {
        this.maxdays = maxdays;
    }

    public List<LeaveApplication> getLeaveApplications() {
        return leaveApplications;
    }

    public void setLeaveApplications(List<LeaveApplication> leaveApplications) {
        this.leaveApplications = leaveApplications;
    }
}
