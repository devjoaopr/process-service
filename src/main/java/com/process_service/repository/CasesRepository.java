package com.process_service.repository;

import com.process_service.entity.Cases;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CasesRepository extends CrudRepository<Cases, UUID>, JpaSpecificationExecutor<Cases> {
}
