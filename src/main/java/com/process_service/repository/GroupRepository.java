package com.process_service.repository;

import com.process_service.entity.Groups;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GroupRepository extends CrudRepository<Groups, UUID>, JpaSpecificationExecutor<Groups> {
}
