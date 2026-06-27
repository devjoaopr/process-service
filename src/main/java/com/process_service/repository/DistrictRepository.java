package com.process_service.repository;

import com.process_service.entity.District;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DistrictRepository extends CrudRepository<District, UUID>, JpaSpecificationExecutor<District> {
}
