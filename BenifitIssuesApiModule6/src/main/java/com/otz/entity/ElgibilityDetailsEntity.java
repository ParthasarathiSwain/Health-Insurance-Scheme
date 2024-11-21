package com.otz.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="ELGIBILITY_DETEMINATION")
public class ElgibilityDetailsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer edTraceId;
	private Integer caseNo;
	@Column(length=40)
	private String holderName;
	private Long holderssn;
	@Column(length=40)
	private String planName;
	@Column(length=40)
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private Double benifitAmount;
	@Column(length = 100)
	private String denialReason;
	private String bankName;
	private Long accountNumber;
}
