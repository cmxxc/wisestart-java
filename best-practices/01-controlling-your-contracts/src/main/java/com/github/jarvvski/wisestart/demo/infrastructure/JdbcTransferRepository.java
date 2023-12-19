package com.github.jarvvski.wisestart.demo.infrastructure;

import com.github.jarvvski.wisestart.demo.domain.Currency;
import com.github.jarvvski.wisestart.demo.domain.Transfer;
import com.github.jarvvski.wisestart.demo.domain.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class JdbcTransferRepository implements TransferRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SqlMapper<Transfer> sqlMapper = new TransferSqlMapper();

    private final String SAVE_QUERY = """
        INSERT INTO transfers (
            id,
            value,
            currency,
            recipient_customer_name,
            recipient_account,
            source_customer_name,
            source_account,
            created_on,
            updated_on
        ) VALUES (
            :id,
            :value,
            :currency,
            :recipient_customer_name,
            :recipient_account,
            :source_customer_name,
            :source_account,
            :created_on,
            :updated_on
        );
        """.stripIndent();

    private final String FIND_BY_ID = """
        SELECT *
        FROM transfers
        WHERE id = :id
        """.stripIndent();
    @Override
    public void save(Transfer transfer) {
        final var params = sqlMapper.toSql(transfer);
        namedParameterJdbcTemplate.update(SAVE_QUERY, params);
    }

    @Override
    public Optional<Transfer> fetchById(String id) {
        final var params = new JdbcMapParameterSource()
            .addValue("id", id);

        final var results = namedParameterJdbcTemplate.query(FIND_BY_ID, params, sqlMapper);

        if (results.size() > 1) {
            throw RepositoryQueryException.multipleEntitiesFound(Transfer.class, id);
        }

        if (results.isEmpty()) {
            throw RepositoryQueryException.noneFound(Transfer.class, id);
        }

        return results.stream().findFirst();
    }

    private static class TransferSqlMapper implements SqlMapper<Transfer> {

        @Override
        public Transfer toEntity(ResultSet rs) throws SQLException {
            return Transfer.builder()
                .id(rs.getString("id"))
                .moneyAmount(rs.getBigDecimal("value"))
                .currency(Currency.valueOf(rs.getString("currency")))
                .recipientName(rs.getString("recipient_customer_name"))
                .recipientAccount(rs.getString("recipient_account"))
                .sourceAccount(rs.getString("source_account"))
                .sourceName(rs.getString("source_customer_name"))
                .creationDate(rs.getTimestamp("created_on").toInstant())
                .lastUpdatedDate(rs.getTimestamp("updated_on").toInstant())
                .build();
        }

        @Override
        public SqlParameterSource toSql(Transfer transfer) {
        return new JdbcMapParameterSource()
            .addValue("id", transfer.getId())
            .addValue("value", transfer.getMoneyAmount())
            .addValue("currency", transfer.getCurrency())
            .addValue("recipient_customer_name", transfer.getRecipientName())
            .addValue("recipient_account", transfer.getRecipientAccount())
            .addValue("source_customer_name", transfer.getSourceName())
            .addValue("source_account", transfer.getSourceAccount())
            .addValue("created_on", transfer.getCreationDate())
            .addValue("updated_on", transfer.getLastUpdatedDate());
        }
    }
}
