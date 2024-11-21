package com.otz.bindings;

import java.time.LocalDate;

import lombok.Data;
@Data
public class CitizenAppRegistractionInputs {
	private String fullName;
	private	String email;
	private	String gender;
	private	Long phoneNo;
	private	Long ssn;
	private	LocalDate dob;
}
