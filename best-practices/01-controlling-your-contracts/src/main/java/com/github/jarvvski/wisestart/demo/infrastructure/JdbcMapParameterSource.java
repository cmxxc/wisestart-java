package com.github.jarvvski.wisestart.demo.infrastructure;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@NoArgsConstructor
public class JdbcMapParameterSource extends MapSqlParameterSource {

    @Override
    public Object getValue(@NonNull String paramName) {
        var value = super.getValue(paramName);
        return JdbcMapParameterSource.extract(value);
    }

    @Override
    @NonNull
    public Map<String, Object> getValues() {
        Map<String, Object> values =
            super.getValues().entrySet().stream().collect(toMap(Map.Entry::getKey, JdbcMapParameterSource::extract));
        return Collections.unmodifiableMap(values);
    }

    public static Object extract(Object value) {
        if (value instanceof Instant) {
            return Timestamp.from((Instant) value);
        }

        if (value instanceof Enum<?>) {
            return ((Enum<?>) value).name();
        }

        if (value instanceof Collection) {
            return ((Collection<?>) value).stream()
                .map(JdbcMapParameterSource::extract)
                .toList();
        }

        if (value instanceof Optional) {
            return ((Optional<?>) value).map(JdbcMapParameterSource::extract).orElse(null);
        }

        return value;
    }
}
