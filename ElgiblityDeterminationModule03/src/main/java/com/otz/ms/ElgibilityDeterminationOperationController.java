package com.otz.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otz.bindings.ElgibilityDetailsOutput;
import com.otz.service.IElgibilityDeterminationService;

@RestController
@RequestMapping("/ed-api")
public class ElgibilityDeterminationOperationController {
	@Autowired
	private IElgibilityDeterminationService service;
	
	@GetMapping("/checkelgibility/{caseNo}")
	public ResponseEntity<ElgibilityDetailsOutput> checkEligibility(@PathVariable Integer caseNo){
		ElgibilityDetailsOutput list=service.determineElgibility(caseNo);
		return new ResponseEntity<ElgibilityDetailsOutput>(list,HttpStatus.CREATED);
	}
}
