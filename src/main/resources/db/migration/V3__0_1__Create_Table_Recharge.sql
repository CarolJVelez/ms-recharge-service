CREATE TABLE recharge (
    id VARCHAR(36) PRIMARY KEY,
    phone_number VARCHAR(10) NOT NULL,
    amount BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_recharge_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);