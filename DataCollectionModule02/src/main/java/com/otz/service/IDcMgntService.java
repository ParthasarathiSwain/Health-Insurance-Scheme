package com.otz.service;

import java.util.List;

import com.otz.bindings.ChildInputs;
import com.otz.bindings.DcSummaryReport;
import com.otz.bindings.EducationInputs;
import com.otz.bindings.IncomeInputs;
import com.otz.bindings.PlanSelectionInputs;

public interface IDcMgntService {
	public Integer generateCaseNo(Integer appId);
	public List<String> showAllPlanNames();
	public Integer   savePlanSelection (PlanSelectionInputs plan);
	public Integer   saveIncomeDetails (IncomeInputs income);
	public Integer   saveEducationDetails (EducationInputs education);
	public Integer   saveChildrenDetails (List<ChildInputs>  children);
	public DcSummaryReport showDcSummary(Integer caseNo);
}
