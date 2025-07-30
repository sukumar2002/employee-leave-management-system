package com.employee.leave.service;

import java.util.List;

import com.employee.leave.dto.LeaveRequestResponseDTO;
import com.employee.leave.responsemessage.ResponseMessage;

public interface LeaveRequestService {
	public ResponseMessage<LeaveRequestResponseDTO> submitLeaves(LeaveRequestResponseDTO leaveRequestResponseDTO);
	
	public ResponseMessage<List<LeaveRequestResponseDTO>> getAllLeaves();
	
	public ResponseMessage<List<LeaveRequestResponseDTO>> getAllPendingLeaves();
	
	public ResponseMessage<LeaveRequestResponseDTO> approveLeave(Long id);
	
	public ResponseMessage<LeaveRequestResponseDTO> rejectLeave(Long id);
	
}
