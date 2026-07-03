package com.process_service.repository;

import com.process_service.entity.Origins;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OriginRepository extends CrudRepository<Origins, UUID>, JpaSpecificationExecutor<Origins> {
}
