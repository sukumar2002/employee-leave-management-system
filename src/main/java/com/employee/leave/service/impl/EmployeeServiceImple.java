package com.employee.leave.service.impl;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.leave.dto.EmployeeLoginDTO;
import com.employee.leave.dto.EmployeeResponseDTO;
import com.employee.leave.dto.EmployeeSaveDTO;
import com.employee.leave.global.exceptions.InvalidPasswordException;
import com.employee.leave.global.exceptions.ResourceNotFoundException;
import com.employee.leave.global.exceptions.UserNotFoundException;
import com.employee.leave.model.Employee;
import com.employee.leave.repo.EmployeeRepo;
import com.employee.leave.responsemessage.ResponseMessage;
import com.employee.leave.service.EmployeeService;

@Service
public class EmployeeServiceImple implements EmployeeService {

	@Autowired
	private EmployeeRepo employeerepo;

	// Utility method to decode Base64 password
	@SuppressWarnings("unused")
	private String decodePassword(String encodedPassword) {
		return new String(Base64.getDecoder().decode(encodedPassword));
	}

	@Override
	public ResponseMessage<EmployeeResponseDTO> saveDetails(EmployeeSaveDTO employeedto) {
		ResponseMessage<EmployeeResponseDTO> responseMessage = new ResponseMessage<>();

		try {
			Optional<Employee> byEmail = employeerepo.findByEmail(employeedto.getEmail());
			if (byEmail.isPresent()) {
				return new ResponseMessage<>("Email already exists", false, 400);
			}
			Optional<Employee> byPhno = employeerepo.findByPhno(employeedto.getPhone());
			if (byPhno.isPresent()) {
				return new ResponseMessage<>("Phone already exists", false, 400);
			}
			if (employeedto.getPhone().trim().length() != 10) {
				return new ResponseMessage<>("Phone number must be 10 digits", false, 400);
			}

			if (employeedto.getName() != null && employeedto.getEmail() != null && employeedto.getPhone() != null
					&& employeedto.getDepartment() != null && employeedto.getJoiningDate() != null) {

				Employee emp = new Employee();
				emp.setEmployee_name(employeedto.getName());
				emp.setEmail(employeedto.getEmail());

				// Base64 encode the password
				String encodedPassword = Base64.getEncoder().encodeToString(employeedto.getPassword().getBytes());
				emp.setPassword(encodedPassword);

				emp.setPhno(employeedto.getPhone());
				emp.setDept_name(employeedto.getDepartment());
				emp.setJoiningdate(LocalDate.parse(employeedto.getJoiningDate()));

				Employee saved = employeerepo.save(emp);

				EmployeeResponseDTO dto = new EmployeeResponseDTO();
				dto.setEmployee_id(saved.getEmployeeId());
				dto.setName(saved.getEmployee_name());
				dto.setEmail(saved.getEmail());
				dto.setPhone(saved.getPhno());
				dto.setDepartment(saved.getDept_name());
				dto.setJoiningDate(saved.getJoiningdate().toString());

				responseMessage.setMessage("Saved Successfully");
				responseMessage.setStatus(true);
				responseMessage.setStatuscode(200);
				responseMessage.setData(dto);
			} else {
				responseMessage.setStatus(false);
				responseMessage.setStatuscode(400);
				responseMessage.setMessage("Required fields are missing");
			}
		} catch (Exception e) {
			responseMessage.setStatus(false);
			responseMessage.setStatuscode(500);
			responseMessage.setMessage("An error occurred while saving employee: " + e.getMessage());
		}

		return responseMessage;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseMessage<List<EmployeeResponseDTO>> getAllEmployees() {
		if (employeerepo.count() == 0) {
			return new ResponseMessage("No Data Available.", false, 400);
		}

		List<Employee> allEmployees = employeerepo.findAll();
		List<EmployeeResponseDTO> dtoList = allEmployees.stream().map(employee -> {
			EmployeeResponseDTO dto = new EmployeeResponseDTO();
			dto.setEmployee_id(employee.getEmployeeId());
			dto.setName(employee.getEmployee_name());
			dto.setEmail(employee.getEmail());
			dto.setPhone(employee.getPhno());
			dto.setDepartment(employee.getDept_name());
			dto.setJoiningDate(employee.getJoiningdate().toString());
			return dto;
		}).collect(Collectors.toList());

		ResponseMessage<List<EmployeeResponseDTO>> response = new ResponseMessage<>();
		response.setMessage("Employee data retrieved successfully");
		response.setStatus(true);
		response.setStatuscode(200);
		response.setData(dtoList);
		return response;
	}

	@Override
	public ResponseMessage<EmployeeResponseDTO> getEmployeeById(Long id) {
		Optional<Employee> byId = employeerepo.findById(id);

		if (byId.isPresent()) {
			Employee emp = byId.get();
			EmployeeResponseDTO dto = new EmployeeResponseDTO();
			dto.setEmployee_id(emp.getEmployeeId());
			dto.setName(emp.getEmployee_name());
			dto.setEmail(emp.getEmail());
			dto.setPhone(emp.getPhno());
			dto.setDepartment(emp.getDept_name());
			dto.setJoiningDate(emp.getJoiningdate().toString());
			return new ResponseMessage<>("Employee details retrieved successfully", true, 200, dto);
		}

		return new ResponseMessage<>("Employee Not found", false, 400);
	}

	@Override
	public ResponseMessage<EmployeeResponseDTO> getEmployeeByMail(String email) {
		Optional<Employee> byEmail = employeerepo.findByEmail(email);

		if (byEmail.isPresent()) {
			Employee emp = byEmail.get();
			EmployeeResponseDTO dto = new EmployeeResponseDTO();
			dto.setEmployee_id(emp.getEmployeeId());
			dto.setName(emp.getEmployee_name());
			dto.setEmail(emp.getEmail());
			dto.setPhone(emp.getPhno());
			dto.setDepartment(emp.getDept_name());
			dto.setJoiningDate(emp.getJoiningdate().toString());
			return new ResponseMessage<>("Employee details retrieved successfully", true, 200, dto);
		}

		return new ResponseMessage<>("Employee Not found", false, 400);
	}

	@Override
	public ResponseMessage<EmployeeResponseDTO> getEmployeeByPhno(String phno) {
		Optional<Employee> byPhno = employeerepo.findByPhno(phno);

		if (byPhno.isPresent()) {
			Employee emp = byPhno.get();
			EmployeeResponseDTO dto = new EmployeeResponseDTO();
			dto.setEmployee_id(emp.getEmployeeId());
			dto.setName(emp.getEmployee_name());
			dto.setEmail(emp.getEmail());
			dto.setPhone(emp.getPhno());
			dto.setDepartment(emp.getDept_name());
			dto.setJoiningDate(emp.getJoiningdate().toString());
			return new ResponseMessage<>("Employee details retrieved successfully", true, 200, dto);
		}

		return new ResponseMessage<>("Employee Not found", false, 400);
	}

	@Override
	public ResponseMessage<EmployeeResponseDTO> deleteByMail(String email) {
		Optional<Employee> byEmail = employeerepo.findByEmail(email);

		if (byEmail.isPresent()) {
			employeerepo.delete(byEmail.get());
			return new ResponseMessage<>("Employee details deleted successfully", true, 200);
		}

		return new ResponseMessage<>("Employee Not found", false, 400);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResponseMessage deleteAll() {
		if (employeerepo.count() == 0) {
			return new ResponseMessage("No employees to delete.", false, 400);
		}

		employeerepo.deleteAll();
		return new ResponseMessage("All employees deleted successfully.", true, 200);
	}

	@Override
	public ResponseMessage<EmployeeResponseDTO> updateDetails(Long id, EmployeeSaveDTO employeedto) {
		Optional<Employee> byId = employeerepo.findById(id);

		if (byId.isPresent()) {
			Employee employee = byId.get();

			if (employeedto.getEmail() != null && !employeedto.getEmail().isBlank()) {
				employee.setEmail(employeedto.getEmail());
			}

			if (employeedto.getPhone() != null && !employeedto.getPhone().isBlank()) {
				employee.setPhno(employeedto.getPhone());
			}

			Employee updatedEmp = employeerepo.save(employee);

			EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
			responseDTO.setEmployee_id(updatedEmp.getEmployeeId());
			responseDTO.setName(updatedEmp.getEmployee_name());
			responseDTO.setEmail(updatedEmp.getEmail());
			responseDTO.setPhone(updatedEmp.getPhno());
			responseDTO.setDepartment(updatedEmp.getDept_name());
			responseDTO.setJoiningDate(updatedEmp.getJoiningdate().toString());

			return new ResponseMessage<>("Email and Phone updated successfully", true, 200, responseDTO);
		} else {
			throw new ResourceNotFoundException("Employee not found with ID: " + id);
		}
	}

	@Override
	public ResponseMessage login(EmployeeLoginDTO employeelogin) {
		Optional<Employee> byEmail = employeerepo.findByEmail(employeelogin.getEmail());
		if (byEmail.isEmpty()) {
			throw new UserNotFoundException("Email not found");
		}
		if (byEmail.isPresent()) {
			Employee employee = byEmail.get();
			String decodedPassword = new String(Base64.getDecoder().decode(employee.getPassword()));

			if (!decodedPassword.equals(employeelogin.getPassword())) {
				throw new InvalidPasswordException("Invalid password");
			}

		}
		return new ResponseMessage("Login successfully", true, 200);
	}

}
