package com.process_service.repository;

import com.process_service.entity.Phase;
import com.process_service.entity.PracticeArea;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PracticeAreaRepository extends CrudRepository<PracticeArea, UUID>, JpaSpecificationExecutor<PracticeArea> {
}
