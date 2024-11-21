package com.otz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otz.entity.ElgibilityDetailsEntity;

public interface IElgibilityDetermineRepository extends JpaRepository<ElgibilityDetailsEntity, Integer> {

}
