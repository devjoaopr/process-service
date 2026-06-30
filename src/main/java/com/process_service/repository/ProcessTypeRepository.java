package com.process_service.repository;

import com.process_service.entity.ActionObject;
import com.process_service.entity.ProcessType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProcessTypeRepository extends CrudRepository<ProcessType, UUID>, JpaSpecificationExecutor<ProcessType> {
}
