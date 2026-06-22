package com.process_service.repository;

import com.process_service.entity.Process;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProcessRepository extends CrudRepository<Process, UUID> {
}
