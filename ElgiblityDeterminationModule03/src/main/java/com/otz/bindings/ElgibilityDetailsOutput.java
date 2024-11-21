package com.otz.bindings;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ElgibilityDetailsOutput {
	private String holderName;
	private String planName;
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private Double benifitAmount;
	private String denialReason;
}
