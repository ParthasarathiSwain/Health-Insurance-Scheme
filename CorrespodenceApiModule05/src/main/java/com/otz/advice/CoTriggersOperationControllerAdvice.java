package com.otz.advice;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CoTriggersOperationControllerAdvice {
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<String> handelFileNotFoundException(FileNotFoundException f){
		return new ResponseEntity<String>(f.getMessage(),HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handelAllException(Exception e){
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.OK);
	}
}
