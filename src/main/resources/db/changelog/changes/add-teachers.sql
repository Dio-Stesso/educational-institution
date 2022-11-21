--liquibase formatted sql
--changeset <alexa>:<create-teachers-table>
INSERT INTO teachers (first_name, last_name, age, email, field) VALUES ('Liam1', 'Moriarty', 25, 'math1@math.com', 'Math');
INSERT INTO teachers (first_name, last_name, age, email, field) VALUES ('Liam2', 'Moriarty', 25, 'math2@math.com', 'Math');
INSERT INTO teachers (first_name, last_name, age, email, field) VALUES ('Liam3', 'Moriarty', 25, 'math3@math.com', 'Math');
INSERT INTO teachers (first_name, last_name, age, email, field) VALUES ('Liam4', 'Moriarty', 25, 'math4@math.com', 'Math');
INSERT INTO teachers (first_name, last_name, age, email, field) VALUES ('Liam5', 'Moriarty', 25, 'math5@math.com', 'Math');

--rollback DELETE FROM teachers;