package com.otz.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otz.bindings.CoSummary;
import com.otz.service.ICorrespondenceMgntService;

@RestController
@RequestMapping("/coTriggers-api")
public class CoTriggersOperationController {
	@Autowired
	private ICorrespondenceMgntService service;
	@GetMapping("/process")
	public ResponseEntity<CoSummary> proccessAndUpdateTriggers() throws Exception{
		CoSummary summary=service.processPendingTriggers();
		return new ResponseEntity<CoSummary>(summary,HttpStatus.OK);
	}
}
