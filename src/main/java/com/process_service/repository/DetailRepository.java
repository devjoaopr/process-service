package com.process_service.repository;

import com.process_service.entity.Details;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DetailRepository extends CrudRepository<Details, UUID>, JpaSpecificationExecutor<Details> {
}
