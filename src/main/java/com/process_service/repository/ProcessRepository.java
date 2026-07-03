package com.process_service.repository;

import com.process_service.entity.Processes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProcessRepository extends JpaRepository<Processes, UUID>,
        JpaSpecificationExecutor<Processes> {
}
