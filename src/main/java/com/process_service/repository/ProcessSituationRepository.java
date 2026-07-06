package com.process_service.repository;

import com.process_service.entity.ProcessSituations;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProcessSituationRepository extends CrudRepository<ProcessSituations, UUID>, JpaSpecificationExecutor<ProcessSituations> {
}
