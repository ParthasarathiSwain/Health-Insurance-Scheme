package com.otz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otz.entity.ElgibilityDetailsEntity;
import java.util.List;


public interface IElgibilityDetermineRepository extends JpaRepository<ElgibilityDetailsEntity, Integer> {
	public ElgibilityDetailsEntity findByCaseNo(Integer caseNo);
}
