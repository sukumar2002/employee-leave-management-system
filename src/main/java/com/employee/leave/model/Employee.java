package com.employee.leave.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId;

	@NotBlank(message = "Employee Name is required")
	@NotNull(message = "It should not be empty")
	@Column(name = "employee_name")
	private String employee_name;

	@Email(message = "Mail is required")
	@NotNull(message = "It should not be empty")
	@Column(name = "email")
	private String email;
	
	@NotBlank(message="Password is required")
	@NotNull(message="It should not be empty")
	@Column(name="password")
	private String password;

	@NotBlank(message = "Phone number is required")
	@NotNull(message = "It should not be empty")
	@Column(name = "phone_number")
	private String phno;

	@NotBlank(message = "Department name is required")
	@NotNull(message = "It should not be empty")
	@Column(name = "dept_name")
	private String dept_name;

	@NotNull(message = "It should not be empty")
	@Column(name = "joining_date")
	private LocalDate joiningdate;

//	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<LeaveRequest> leaveRequests;

}
