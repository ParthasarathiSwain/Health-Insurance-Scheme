package com.otz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otz.bindings.ChildInputs;
import com.otz.bindings.CitizenAppRegistractionInputs;
import com.otz.bindings.DcSummaryReport;
import com.otz.bindings.EducationInputs;
import com.otz.bindings.IncomeInputs;
import com.otz.bindings.PlanSelectionInputs;
import com.otz.entity.CitizenAppRegistrationEntity;
import com.otz.entity.DcCaseEntity;
import com.otz.entity.DcChildrenEntity;
import com.otz.entity.DcEducationEntity;
import com.otz.entity.DcIncomeEntity;
import com.otz.entity.PlanEntity;
import com.otz.repository.IApplicationRegistrationRepository;
import com.otz.repository.IDcCaseRepository;
import com.otz.repository.IDcChildrenRepository;
import com.otz.repository.IDcEducationRepository;
import com.otz.repository.IDcIncomeRepository;
import com.otz.repository.IPlanRepository;

@Service
public class DcMgntServiceIMPL implements IDcMgntService{
	@Autowired
	private IDcCaseRepository caseRepo;
	@Autowired
	private IDcChildrenRepository childRepo;
	@Autowired
	private IDcEducationRepository educationRepo;
	@Autowired
	private IDcIncomeRepository incomeRepo;
	@Autowired
	private IPlanRepository planRepo;
	@Autowired
	private IApplicationRegistrationRepository appRepo;

	@Override
	public Integer generateCaseNo(Integer appId) {
		Optional<CitizenAppRegistrationEntity> appCitizen=appRepo.findById(appId);
		if (appCitizen.isPresent()) {
			DcCaseEntity caseEntity=new DcCaseEntity();
			caseEntity.setAppId(appId);
			return caseRepo.save(caseEntity).getCaseNo();
		}
		return 0;
	}

	@Override
	public List<String> showAllPlanNames() {
		List<PlanEntity> planList=planRepo.findAll();
		//get only plan Names using streaming api
		List<String> planNameList=planList.stream().map(plan->plan.getPlanName()).toList();
		return planNameList;
	}

	@Override
	public Integer savePlanSelection(PlanSelectionInputs plan) {
		Optional<DcCaseEntity> opt=caseRepo.findById(plan.getCaseNo());
		if (opt.isPresent()) {
			DcCaseEntity caseEntity=opt.get();
			caseEntity.setPlanId(plan.getPlanId());
			//update the DcCaseEntity
			caseRepo.save(caseEntity);
			return caseEntity.getCaseNo();
		}
		return 0;
	}

	@Override
	public Integer saveIncomeDetails(IncomeInputs income) {
		//Convert binding object data to entity object data
		DcIncomeEntity incomeEntity=new DcIncomeEntity();
		BeanUtils.copyProperties(income, incomeEntity);
		incomeRepo.save(incomeEntity);

		return income.getCaseNo();
	}

	@Override
	public Integer saveEducationDetails(EducationInputs education) {
		DcEducationEntity eduEntity=new DcEducationEntity();
		BeanUtils.copyProperties(education, eduEntity);
		educationRepo.save(eduEntity);
		return education.getCaseNo();
	}

	@Override
	public Integer saveChildrenDetails(List<ChildInputs>  children) {
		children.forEach(child->{
			DcChildrenEntity childrenEntity=new DcChildrenEntity();
			BeanUtils.copyProperties(child, childrenEntity);
			//save each childEntity
			childRepo.save(childrenEntity);
		});
		return children.get(0).getCaseNo();
	}

	@Override
	public DcSummaryReport showDcSummary(Integer caseNo) {
		//get multiple entity based on caseNo 
		DcIncomeEntity incomeEntity=incomeRepo.findByCaseNo(caseNo);
		DcEducationEntity educationEntity=educationRepo.findByCaseNo(caseNo);
		List<DcChildrenEntity> childList=childRepo.findByCaseNo(caseNo);
		Optional<DcCaseEntity> opt=caseRepo.findById(caseNo);
		//get the planName
		String planName=null;
		Integer appId=null;
		if (opt.isPresent()) {
			DcCaseEntity caseEntity=opt.get();
			Integer planId=caseEntity.getPlanId();
			appId=caseEntity.getAppId();
			Optional<PlanEntity> optPlanEntity=planRepo.findById(planId);
			if (optPlanEntity.isPresent()) {
				planName=optPlanEntity.get().getPlanName();
			}
		}
		Optional<CitizenAppRegistrationEntity> optCitizenEntity=appRepo.findById(appId);  
		CitizenAppRegistrationEntity citienEntity=null;
		if (optCitizenEntity.isPresent()) {
			citienEntity=optCitizenEntity.get();
		}
		//convert Entity object to binding object
		IncomeInputs income=new IncomeInputs();
		BeanUtils.copyProperties(incomeEntity, income);
		EducationInputs education=new EducationInputs();
		BeanUtils.copyProperties(educationEntity, education);
		List<ChildInputs> childInputs=new ArrayList<>();
		childList.forEach(childEntity->{
			ChildInputs child=new ChildInputs();
			BeanUtils.copyProperties(childEntity, child);
			childInputs.add(child);
		});
		
		CitizenAppRegistractionInputs citizen=new CitizenAppRegistractionInputs();
		BeanUtils.copyProperties(citienEntity, citizen);
		
		DcSummaryReport report=new DcSummaryReport();
		report.setChildrenDetails(childInputs);
		report.setCitizenDetails(citizen);
		report.setEduDetails(education);
		report.setIncomeDetails(income);
		report.setPlanName(planName);
		return report;
	}

}
