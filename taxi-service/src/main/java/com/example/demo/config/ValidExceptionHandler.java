package com.example.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author MayilaiMuthu
 * @since 15-07-2023
 *
 */
@RestControllerAdvice
public class ValidExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public APIResponse<?> handleMethodArgumentException(MethodArgumentNotValidException exception) {
		APIResponse<?> response = new APIResponse<>();
		List<ERROR> errors = new ArrayList<>();
		exception.getBindingResult().getFieldErrors().forEach(e->{
			ERROR error = new ERROR(e.getField(), e.getDefaultMessage());
			errors.add(error);
		});
		response.setStatus(ApplicationConstants.MESSAGE_ERROR);
		response.setErrors(errors);
		return response;
	}

}