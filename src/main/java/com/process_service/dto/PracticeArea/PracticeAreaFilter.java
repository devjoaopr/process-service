package com.process_service.dto.PracticeArea;

import java.util.List;

public record PracticeAreaFilter(
        List<String> description,
        List<String> name,
        Boolean active,
        List<String> slug
) {
}
