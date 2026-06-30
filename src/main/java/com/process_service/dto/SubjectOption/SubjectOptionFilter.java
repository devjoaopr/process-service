package com.process_service.dto.SubjectOption;

import java.util.List;

public record SubjectOptionFilter(
        List<String> description,
        List<String> name,
        Boolean active,
        List<String> slug
) {
}
