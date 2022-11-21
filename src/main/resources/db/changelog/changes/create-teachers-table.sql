--liquibase formatted sql
--changeset <alexa>:<create-teachers-table>
CREATE TABLE IF NOT EXISTS teachers
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    age        INTEGER,
    email      VARCHAR(255),
    field      VARCHAR(255)
);

--rollback DROP TABLE teachers;