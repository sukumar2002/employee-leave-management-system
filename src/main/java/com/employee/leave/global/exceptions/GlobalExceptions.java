package com.employee.leave.global.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.employee.leave.responsemessage.ResponseMessage;

@ControllerAdvice
public class GlobalExceptions {

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ResponseMessage> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		String name = ex.getName();
		String type = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "Unknown";
		String value = ex.getValue() != null ? ex.getValue().toString() : "null";

		String message = "Invalid value '" + value + "' for parameter '" + name + "'. Expected type: " + type;

		return ResponseEntity.badRequest().body(new ResponseMessage(message, false, 400));
	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResponseMessage> employeeNotFound(ResourceNotFoundException ex) {
		ResponseMessage<Object> response = new ResponseMessage<>("Employee id not found", false, 400);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ResponseMessage<String>> invalidPassword(InvalidPasswordException ex) {

		String message = ex.getMessage();
		ResponseMessage<String> response = new ResponseMessage<>(message, false, 400);
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ResponseMessage<String>> emailNotFound(UserNotFoundException ex) {
		String message = ex.getMessage();
		ResponseMessage<String> response = new ResponseMessage<>(message, false, 400);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
