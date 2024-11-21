package com.otz.service;

import org.springframework.batch.core.JobExecution;

public interface IbenifiaryIsuranceService {
	public JobExecution sendAmountToBenificiaries() throws Exception;
}
