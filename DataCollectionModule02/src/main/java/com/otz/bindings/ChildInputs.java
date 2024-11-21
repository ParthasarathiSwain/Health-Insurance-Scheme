package com.otz.bindings;

import java.time.LocalDate;

import lombok.Data;
@Data
public class ChildInputs {
	private Integer childId;
	private Integer caseNo;
	private LocalDate childDOB;
	private Long childSSN;
}
