package com.github.jarvvski.wisestart.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SAVE_NEW_USER_QUERY = """
        INSERT INTO users (
            id,
            username
        ) VALUES (
            :id,
            :username
        )
        """.stripIndent();

    public void createUser(String username) {
        var params = new MapSqlParameterSource()
            .addValue("username", username)
            .addValue("id", UUID.randomUUID()); // don't actually do this
        namedParameterJdbcTemplate.update(
            SAVE_NEW_USER_QUERY,
            params
        );
    }

    public List<User> getAllUsers() {
        return namedParameterJdbcTemplate.query("""
            SELECT * FROM users
            """.stripIndent(),
            (row, i) -> new User(
                row.getObject("id", java.util.UUID.class),
                row.getString("username")
            ));
    }
}
