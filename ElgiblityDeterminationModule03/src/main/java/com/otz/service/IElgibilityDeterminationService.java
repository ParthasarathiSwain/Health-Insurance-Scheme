package com.otz.service;

import com.otz.bindings.ElgibilityDetailsOutput;

public interface IElgibilityDeterminationService {
	public ElgibilityDetailsOutput determineElgibility(int caseNo);
}
