package com.employee.leave.responsemessage;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage<T> {

	private String message;
	private boolean status;
	private int statuscode;
	private T data;
	
	public ResponseMessage(String message,boolean status,int statuscode) {
		this.message=message;
		this.status=status;
		this.statuscode=statuscode;
	}

	
	
}
