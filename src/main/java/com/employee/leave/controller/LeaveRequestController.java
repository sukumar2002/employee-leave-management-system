package com.employee.leave.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.leave.dto.LeaveRequestResponseDTO;
import com.employee.leave.responsemessage.ResponseMessage;
import com.employee.leave.service.LeaveRequestService;

@RestController
@RequestMapping(value = "/leaves")
public class LeaveRequestController {

	@Autowired
	private LeaveRequestService leaveRequestService;

	@PostMapping("/submitleave")
	public ResponseEntity<ResponseMessage<LeaveRequestResponseDTO>> submitLeave(
			@RequestBody LeaveRequestResponseDTO leaveRequestResponseDTO) {

		ResponseMessage<LeaveRequestResponseDTO> response = leaveRequestService.submitLeaves(leaveRequestResponseDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@GetMapping(value="/getAllLeaves")
	public ResponseEntity<ResponseMessage<List<LeaveRequestResponseDTO>>> getAllLeaves(){
		ResponseMessage<List<LeaveRequestResponseDTO>> allLeaves = leaveRequestService.getAllLeaves();
		return new ResponseEntity<>(allLeaves,HttpStatus.OK);
	}
	
	@GetMapping(value="/getAllPendingLeaves")
	public ResponseEntity<ResponseMessage<List<LeaveRequestResponseDTO>>> getAllPendingLeaves(){
		ResponseMessage<List<LeaveRequestResponseDTO>> allLeaves = leaveRequestService.getAllPendingLeaves();
		return new ResponseEntity<>(allLeaves,HttpStatus.OK);
	}
	
	@PutMapping(value="/{id}/approve")
	public ResponseEntity<ResponseMessage<LeaveRequestResponseDTO>> approveLeave(@PathVariable Long id){
		ResponseMessage<LeaveRequestResponseDTO> approveLeave = leaveRequestService.approveLeave(id);
		return new ResponseEntity<>(approveLeave,HttpStatus.OK);
		
	}
	
	@PutMapping(value="/{id}/reject")
	public ResponseEntity<ResponseMessage<LeaveRequestResponseDTO>> rejectLeave(@PathVariable Long id){
		ResponseMessage<LeaveRequestResponseDTO> approveLeave = leaveRequestService.rejectLeave(id);
		return new ResponseEntity<>(approveLeave,HttpStatus.OK);
	}
	
}
