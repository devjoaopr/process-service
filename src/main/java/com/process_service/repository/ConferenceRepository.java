package com.process_service.repository;

import com.process_service.entity.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ConferenceRepository extends CrudRepository<Conference, UUID>, JpaSpecificationExecutor<Conference> {
}
