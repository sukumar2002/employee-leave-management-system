package com.employee.leave.dto;

import java.util.List;

import com.employee.leave.model.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaveRequestResponseDTO {

	private Long leaveId;
	private Long employeeid;
	private List<Employee> employees;
	private String startDate;
	private String endDate;
	private String leaveType;
	private String reason;
	private String status;
	private String appliedDate;
	private int totalLeaves;

	public LeaveRequestResponseDTO(String status, String appliedDate, int totalLeaves) {
		this.status = status;
		this.appliedDate = appliedDate;
		this.totalLeaves = totalLeaves;
	}

	public LeaveRequestResponseDTO(Long leaveId, Long employeeid, String startDate, String endDate, String leaveType,
			String reason, String status, String appliedDate, int totalLeaves) {
		super();
		this.leaveId = leaveId;
		this.employeeid = employeeid;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leaveType = leaveType;
		this.reason = reason;
		this.status = status;
		this.appliedDate = appliedDate;
		this.totalLeaves = totalLeaves;
	}

	public LeaveRequestResponseDTO(Long leaveId, List<Employee> employees, String startDate, String endDate,
			String leaveType, String reason, String status, String appliedDate, int totalLeaves) {
		super();
		this.leaveId = leaveId;
		this.employees = employees;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leaveType = leaveType;
		this.reason = reason;
		this.status = status;
		this.appliedDate = appliedDate;
		this.totalLeaves = totalLeaves;
	}
	
}
