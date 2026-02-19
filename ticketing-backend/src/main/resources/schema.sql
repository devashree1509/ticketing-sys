CREATE TABLE users
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(50)        NOT NULL,
    email      VARCHAR(50) UNIQUE NOT NULL,
    password   VARCHAR(25)        NOT NULL,
    role       VARCHAR(50)        NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tickets
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(50) NOT NULL,
    description TEXT,
    status      VARCHAR(50) NOT NULL,
    priority    VARCHAR(50),
    created_by  BIGINT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users (id)
);

CREATE TABLE ticket_comments(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ticket_id BIGINT,
    comment TEXT NOT NULL,
    commented_by BIGINT,
    commented_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ticket_id) REFERENCES tickets(id),
    FOREIGN KEY (commented_by) REFERENCES users(id)
);