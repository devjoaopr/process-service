package com.process_service.repository;

import com.process_service.entity.ProcessType;
import com.process_service.entity.Prognosis;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PrognosisRepository extends CrudRepository<Prognosis, UUID>, JpaSpecificationExecutor<Prognosis> {
}
