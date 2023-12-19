CREATE TABLE IF NOT EXISTS transfers
(
    id UUID PRIMARY KEY,
    value DECIMAL NOT NULL,
    currency VARCHAR(255) NOT NULL,
    recipient_customer_name VARCHAR(255) NOT NULL,
    recipient_account VARCHAR(255) NOT NULL,
    source_customer_name VARCHAR(255) NOT NULL,
    source_account VARCHAR(255) NOT NULL,
    created_on TIMESTAMP NOT NULL,
    updated_on TIMESTAMP NOT NULL
);