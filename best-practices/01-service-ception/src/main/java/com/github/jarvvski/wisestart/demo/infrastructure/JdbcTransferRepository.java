package com.github.jarvvski.wisestart.demo.infrastructure;

import com.github.jarvvski.wisestart.demo.domain.Currency;
import com.github.jarvvski.wisestart.demo.domain.Money;
import com.github.jarvvski.wisestart.demo.domain.Recipient;
import com.github.jarvvski.wisestart.demo.domain.Source;
import com.github.jarvvski.wisestart.demo.domain.Transfer;
import com.github.jarvvski.wisestart.demo.domain.TransferId;
import com.github.jarvvski.wisestart.demo.domain.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

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
    public Optional<Transfer> fetchById(TransferId transferId) {
        final var params = new JdbcMapParameterSource()
            .addValue("id", transferId);

        final var results = namedParameterJdbcTemplate.query(FIND_BY_ID, params, sqlMapper);

        if (results.size() > 1) {
            throw RepositoryQueryException.multipleEntitiesFound(Transfer.class, transferId);
        }

        if (results.isEmpty()) {
            throw RepositoryQueryException.noneFound(Transfer.class, transferId);
        }

        return results.stream().findFirst();
    }

    private static class TransferSqlMapper implements SqlMapper<Transfer> {

        @Override
        public Transfer toEntity(ResultSet rs) throws SQLException {
            return Transfer.builder()
                .id(TransferId.of(rs.getString("id")))
                .money(
                    new Money(rs.getBigDecimal("value"),
                        Currency.valueOf(rs.getString("currency"))
                    )
                ).recipient(
                    new Recipient(rs.getString("recipient_customer_name"),
                        rs.getString("recipient_account"))
                ).source(
                    new Source(rs.getString("source_customer_name"),
                        rs.getString("source_account"))
                ).creationDate(rs.getTimestamp("created_on").toInstant())
                .lastUpdatedDate(rs.getTimestamp("updated_on").toInstant())
                .build();
        }

        @Override
        public SqlParameterSource toSql(Transfer transfer) {
        return new JdbcMapParameterSource()
            .addValue("id", transfer.getId())
            .addValue("value", transfer.getMoney().getValue())
            .addValue("currency", transfer.getMoney().getCurrency())
            .addValue("recipient_customer_name", transfer.getRecipient().getName())
            .addValue("recipient_account", transfer.getRecipient().getAccountNumber())
            .addValue("source_customer_name", transfer.getSource().getName())
            .addValue("source_account", transfer.getSource().getAccountNumber())
            .addValue("created_on", transfer.getCreationDate())
            .addValue("updated_on", transfer.getLastUpdatedDate());
        }
    }
}
