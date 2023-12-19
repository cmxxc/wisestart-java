package com.github.jarvvski.wisestart.demo.infrastructure;

import lombok.NonNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

interface SqlMapper<T> extends RowMapper<T> {

    T toEntity(ResultSet rs) throws SQLException;

    SqlParameterSource toSql(T entity);

    default SqlParameterSource[] toSql(List<T> entities) {
        return entities.stream().map(this::toSql).toArray(SqlParameterSource[]::new);
    }

    default T mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return toEntity(rs);
    }
}
