package com.otz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otz.entity.DcCaseEntity;

public interface IDcCaseRepository extends JpaRepository<DcCaseEntity, Integer> {
}
