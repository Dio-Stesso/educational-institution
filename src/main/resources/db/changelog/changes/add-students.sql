--liquibase formatted sql
--changeset <alexa>:<create-teachers-table>
INSERT INTO students (first_name, last_name, age, email, speciality) VALUES ('Alex1', 'Alexandrov', 19, 'student1@math.edu', 'Mathematician');
INSERT INTO students (first_name, last_name, age, email, speciality) VALUES ('Alex2', 'Alexandrov', 19, 'student2@math.edu', 'Mathematician');
INSERT INTO students (first_name, last_name, age, email, speciality) VALUES ('Alex3', 'Alexandrov', 19, 'student3@math.edu', 'Mathematician');
INSERT INTO students (first_name, last_name, age, email, speciality) VALUES ('Alex4', 'Alexandrov', 19, 'student4@math.edu', 'Mathematician');
INSERT INTO students (first_name, last_name, age, email, speciality) VALUES ('Alex5', 'Alexandrov', 19, 'student5@math.edu', 'Mathematician');

--rollback DELETE FROM teachers;