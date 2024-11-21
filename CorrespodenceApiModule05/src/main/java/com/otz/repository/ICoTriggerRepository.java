package com.otz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otz.entity.CoTriggersEntity;

public interface ICoTriggerRepository extends JpaRepository<CoTriggersEntity, Integer> {
	public List<CoTriggersEntity> findByTriggerStatus(String triggerStatus);
	public CoTriggersEntity  findByCaseNo(int caseNo);
}
