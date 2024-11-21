package com.otz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otz.entity.DcIncomeEntity;

public interface IDcIncomeRepository extends JpaRepository<DcIncomeEntity, Integer>{
	public DcIncomeEntity findByCaseNo(int caseNo);
}
