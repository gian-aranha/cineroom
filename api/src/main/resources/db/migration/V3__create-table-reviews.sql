CREATE TABLE reviews(
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME NOT NULL,
    content VARCHAR(255) NOT NULL,
    rating INT NOT NULL,
    comment VARCHAR(255),
    user_id BIGINT NOT NULL,
    session_id BIGINT NOT NULL,

    PRIMARY KEY(id),
    CONSTRAINT fk_reviews_user_id FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_reviews_session_id FOREIGN KEY(session_id) REFERENCES sessions(id)
);