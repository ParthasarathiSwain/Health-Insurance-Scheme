package com.otz.ms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otz.bindings.ChildInputs;
import com.otz.bindings.DcSummaryReport;
import com.otz.bindings.EducationInputs;
import com.otz.bindings.IncomeInputs;
import com.otz.bindings.PlanSelectionInputs;
import com.otz.service.IDcMgntService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/dataColl-api")
@Tag(name="dc-api",description = "Data Collection Module")
public class DataCollectionOperationController {
	@Autowired
	private IDcMgntService service;
	
	@GetMapping("/planNames")
	public ResponseEntity<List<String>> displayPlanNames(){
		List<String> listPlanName=service.showAllPlanNames();
		return new ResponseEntity<List<String>>(listPlanName,HttpStatus.OK);
	}
	@PostMapping("/generatorCaseNo/{appId}")
	public ResponseEntity<Integer> generateCaseNo(@PathVariable Integer appId){
		Integer caseNo=service.generateCaseNo(appId);
		return new ResponseEntity<Integer>(caseNo,HttpStatus.OK);
	}
	@PutMapping("/updatePlanSelection")
	public ResponseEntity<Integer> savePlanSelectionDetails(@RequestBody PlanSelectionInputs inputs){
		Integer caseNo=service.savePlanSelection(inputs);
		return new ResponseEntity<Integer>(caseNo,HttpStatus.CREATED);
	}
	@PostMapping("/saveIncome")
	public ResponseEntity<Integer> saveIncomeDetails(@RequestBody IncomeInputs inputs){
		Integer caseNo=service.saveIncomeDetails(inputs);
		return new ResponseEntity<Integer>(caseNo,HttpStatus.CREATED);
	}
	@PostMapping("/saveEducation")
	public ResponseEntity<Integer> saveEducationDetails(@RequestBody EducationInputs inputs){
		Integer caseNo=service.saveEducationDetails(inputs);
		return new ResponseEntity<Integer>(caseNo,HttpStatus.CREATED);
	}
	@PostMapping("/saveChilds")
	public ResponseEntity<Integer> saveChilds(@RequestBody List<ChildInputs> inputs){
		Integer caseNo=service.saveChildrenDetails(inputs);
		return new ResponseEntity<Integer>(caseNo,HttpStatus.CREATED);
	}
	@GetMapping("/summary/{caseNo}")
	public ResponseEntity<DcSummaryReport> showSummaryReport(@PathVariable Integer caseNo){
		DcSummaryReport report=service.showDcSummary(caseNo);
		return new ResponseEntity<DcSummaryReport>(report,HttpStatus.OK);
	}
	
}
