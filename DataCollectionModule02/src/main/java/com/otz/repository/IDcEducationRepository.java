package com.otz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otz.entity.DcEducationEntity;
import com.otz.entity.DcIncomeEntity;

public interface IDcEducationRepository extends JpaRepository<DcEducationEntity, Integer> {
	public DcEducationEntity findByCaseNo(int caseNo);
}
