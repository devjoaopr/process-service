package com.process_service.repository;

import com.process_service.entity.Group;
import com.process_service.entity.Locator;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LocatorRepository extends CrudRepository<Locator, UUID>, JpaSpecificationExecutor<Locator> {
}
