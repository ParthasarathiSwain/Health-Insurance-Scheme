package com.otz.bindings;

import lombok.Data;

@Data
public class ElgibilityDetails {
	private Integer caseNo;
	private String holderName;
	private String planName;
	private String planStatus;
	private Double benifitAmount;
	private String bankName;
	private Long accountNumber;
	private Long holderssn;
}
