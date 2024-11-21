package com.otz.service;

import java.util.Date;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IbenifiaryIsuranceServiceIMPL implements IbenifiaryIsuranceService {
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;
	
	@Override
	public JobExecution sendAmountToBenificiaries() throws Exception{
		//prepare job parameter object
		JobParameter<Date> param=new JobParameter<Date>(new Date(), Date.class);
		Map<String,JobParameter<?>> map= Map.of("param1",param);
		JobParameters params=new JobParameters(map);
		//call the run method
		JobExecution execution=jobLauncher.run(job, params);
		System.out.println("job Execution status : "+execution.getExitStatus());
		return execution;
	}

}
