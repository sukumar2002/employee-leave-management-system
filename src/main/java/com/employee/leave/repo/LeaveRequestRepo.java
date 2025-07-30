package com.employee.leave.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.leave.enummessage.Status;
import com.employee.leave.model.LeaveRequest;

public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, Long> {

	
	public List<LeaveRequest> findByStatus(Status status);
	
	public Optional<LeaveRequest> findById(Long id);
}
