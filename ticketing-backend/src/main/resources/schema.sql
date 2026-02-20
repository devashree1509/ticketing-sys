CREATE TABLE IF NOT EXISTS users(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM ('ADMIN','AGENT','CUSTOMER')  NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tickets(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(50),
    priority ENUM('LOW','MEDIUM','HIGH'),
    status ENUM('OPEN','IN_PROGRESS','RESOLVED','CLOSED') NOT NULL DEFAULT 'OPEN',
    created_by BIGINT NOT NULL,
    assigned_to BIGINT  NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (assigned_to) REFERENCES users(id)
);

CREATE INDEX idx_ticket_status ON tickets(status);
CREATE INDEX idx_ticket_assigned_to ON tickets(assigned_to);
CREATE INDEX idx_ticket_created_by ON tickets(created_by);
CREATE INDEX idx_ticket_created_at ON tickets(created_at);

CREATE TABLE  IF NOT EXISTS ticket_comments(
       id BIGINT PRIMARY KEY AUTO_INCREMENT,
       ticket_id BIGINT NOT NULL,
       author_id BIGINT NOT NULL,
       message TEXT NOT NULL,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

       FOREIGN KEY (ticket_id) REFERENCES tickets(id),
       FOREIGN KEY (author_id) REFERENCES users(id)
);