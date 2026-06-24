package com.process_service.repository;

import com.process_service.entity.ProcessSituation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProcessSituationRepository extends CrudRepository<ProcessSituation, UUID>, JpaSpecificationExecutor<ProcessSituation> {
}
