package com.process_service.repository;

import com.process_service.entity.Detail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DetailRepository extends CrudRepository<Detail, UUID>, JpaSpecificationExecutor<Detail> {
}
