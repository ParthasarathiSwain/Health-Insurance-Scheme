package com.otz.service;

import com.otz.binding.CitizenAppRegistractionInputs;
import com.otz.exception.InvalidSSNException;

public interface IApplicationRegistrationService {
	public Integer registerCitizenApplication(CitizenAppRegistractionInputs inputs)throws InvalidSSNException;
}
