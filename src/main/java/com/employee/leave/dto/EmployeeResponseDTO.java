package com.employee.leave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {

	private Long employee_id;
	private String name;
	private String email;
	private String phone;
	private String department;
	private String joiningDate;
	//private LeaveRequestResponseDTO leaveRequest;
}
