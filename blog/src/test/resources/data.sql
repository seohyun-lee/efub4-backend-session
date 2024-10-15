-- src/test/resources/data.sql

/* data.sql */
CREATE TABLE IF NOT EXISTS account (
                                       account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       email VARCHAR(60) NOT NULL,
    encoded_password VARCHAR(255) NOT NULL,
    nickname VARCHAR(16) NOT NULL,
    bio VARCHAR(255),
    status VARCHAR(255) NOT NULL
    );

INSERT INTO account (email, encoded_password, nickname, bio, status) VALUES
    ('test@example.com', 'encodedPassword123', 'TestNickname','This is a bio.', 'REGISTERED');

