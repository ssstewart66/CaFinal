package com.example.c23team2.Repositories;

import com.example.c23team2.Models.LeaveApplication;
import com.example.c23team2.Models.LeaveApplicationStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Integer> {
    @Query("select l from LeaveApplication l where l.id = :keyword")
    LeaveApplication findLeaveApplicationById(@Param("keyword") int keyword);

    @Query("select l from LeaveApplication l where l.status = :keyword")
    List<LeaveApplication> findLeaveApplicationByStatus(@Param("keyword") LeaveApplicationStatusEnum keyword);

    @Query("select l from LeaveApplication l where l.user.id = :keyword")
    List<LeaveApplication> findLeaveApplicationsByUserId(@Param("keyword") int keyword);


    @Query("select l from LeaveApplication l where l.user.LeaveApproverid = :keyword and l.status = :keyword1")
    List<LeaveApplication> findByApprovedByIdAndStatus(@Param("keyword")int approvedById,@Param("keyword1") LeaveApplicationStatusEnum status);


    @Query("SELECT la FROM LeaveApplication la WHERE la.user.id = :userId ORDER BY la.updated_at DESC")
    Page<LeaveApplication> findLeaveApplicationsByUserIdOrderByUpdatedAtDesc(@Param("userId") int userId, Pageable pageable);

    //List<LeaveApplication> findLeaveApplicationsByUserId(int userId);

    @Query("SELECT l FROM LeaveApplication l WHERE l.start_date >= :startDate AND l.end_date <= :endDate")
    List<LeaveApplication> findLeaveApplicationsBetweenDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);


}
