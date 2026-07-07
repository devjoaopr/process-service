package com.process_service.repository;

import com.process_service.entity.Tasks;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TasksRepository extends CrudRepository<Tasks, UUID>, JpaSpecificationExecutor<Tasks> {
}
