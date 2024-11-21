package com.otz.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otz.entity.PlanCategory;

public interface IPlanCategoryRepository extends JpaRepository<PlanCategory,Integer> {

}
