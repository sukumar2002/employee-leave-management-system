package com.employee.leave.service;

import java.util.List;

import com.employee.leave.dto.EmployeeLoginDTO;
import com.employee.leave.dto.EmployeeResponseDTO;
import com.employee.leave.dto.EmployeeSaveDTO;
import com.employee.leave.responsemessage.ResponseMessage;

public interface EmployeeService {
	public ResponseMessage<EmployeeResponseDTO> saveDetails(EmployeeSaveDTO employeedto);

	public ResponseMessage<List<EmployeeResponseDTO>> getAllEmployees();

	public ResponseMessage<EmployeeResponseDTO> getEmployeeById(Long id);

	public ResponseMessage<EmployeeResponseDTO> getEmployeeByMail(String email);

	public ResponseMessage<EmployeeResponseDTO> getEmployeeByPhno(String phno);

	public ResponseMessage<EmployeeResponseDTO> deleteByMail(String email);

	@SuppressWarnings("rawtypes")
	public ResponseMessage deleteAll();

	public ResponseMessage<EmployeeResponseDTO> updateDetails(Long id, EmployeeSaveDTO employeedto);

	public ResponseMessage login(EmployeeLoginDTO employeelogin);
}
