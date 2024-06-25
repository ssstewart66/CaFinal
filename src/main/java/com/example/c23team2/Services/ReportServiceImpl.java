package com.example.c23team2.Services;

import com.example.c23team2.Models.LeaveApplication;
import com.example.c23team2.Repositories.LeaveApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class ReportServiceImpl {
    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    public List<LeaveApplication> getReportData(String type) {
        // 根据类型获取报表数据，具体实现略
        return leaveApplicationRepository.findAll();
    }

    public void exportReportData(PrintWriter writer, String type) {
        List<LeaveApplication> data = getReportData(type);
        // 将数据写入CSV文件，具体实现略
        writer.write("id,username,start_date,end_date,status\n");
        for (LeaveApplication application : data) {
            writer.write(application.getId() + "," + application.getUser().getUsername() + "," + application.getStart_date() + "," + application.getEnd_date() + "," + application.getStatus() + "\n");
        }
    }
}
