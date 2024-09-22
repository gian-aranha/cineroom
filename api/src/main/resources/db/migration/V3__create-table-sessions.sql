CREATE TABLE sessions(
    id BIGINT NOT NULL AUTO_INCREMENT,
    code VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    users_limit INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    movie VARCHAR(255) NOT NULL,
    review_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL,
    user_id BIGINT NOT NULL,

    PRIMARY KEY(id),
    CONSTRAINT fk_sessions_review_id FOREIGN KEY(review_id) REFERENCES reviews(id),
    CONSTRAINT fk_sessions_user_id FOREIGN KEY(user_id) REFERENCES users(id)
);