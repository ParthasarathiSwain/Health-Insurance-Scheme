package com.otz.exception;

public class InvalidSSNException extends Exception {
	public InvalidSSNException(){
		super();
	}
	
	public InvalidSSNException(String msg){
		super(msg);
	}
}
