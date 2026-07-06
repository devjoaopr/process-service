package com.process_service.repository;

import com.process_service.entity.Locators;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LocatorRepository extends CrudRepository<Locators, UUID>, JpaSpecificationExecutor<Locators> {
}
