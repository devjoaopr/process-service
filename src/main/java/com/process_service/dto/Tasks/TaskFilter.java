package com.process_service.dto.Tasks;

import java.util.List;

public record TaskFilter(
        List<String> title,
        List<String> observation,
        List<String> entityNames
) {
}
