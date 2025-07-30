package com.employee.leave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSaveDTO {

	private String name;
	private String email;
	private String password;
	private String phone;
	private String department;
	private String joiningDate;
}
