CREATE TABLE sessions(
    id BIGINT NOT NULL AUTO_INCREMENT,
    code VARCHAR(255) NOT NULL,
    category VARCHAR(255),
    users_limit INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    content VARCHAR(255),
    created_at DATETIME NOT NULL,
    user_id BIGINT NOT NULL,
    movie_id BIGINT NULL,
    movie_title VARCHAR(255) NULL,
    movie_img VARCHAR(255) NULL,
    movie_overview TEXT NULL,
    movie_vote_average DOUBLE NULL,

    PRIMARY KEY(id),
    CONSTRAINT fk_sessions_user_id FOREIGN KEY(user_id) REFERENCES users(id)
);