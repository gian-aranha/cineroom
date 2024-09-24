CREATE TABLE credentials(
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,

    PRIMARY KEY(id),
    CONSTRAINT fk_credentials_user_id FOREIGN KEY(user_id) REFERENCES users(id)
)