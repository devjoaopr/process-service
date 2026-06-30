package com.process_service.shared;

import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;
import java.util.List;

public class SpecificationUtils {

    public static <T> Specification<T> equal(String field, Object value) {
        if (value == null) return Specification.unrestricted();

        return (root, query, cb) ->
                cb.equal(root.get(field), value);
    }

    public static <T> Specification<T> like(String field, String value) {
        if (value == null || value.isBlank())
            return Specification.unrestricted();

        return (root, query, cb) ->
                cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
    }

    public static <T, V> Specification<T> in(String field, List<V> values) {
        if (values == null || values.isEmpty())
            return Specification.unrestricted();

        return (root, query, cb) ->
                root.get(field).in(values);
    }

    public static <T> Specification<T> date(String field, OffsetDateTime value) {
        if (value == null)
            return Specification.unrestricted();

        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get(field), value);
    }
}