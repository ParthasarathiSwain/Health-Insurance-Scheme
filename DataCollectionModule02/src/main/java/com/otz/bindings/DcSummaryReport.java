package com.otz.bindings;

import java.util.List;

import lombok.Data;

@Data
public class DcSummaryReport {
	private CitizenAppRegistractionInputs citizenDetails;
	private	String planName;	
	private	EducationInputs eduDetails;
	private	List<ChildInputs> childrenDetails;
	private	IncomeInputs incomeDetails;
	
}
