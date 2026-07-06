package com.process_service.repository;

import com.process_service.entity.Phases;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface PhaseRepository extends CrudRepository<Phases, UUID>, JpaSpecificationExecutor<Phases> {
}
