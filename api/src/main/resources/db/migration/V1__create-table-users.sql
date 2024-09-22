CREATE TABLE users(
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    sessions INT NOT NULL,
    reviews INT NOT NULL,

    PRIMARY KEY(id)
);