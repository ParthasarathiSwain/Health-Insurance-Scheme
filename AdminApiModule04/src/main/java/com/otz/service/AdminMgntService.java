package com.otz.service;

import java.util.List;
import java.util.Map;

import com.otz.bindings.PlanData;

public interface AdminMgntService {
	public String registerPlan (PlanData plan); 
	public Map<Integer, String> getPlanCategories(); 
	public List<PlanData> showAllPlans();
	public String updatePlan (PlanData plan); 
	public String deletePlan (Integer planld); 
	public String changePlanStatus (Integer planld, String status); 
	public PlanData showPlanById(Integer planId);
}
