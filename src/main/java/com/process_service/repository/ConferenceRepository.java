package com.process_service.repository;

import com.process_service.entity.Conferences;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ConferenceRepository extends CrudRepository<Conferences, UUID>, JpaSpecificationExecutor<Conferences> {
}
