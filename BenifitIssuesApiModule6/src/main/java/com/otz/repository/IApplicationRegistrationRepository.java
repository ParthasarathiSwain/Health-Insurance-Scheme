package com.otz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otz.entity.CitizenAppRegistrationEntity;

public interface IApplicationRegistrationRepository extends JpaRepository<CitizenAppRegistrationEntity, Integer> {

}
