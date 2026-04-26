CREATE TABLE IF NOT EXISTS card (
    number VARCHAR(16) PRIMARY KEY,
    name VARCHAR(100),
    cvv VARCHAR(3),
    valid_till VARCHAR(5),
    balance DECIMAL(19,2) NOT NULL,
    currency_code VARCHAR(3) NOT NULL
);

CREATE TABLE IF NOT EXISTS transfer_operation (
    operation_id UUID PRIMARY KEY,
    card_from_number VARCHAR(16),
    card_to_number VARCHAR(16),
    amount DECIMAL(19,2),
    commission DECIMAL(19,2),
    status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    confirmed_at TIMESTAMP
);
