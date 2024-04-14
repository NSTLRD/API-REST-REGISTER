CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       name VARCHAR(255),
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       created TIMESTAMP,
                       modified TIMESTAMP,
                       last_login TIMESTAMP,
                       token VARCHAR(255),
                       is_active BOOLEAN
);

CREATE TABLE phones (
                        id UUID PRIMARY KEY,
                        number VARCHAR(255) NOT NULL,
                        citycode VARCHAR(10),
                        countrycode VARCHAR(10),
                        user_id UUID,
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE tokens (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        token VARCHAR(255) UNIQUE NOT NULL,
                        created_at TIMESTAMP,
                        expired_at TIMESTAMP,
                        validated_at TIMESTAMP,
                        user_id UUID,
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Indexes for faster lookup
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_phone_user ON phones(user_id);
CREATE INDEX idx_token_user ON tokens(user_id);
