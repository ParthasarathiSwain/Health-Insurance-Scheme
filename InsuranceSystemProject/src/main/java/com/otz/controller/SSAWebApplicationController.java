package com.otz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ssa-api-web")
public class SSAWebApplicationController {
	@GetMapping("/find/{ssn}")
	public ResponseEntity<String> getStateBySSN(@PathVariable Integer ssn){
		if (String.valueOf(ssn).length()!=9) {
			return new ResponseEntity<String>("Invalid ssn",HttpStatus.BAD_REQUEST);
		}
		//get the ssn
		int stateCode=ssn%100;
		String stateName=null;
		if(stateCode==01)
			stateName="Washington Dc";
		else if(stateCode==02)
			stateName="Ohio";
		else if(stateCode==03)
			stateName="Texas";
		else if(stateCode==04)
			stateName="California";
		else if(stateCode==05)
			stateName="florida";
		else 
			stateName="Invalid ssn";
		return new ResponseEntity<String>(stateName,HttpStatus.OK);
	}
}
