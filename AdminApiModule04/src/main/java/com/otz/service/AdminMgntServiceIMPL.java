package com.otz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otz.bindings.PlanData;
import com.otz.config.AppConfigProperties;
import com.otz.constants.PlanConstant;
import com.otz.entity.PlanCategory;
import com.otz.entity.PlanEntity;
import com.otz.repo.IPlanCategoryRepository;
import com.otz.repo.IPlanRepository;
@Service
public class AdminMgntServiceIMPL  implements AdminMgntService{
	@Autowired
	private IPlanRepository planRepo;
	@Autowired
	private IPlanCategoryRepository planCategoryRepo;

	private Map<String,String> messages;
	
	@Autowired
	public AdminMgntServiceIMPL(AppConfigProperties props) {
	messages=props.getMessages();
	}
	
	@Override
	public String registerPlan (PlanData plan) {
		//convert
		PlanEntity planEntity=new PlanEntity();
		BeanUtils.copyProperties(plan, planEntity);
		//save the object
		PlanEntity saved=planRepo.save(planEntity);
		return saved.getPlanId()!=null?messages.get(PlanConstant.SAVE_SUCCESS)+saved.getPlanId():messages.get(PlanConstant.SAVE_FAILURE);
	}

	@Override
	public Map<Integer, String> getPlanCategories() {
		//get All Travel Plan Categories
		List<PlanCategory> list= planCategoryRepo.findAll();
		Map<Integer, String> categoriesMap= new HashMap<Integer, String>();
		list.forEach(category->{
			categoriesMap.put(category.getCategoryId(), category.getCategoryName());
		});
		return categoriesMap;
	}
	

	@Override
	public String updatePlan(PlanData plan) {
		Optional<PlanEntity>  opt=planRepo.findById(plan.getPlanId());
		if (opt.isPresent()) {
			//updated the project
			PlanEntity planEntity=opt.get();
			BeanUtils.copyProperties(plan,  planEntity);
			planRepo.save(planEntity);
			return plan.getPlanId()+messages.get(PlanConstant.UPDATE_SUCCESS);
		} else {
			return plan.getPlanId()+messages.get(PlanConstant.UPDATE_FAILURE);
		}
	}

	@Override
	public String deletePlan(Integer planld) {
		Optional<PlanEntity>  opt=planRepo.findById(planld);
		if (opt.isPresent()) {
			planRepo.deleteById(planld);
			return planld+messages.get(PlanConstant.DELETE_SUCCESS);
		} else {
			return planld+messages.get(PlanConstant.DELETE_FAILURE);
		}
	}

	@Override
	public String changePlanStatus(Integer planld, String status) {
		Optional<PlanEntity>  opt=planRepo.findById(planld);
		if (opt.isPresent()) {
			PlanEntity plan=opt.get();
			plan.setActiveSw(status);
			planRepo.save(plan);
			return planld+messages.get(PlanConstant.STATUS_CHANGE_SUCCESS);
		} else {
			return planld+messages.get(PlanConstant.STATUS_CHANGE_FAILURE);
		}
	}

	@Override
	public List<PlanData> showAllPlans() {
		List<PlanEntity> listPlan=planRepo.findAll();
		List<PlanData> listPlanData=new ArrayList<>();
		listPlan.forEach(plan->{
			PlanData pd=new PlanData();
			BeanUtils.copyProperties(plan, pd);
			listPlanData.add(pd);
		});
		return listPlanData;
	}

	@Override
	public PlanData showPlanById(Integer planId) {
		PlanEntity planEntity= planRepo.findById(planId).orElseThrow(()->new IllegalArgumentException(messages.get(PlanConstant.FIND_BY_ID_FAILURE)));
		PlanData planData=new PlanData();
		BeanUtils.copyProperties(planEntity, planData);
		return planData;
	}
}

