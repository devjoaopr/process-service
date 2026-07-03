package com.process_service.repository;

import com.process_service.entity.Districts;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DistrictRepository extends CrudRepository<Districts, UUID>, JpaSpecificationExecutor<Districts> {
}
