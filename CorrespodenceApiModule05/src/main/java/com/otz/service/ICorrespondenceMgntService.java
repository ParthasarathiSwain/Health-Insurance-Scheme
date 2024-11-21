package com.otz.service;

import com.otz.bindings.CoSummary;

public interface ICorrespondenceMgntService {
	public CoSummary processPendingTriggers() throws Exception;
}
