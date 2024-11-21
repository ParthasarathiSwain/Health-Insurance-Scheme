package com.otz.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.otz.binding.CitizenAppRegistractionInputs;
import com.otz.service.IApplicationRegistrationService;

@RestController
@RequestMapping("/citizenAR-api")
public class CitizenApllicationRegistrationController {
	@Autowired
	private IApplicationRegistrationService registrationService;

//	@PostMapping("/save")
//	public ResponseEntity<String> saveCitizenApplication(@RequestBody CitizenAppRegistractionInputs inputs){
//		//use Service
//		try {
//			int appId=registrationService.registerCitizenApplication(inputs);
//			if (appId>0) {
//				return new ResponseEntity<String> ("Citizen Application is Registered with id "+appId,HttpStatus.CREATED);
//			}else {
//				return new ResponseEntity<String> ("Invalid SSN or Citizen must belongs to California "+appId,HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			return new ResponseEntity<String> (e.getMessage(),HttpStatus.BAD_REQUEST);
//		}
//	}
	@PostMapping("/save")
	public ResponseEntity<String> saveCitizenApplication(@RequestBody CitizenAppRegistractionInputs inputs)throws Exception{
			int appId=registrationService.registerCitizenApplication(inputs);
			return new ResponseEntity<String> ("Citizen Application is Registered with id "+appId,HttpStatus.CREATED);
	}
}
