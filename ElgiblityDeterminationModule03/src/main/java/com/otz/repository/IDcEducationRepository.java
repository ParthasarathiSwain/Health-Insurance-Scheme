package com.otz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otz.entity.DcEducationEntity;

public interface IDcEducationRepository extends JpaRepository<DcEducationEntity, Integer> {
	public DcEducationEntity findByCaseNo(int caseNo);
}
