package com.process_service.repository;

import com.process_service.entity.SubjectOptions;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SubjectOptionRepository extends CrudRepository<SubjectOptions, UUID>, JpaSpecificationExecutor<SubjectOptions> {
}
