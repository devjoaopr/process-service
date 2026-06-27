package com.process_service.repository;

import com.process_service.entity.ProcessType;
import com.process_service.entity.SubjectOption;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SubjectOptionRepository extends CrudRepository<SubjectOption, UUID>, JpaSpecificationExecutor<SubjectOption> {
}
