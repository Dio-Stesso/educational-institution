--liquibase formatted sql
--changeset <alexa>:<create-students-table>
CREATE TABLE IF NOT EXISTS students
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    age        INTEGER,
    email      VARCHAR(255),
    speciality VARCHAR(255)
);

--rollback DROP TABLE students;