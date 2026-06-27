package com.process_service.repository;

import com.process_service.entity.Origin;
import com.process_service.entity.Phase;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PhaseRepository extends CrudRepository<Phase, UUID>, JpaSpecificationExecutor<Phase> {
}
