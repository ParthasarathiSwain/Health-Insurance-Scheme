package com.otz.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otz.bindings.ElgibilityDetailsOutput;
import com.otz.entity.CitizenAppRegistrationEntity;
import com.otz.entity.CoTriggersEntity;
import com.otz.entity.DcCaseEntity;
import com.otz.entity.DcChildrenEntity;
import com.otz.entity.DcEducationEntity;
import com.otz.entity.DcIncomeEntity;
import com.otz.entity.ElgibilityDetailsEntity;
import com.otz.entity.PlanEntity;
import com.otz.repository.IApplicationRegistrationRepository;
import com.otz.repository.ICoTriggerRepository;
import com.otz.repository.IDcCaseRepository;
import com.otz.repository.IDcChildrenRepository;
import com.otz.repository.IDcEducationRepository;
import com.otz.repository.IDcIncomeRepository;
import com.otz.repository.IElgibilityDetermineRepository;
import com.otz.repository.IPlanRepository;
@Service
public class ElgibilityDeterminationServiceIMPL implements IElgibilityDeterminationService {
	@Autowired
	private IElgibilityDetermineRepository  edRepo;
	@Autowired
	private IDcCaseRepository caseRepo;
	@Autowired
	private IPlanRepository planRepo;
	@Autowired
	private IDcIncomeRepository incomeRepo;
	@Autowired
	private IDcChildrenRepository childrenRepo;
	@Autowired
	private IApplicationRegistrationRepository  citizenRepo;
	@Autowired
	private IDcEducationRepository educationRepo;
	@Autowired
	private ICoTriggerRepository triggerRepo;
	
	@Override
	public ElgibilityDetailsOutput determineElgibility(int caseNo) {
		//get planId and appId based on the caseno
		Integer appId=null;
		Integer planId=null;
		String planName=null;
		String citizenName=null;
		Long citizenSSN=0l;
		int citizenAge=0;
		Optional<DcCaseEntity> optCaseEntity=caseRepo.findById(caseNo);
		if (optCaseEntity.isPresent()) {
			DcCaseEntity caseEntity=optCaseEntity.get();
			planId=caseEntity.getPlanId();
			appId=caseEntity.getAppId();
		}
		
		//get the plan Name
		Optional<PlanEntity> optPlanEntity=planRepo.findById(planId);
		if (optPlanEntity.isPresent()) {
			PlanEntity planEntity=optPlanEntity.get();
			planName=planEntity.getPlanName();
		}
		
		//calculate citizen age by geting citizen dob through appid
		Optional<CitizenAppRegistrationEntity> optCitizenEntity=citizenRepo.findById(appId);
		if (optCitizenEntity.isPresent()) {
			CitizenAppRegistrationEntity citizenEntity=optCitizenEntity.get();
			citizenName=citizenEntity.getFullName();
			citizenSSN=citizenEntity.getSsn();
			LocalDate citizenDOB=citizenEntity.getDob();
			LocalDate sysDate=LocalDate.now();
			citizenAge=Period.between(citizenDOB, sysDate).getYears();
		}
		//call helper method
		ElgibilityDetailsOutput elgiOutput=applyCondition(caseNo,planName,citizenAge);
		//set fullname to the binding object
		elgiOutput.setHolderName(citizenName);
		//converting elgibility binding object to entity object
		ElgibilityDetailsEntity elgiEntity=new ElgibilityDetailsEntity();
		BeanUtils.copyProperties(elgiOutput, elgiEntity);
		//save the entity
		elgiEntity.setHolderSSN(citizenSSN);
		elgiEntity.setCaseNo(caseNo);
		edRepo.save(elgiEntity);
		//save Corresponding Triggers
		CoTriggersEntity triggersEntity=new CoTriggersEntity();
		triggersEntity.setCaseNo(caseNo);
		triggersEntity.setTriggerStatus("Pending");
		triggerRepo.save(triggersEntity);
		return elgiOutput;
	}
	
	//helper method
	private ElgibilityDetailsOutput applyCondition(int caseNo, String planName,int citizenAge) {
		ElgibilityDetailsOutput elgiOutput=new ElgibilityDetailsOutput();
		elgiOutput.setPlanName(planName);
		//get the income details
		DcIncomeEntity incomeEntity=incomeRepo.findByCaseNo(caseNo);
		double empIncome=incomeEntity.getEmpIncome();
		double propertyIncome=incomeEntity.getPropertyIncome();
		if (planName.equalsIgnoreCase("SNAP")) {
			if (empIncome<=300) {
				elgiOutput.setPlanStatus("Approved");
				elgiOutput.setBenifitAmount(200.0);
			}else {
				elgiOutput.setPlanStatus("Denied");
				elgiOutput.setDenialReason("High Income");
			}
		}
		else if(planName.equalsIgnoreCase("CCAP")){
			boolean kidsCountCondition=false;
			boolean kidsAgeCondition=true;
			List<DcChildrenEntity> listChild=childrenRepo.findByCaseNo(caseNo);
			if (!listChild.isEmpty()) {
				kidsCountCondition=true;
				for(DcChildrenEntity child:listChild){
					int kidAge=Period.between(child.getChildDOB(), LocalDate.now()).getYears();
					if (kidAge>16) {
						kidsAgeCondition=false;
						break;
					}//end inner if2
				}//end for
			}//end inner if1
			if (empIncome<=300 && kidsAgeCondition&&kidsCountCondition) {
				elgiOutput.setPlanStatus("Approved");
				elgiOutput.setBenifitAmount(300.0);
			}else {
				elgiOutput.setPlanStatus("Denied");
				elgiOutput.setDenialReason("CCAP Rules are not satisfied");
			}
		}
		else if(planName.equalsIgnoreCase("MEDAID")){
			if (empIncome<=300&& propertyIncome==0) {
				elgiOutput.setPlanStatus("Approved");
				elgiOutput.setBenifitAmount(200.0);
			}else {
				elgiOutput.setPlanStatus("Denied");
				elgiOutput.setDenialReason("MEDAID Rules are not satisfied");
			}
		}
		else if(planName.equalsIgnoreCase("MEDICARE")){
			if (citizenAge>=65) {
				elgiOutput.setPlanStatus("Approved");
				elgiOutput.setBenifitAmount(350.0);
			}else {
				elgiOutput.setPlanStatus("Denied");
				elgiOutput.setDenialReason("MEDICARE Rules are not satisfied");
			}
		}
		else if(planName.equalsIgnoreCase("CAJW")){
			DcEducationEntity eduEntity=educationRepo.findByCaseNo(caseNo);
			int passOutyear=eduEntity.getPassOutYear();
			if (empIncome==0&& passOutyear<LocalDate.now().getYear()) {
				elgiOutput.setPlanStatus("Approved");
				elgiOutput.setBenifitAmount(300.0);
			}else {
				elgiOutput.setPlanStatus("Denied");
				elgiOutput.setDenialReason("CAJW Rules are not satisfied");
			}
		}
		else if(planName.equalsIgnoreCase("QHP")){//quality health plan
			if (citizenAge>1) {
				elgiOutput.setPlanStatus("Approved");
			}else {
				elgiOutput.setPlanStatus("Denied");
				elgiOutput.setDenialReason("QHP Rules are not satisfied");
			}
		}
		//set the commen property for elgiOutput only if the plan is approved
		if (elgiOutput.getPlanStatus().equalsIgnoreCase("Approved")) {
			elgiOutput.setPlanStartDate(LocalDate.now());
			elgiOutput.setPlanEndDate(LocalDate.now().plusYears(2));
			elgiOutput.setPlanName(planName);
		} 
		return elgiOutput;
	}

}
