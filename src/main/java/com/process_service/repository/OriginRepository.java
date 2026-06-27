package com.process_service.repository;

import com.process_service.entity.Locator;
import com.process_service.entity.Origin;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OriginRepository extends CrudRepository<Origin, UUID>, JpaSpecificationExecutor<Origin> {
}
