package com.employee.leave.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.leave.dto.LeaveRequestResponseDTO;
import com.employee.leave.enummessage.Status;
import com.employee.leave.global.exceptions.ResourceNotFoundException;
import com.employee.leave.model.Employee;
import com.employee.leave.model.LeaveRequest;
import com.employee.leave.repo.EmployeeRepo;
import com.employee.leave.repo.LeaveRequestRepo;
import com.employee.leave.responsemessage.ResponseMessage;
import com.employee.leave.service.LeaveRequestService;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private LeaveRequestRepo leaveRequestRepo;

	@Override
	public ResponseMessage<LeaveRequestResponseDTO> submitLeaves(LeaveRequestResponseDTO dto) {
		ResponseMessage<LeaveRequestResponseDTO> response = new ResponseMessage<>();

		if (dto.getEmployeeid() == null || dto.getStartDate() == null || dto.getEndDate() == null
				|| dto.getReason() == null) {
			response.setMessage("Invalid input fields.");
			response.setStatus(false);
			response.setStatuscode(400);
			return response;
		}

		Optional<Employee> optionalEmployee = employeeRepo.findById(dto.getEmployeeid());
		if (optionalEmployee.isEmpty()) {
			response.setMessage("Employee not found.");
			response.setStatus(false);
			response.setStatuscode(404);
			return response;
		}
		LocalDate startDate;
		LocalDate endDate;
		try {
			startDate = LocalDate.parse(dto.getStartDate());
			endDate = LocalDate.parse(dto.getEndDate());
		} catch (DateTimeParseException e) {
			response.setMessage("Invalid date format. Please use yyyy-MM-dd.");
			response.setStatus(false);
			response.setStatuscode(400);
			return response;
		}

		if (startDate.isAfter(endDate)) {
			response.setMessage("Start date cannot be after end date.");

			response.setStatus(false);
			response.setStatuscode(400);
			return response;
		}

		// Count leave days excluding weekends
		int totalLeaves = 0;
		LocalDate temp = startDate;
		while (!temp.isAfter(endDate)) {
			DayOfWeek day = temp.getDayOfWeek();
			if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
				totalLeaves++;
			}
			temp = temp.plusDays(1);
		}

		// Create and save leave request
		LeaveRequest leaveRequest = new LeaveRequest();
		leaveRequest.setEmployeeid(optionalEmployee.get());
		leaveRequest.setStartdate(startDate);
		leaveRequest.setEnddate(endDate);
		leaveRequest.setLeaveType(dto.getLeaveType());
		leaveRequest.setReason(dto.getReason());
		leaveRequest.setStatus(Status.PENDING);
		leaveRequest.setDate(LocalDate.now());
		leaveRequest.setTotal_leaves(totalLeaves);

		LeaveRequest saved = leaveRequestRepo.save(leaveRequest);

		LeaveRequestResponseDTO responseDTO = new LeaveRequestResponseDTO();
		responseDTO.setLeaveId(saved.getLeave_id());
		responseDTO.setEmployeeid(saved.getEmployeeid().getEmployeeId());
		responseDTO.setStartDate(saved.getStartdate().toString());
		responseDTO.setEndDate(saved.getEnddate().toString());
		responseDTO.setLeaveType(saved.getLeaveType());
		responseDTO.setReason(saved.getReason());
		responseDTO.setStatus(saved.getStatus().toString());
		responseDTO.setAppliedDate(saved.getDate().toString());
		responseDTO.setTotalLeaves(saved.getTotal_leaves());

		response.setMessage("Leave submitted successfully.");
		response.setStatus(true);
		response.setStatuscode(200);
		response.setData(responseDTO);

		return response;
	}

	@Override
	public ResponseMessage<List<LeaveRequestResponseDTO>> getAllLeaves() {
		List<LeaveRequest> all = leaveRequestRepo.findAll();
		List<LeaveRequestResponseDTO> responseDTOList = new ArrayList<>();

		if (!all.isEmpty()) {
			responseDTOList = all.stream().map(empleave -> {
				LeaveRequestResponseDTO dto = new LeaveRequestResponseDTO();
				dto.setLeaveId(empleave.getLeave_id());

				// converting employee to list<employee>
				List<Employee> empList = new ArrayList<>();
				empList.add(empleave.getEmployeeid());
				dto.setEmployees(empList);

				dto.setStartDate(empleave.getStartdate().toString());
				dto.setEndDate(empleave.getEnddate().toString());
				dto.setLeaveType(empleave.getLeaveType());
				dto.setReason(empleave.getReason());
				dto.setStatus(empleave.getStatus().toString());
				dto.setAppliedDate(empleave.getDate().toString());
				dto.setTotalLeaves(empleave.getTotal_leaves());

				return dto;
			}).collect(Collectors.toList());
		}

		ResponseMessage<List<LeaveRequestResponseDTO>> response = new ResponseMessage<>();
		response.setStatus(true);
		response.setMessage("Leave data fetched successfully");
		response.setStatuscode(200);
		response.setData(responseDTOList);
		return response;
	}

	@Override
	public ResponseMessage<List<LeaveRequestResponseDTO>> getAllPendingLeaves() {

		List<LeaveRequest> byStatus = leaveRequestRepo.findByStatus(Status.PENDING);

		List<LeaveRequestResponseDTO> dtoList = byStatus.stream().map(empleave -> {
			LeaveRequestResponseDTO dto = new LeaveRequestResponseDTO();
			dto.setLeaveId(empleave.getLeave_id());

			List<Employee> empList = new ArrayList<>();
			empList.add(empleave.getEmployeeid());
			dto.setEmployees(empList);

			dto.setStartDate(empleave.getStartdate().toString());
			dto.setEndDate(empleave.getEnddate().toString());
			dto.setLeaveType(empleave.getLeaveType());
			dto.setReason(empleave.getReason());
			dto.setStatus(empleave.getStatus().toString());
			dto.setAppliedDate(empleave.getDate().toString());
			dto.setTotalLeaves(empleave.getTotal_leaves());

			return dto;
		}).collect(Collectors.toList());

		ResponseMessage<List<LeaveRequestResponseDTO>> response = new ResponseMessage<>();
		response.setStatus(true);
		response.setMessage("Fetched all pending leave requests successfully");
		response.setStatuscode(200);
		response.setData(dtoList);

		return response;
	}

	@Override
	public ResponseMessage<LeaveRequestResponseDTO> approveLeave(Long id) {
		Optional<LeaveRequest> byId = leaveRequestRepo.findById(id);
		if (byId.isEmpty()) {
			throw new ResourceNotFoundException("Leave request not found with ID: " + id);
		}

		LeaveRequest leaveRequest = byId.get();
		
		
		leaveRequest.setStatus(Status.APPROVED);
		leaveRequestRepo.save(leaveRequest); // save the update
		
		// Convert to response DTO
		LeaveRequestResponseDTO responseDTO = new LeaveRequestResponseDTO();
		responseDTO.setLeaveId(leaveRequest.getLeave_id());
		responseDTO.setEmployeeid(leaveRequest.getEmployeeid().getEmployeeId());
		responseDTO.setStartDate(leaveRequest.getStartdate().toString());
		responseDTO.setEndDate(leaveRequest.getEnddate().toString());
		responseDTO.setLeaveType(leaveRequest.getLeaveType());
		responseDTO.setReason(leaveRequest.getReason());
		responseDTO.setStatus(leaveRequest.getStatus().toString());
		LocalDate date = leaveRequest.getDate();
		responseDTO.setAppliedDate(date.toString());
		responseDTO.setTotalLeaves(leaveRequest.getTotal_leaves());

		return new ResponseMessage<>("Leave request approved successfully", true, 200, responseDTO);
	}

	@Override
	public ResponseMessage<LeaveRequestResponseDTO> rejectLeave(Long id) {

		Optional<LeaveRequest> byId = leaveRequestRepo.findById(id);
		if (byId.isEmpty()) {
			throw new ResourceNotFoundException("Leave request not found with ID: " + id);
		}

		LeaveRequest leaveRequest = byId.get();
		
		leaveRequest.setStatus(Status.REJECTED);
		leaveRequestRepo.save(leaveRequest);
		
		// Convert to response DTO
		LeaveRequestResponseDTO responseDTO = new LeaveRequestResponseDTO();
		responseDTO.setLeaveId(leaveRequest.getLeave_id());
		responseDTO.setEmployeeid(leaveRequest.getEmployeeid().getEmployeeId());
		responseDTO.setStartDate(leaveRequest.getStartdate().toString());
		responseDTO.setEndDate(leaveRequest.getEnddate().toString());
		responseDTO.setLeaveType(leaveRequest.getLeaveType());
		responseDTO.setReason(leaveRequest.getReason());
		responseDTO.setStatus(leaveRequest.getStatus().toString());
		LocalDate date = leaveRequest.getDate();
		responseDTO.setAppliedDate(date.toString());
		responseDTO.setTotalLeaves(leaveRequest.getTotal_leaves());

		return new ResponseMessage<>("Leave request Rejected successfully", true, 200, responseDTO);
	}


}
