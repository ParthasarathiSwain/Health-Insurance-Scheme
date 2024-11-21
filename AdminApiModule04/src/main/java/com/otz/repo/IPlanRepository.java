package com.otz.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otz.entity.PlanEntity;

public interface IPlanRepository extends JpaRepository<PlanEntity, Integer> {

}
