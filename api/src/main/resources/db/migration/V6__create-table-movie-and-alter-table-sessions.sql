CREATE TABLE movies(
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    image VARCHAR(255) NOT NULL,
    overview TEXT NOT NULL,
    rating DOUBLE NOT NULL,

    PRIMARY KEY(id)
);

ALTER TABLE sessions ADD COLUMN movie_id BIGINT NULL;
ALTER TABLE sessions ADD CONSTRAINT fk_sessions_movie_id FOREIGN KEY (movie_id) REFERENCES movies(id);