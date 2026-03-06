CREATE DATABASE IF NOT EXISTS taskhub;
USE taskhub;

CREATE TABLE IF NOT EXISTS tasks (
                                     id INT NOT NULL AUTO_INCREMENT,
                                     title VARCHAR(120) NOT NULL,
    status ENUM('TODO', 'DOING', 'DONE') NOT NULL DEFAULT 'TODO',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
    );

CREATE INDEX idx_tasks_status ON tasks(status);

INSERT INTO tasks (title, status) VALUES
                                      ('Finish lab exercise', 'TODO'),
                                      ('Prepare JDBC demo', 'DOING'),
                                      ('Submit assignment', 'DONE');