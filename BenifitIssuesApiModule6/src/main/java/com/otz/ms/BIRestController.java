package com.otz.ms;

import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otz.service.IbenifiaryIsuranceService;

@RestController
@RequestMapping("/bi-api")
public class BIRestController {
	@Autowired
	private IbenifiaryIsuranceService service;
	
	@GetMapping("/send")
	public ResponseEntity<String> sendAmount()throws Exception{
		JobExecution execution=	service.sendAmountToBenificiaries();
		return new ResponseEntity<String>(execution.getExitStatus().getExitDescription(),HttpStatus.OK);
	}
}
