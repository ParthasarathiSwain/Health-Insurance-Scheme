package com.otz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otz.entity.CitizenAppRegistrationEntity;

public interface ICitizenRepository extends JpaRepository<CitizenAppRegistrationEntity, Integer> {
	 
}
