package com.otz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otz.entity.DcCaseEntity;
import com.otz.entity.DcIncomeEntity;

public interface IDcCaseRepository extends JpaRepository<DcCaseEntity, Integer> {
	public Integer findByCaseNo(int caseNo);
}
