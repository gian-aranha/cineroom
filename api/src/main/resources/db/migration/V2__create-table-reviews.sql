CREATE TABLE reviews(
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL,
    movie VARCHAR(255) NOT NULL,
    rating INT NOT NULL,
    comment VARCHAR(255),

    PRIMARY KEY(id),
    CONSTRAINT fk_reviews_user_id FOREIGN KEY(user_id) REFERENCES users(id)
);