package com.process_service.repository;

import com.process_service.entity.ActionObject;
import com.process_service.entity.District;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ActionObjectRepository extends CrudRepository<ActionObject, UUID>, JpaSpecificationExecutor<ActionObject> {
}
