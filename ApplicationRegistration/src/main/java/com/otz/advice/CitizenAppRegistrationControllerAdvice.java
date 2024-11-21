package com.otz.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.otz.exception.InvalidSSNException;


@RestControllerAdvice
public class CitizenAppRegistrationControllerAdvice {
	@ExceptionHandler(InvalidSSNException.class)
	public ResponseEntity<String> handelinvalidSSN(InvalidSSNException exception){
		return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handeAllSSN(Exception exception){
		return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
	}
}
