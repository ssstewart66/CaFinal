package com.example.c23team2.Repositories;

import com.example.c23team2.Models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CompensationLeaveRepository extends JpaRepository<CompensationLeave, Integer> {
    @Query("select l from CompensationLeave l where l.id = :keyword")
    CompensationLeave findCompensationLeaveById(@Param("keyword") int keyword);

    @Query("select l from CompensationLeave l where l.user.id = :keyword")
    List<CompensationLeave> findCompensationLeaveByuserId(@Param("keyword") int keyword);

    @Query("select l from CompensationLeave l where l.status = :keyword")
    List<CompensationLeave> findCompensationLeaveByStatus(@Param("keyword") LeaveApplicationStatusEnum keyword);

    @Query("SELECT h FROM CompensationLeave h WHERE h.user.id = :userId AND h.id <> :leaveId AND (h.startDate < :endDate AND h.endDate > :startDate)")
    List<CompensationLeave> findConflictingLeaves(@Param("userId") Integer userId, @Param("leaveId") Integer leaveId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    List<CompensationLeave> findByEndDateAfter(LocalDate startDate);

    List<CompensationLeave> findAllByUser(User user);

    @Query("SELECT h FROM CompensationLeave h WHERE h.user.id = :userId AND h.endDate >= :startDate")
    List<CompensationLeave> findLeavesEndingAfter(@Param("userId") Integer userId, @Param("startDate") LocalDate startDate);

    @Query("select l from CompensationLeave l where l.user.LeaveApproverid = :keyword and l.status = :keyword1")
    List<CompensationLeave> findByApprovedByIdAndStatus(@Param("keyword")int approvedById,@Param("keyword1") LeaveApplicationStatusEnum status);


}
