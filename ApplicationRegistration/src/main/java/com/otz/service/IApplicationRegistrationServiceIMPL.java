package com.otz.service;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.otz.binding.CitizenAppRegistractionInputs;
import com.otz.entity.CitizenAppRegistrationEntity;
import com.otz.exception.InvalidSSNException;
import com.otz.repository.IApplicationRegistrationRepository;

import reactor.core.publisher.Mono;

@Service
public class IApplicationRegistrationServiceIMPL  implements IApplicationRegistrationService{
	
	@Autowired
	private IApplicationRegistrationRepository citizenRepo;
	/*
	 * @Autowired 
	 * private RestTemplate template;
	 */
	@Autowired 
	private WebClient client;
	
	@Value("${ar.ssa-web.url}")
	private String ssaWebUrl;
	@Value("${ar.state}")
	private String targetState;
	
	@Override
	public Integer registerCitizenApplication(CitizenAppRegistractionInputs inputs)throws InvalidSSNException {
		//perform webService call to check wheather SSN is valid or not and to get the state time
		//ResponseEntity<String> response=template.exchange(ssaWebUrl, HttpMethod.GET,null,String.class,inputs.getSsn());
		//perform WebService call to check wheather SSN is valid or not and to get the state name(using web client)
		
		//get stateName
		//String stateName=response.getBody(); 
	//	String stateName=client.get().uri(ssaWebUrl,inputs.getSsn()).retrieve().bodyToMono(String.class).block();
		Mono<String> response=client.get().uri(ssaWebUrl,inputs.getSsn()).retrieve().onStatus(HttpStatus.BAD_REQUEST::equals,res-> 
					res.bodyToMono(String.class).map(ex-> new InvalidSSNException("Invalid SSN"))).bodyToMono(String.class);
		String stateName=response.block();
		//register citizen if the he belongs to California state
		if (stateName.equalsIgnoreCase(targetState)) {
			//Prepare the entity Object
			CitizenAppRegistrationEntity entity=new CitizenAppRegistrationEntity();
			BeanUtils.copyProperties(inputs, entity);
			entity.setStateName(stateName);
			int appId=citizenRepo.save(entity).getAppId();
			return appId;
		}
		throw new InvalidSSNException("invalid SSN");
	}

}
