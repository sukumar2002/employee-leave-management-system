package com.employee.leave.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.leave.dto.EmployeeLoginDTO;
import com.employee.leave.dto.EmployeeResponseDTO;
import com.employee.leave.dto.EmployeeSaveDTO;
import com.employee.leave.responsemessage.ResponseMessage;
import com.employee.leave.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping(value = "/saveEmployeeDetails")
	public ResponseEntity<ResponseMessage<EmployeeResponseDTO>> saveEmployeeDetails(
			@Valid @RequestBody EmployeeSaveDTO employeesavedto) {
		ResponseMessage<EmployeeResponseDTO> saveDetails = employeeService.saveDetails(employeesavedto);
		return new ResponseEntity<>(saveDetails, HttpStatus.CREATED);
	}

	@GetMapping(value = "/getAllEmployees")
	public ResponseEntity<ResponseMessage<List<EmployeeResponseDTO>>> getAllEmployees() {
		ResponseMessage<List<EmployeeResponseDTO>> response = employeeService.getAllEmployees();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/byId/{id}")
	public ResponseEntity<ResponseMessage<EmployeeResponseDTO>> getEmployeeById(@PathVariable Long id) {
		ResponseMessage<EmployeeResponseDTO> employeeById = employeeService.getEmployeeById(id);
		return new ResponseEntity<>(employeeById, HttpStatus.OK);
	}

	@GetMapping(value = "/byEmail/{email}")
	public ResponseEntity<ResponseMessage<EmployeeResponseDTO>> getEmployeeByEmail(@PathVariable("email") String mail) {
		ResponseMessage<EmployeeResponseDTO> employeeByMail = employeeService.getEmployeeByMail(mail);
		return new ResponseEntity<>(employeeByMail, HttpStatus.OK);
	}

	@GetMapping(value = "/byPhno/{phno}")
	public ResponseEntity<ResponseMessage<EmployeeResponseDTO>> getEmployeeByPhno(@PathVariable String phno) {
		ResponseMessage<EmployeeResponseDTO> employeeByPhno = employeeService.getEmployeeByPhno(phno);
		return new ResponseEntity<>(employeeByPhno, HttpStatus.OK);
	}
	
	
	@DeleteMapping(value="/deleteByMail/{email}")
	public ResponseEntity<ResponseMessage<EmployeeResponseDTO>> deleteByEmail(@PathVariable("email") String mail){
		ResponseMessage<EmployeeResponseDTO> deleteByMail = employeeService.deleteByMail(mail);
		return new ResponseEntity<>(deleteByMail,HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping(value="/deleteAllEmployees")
	public ResponseEntity<ResponseMessage> deleteAll(){
		ResponseMessage deleteAll = employeeService.deleteAll();
		return new ResponseEntity<>(deleteAll,HttpStatus.OK);
	}
	
	@PutMapping(value="/update/{id}")
	public ResponseEntity<ResponseMessage<EmployeeResponseDTO>> updateDetails(@PathVariable Long id,@RequestBody EmployeeSaveDTO employeedto){
		ResponseMessage<EmployeeResponseDTO> updateDetails = employeeService.updateDetails(id, employeedto);
		return new ResponseEntity<>(updateDetails,HttpStatus.OK);
	}
	
	@GetMapping(value="/login")
	public ResponseEntity<ResponseMessage> login(@RequestBody EmployeeLoginDTO employeelogin){
		ResponseMessage login = employeeService.login(employeelogin);
		return new ResponseEntity<>(login,HttpStatus.OK);
	}
}
