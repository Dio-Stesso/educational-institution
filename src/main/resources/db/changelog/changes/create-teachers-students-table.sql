--liquibase formatted sql
--changeset <alexa>:<create-teachers_students-table>
CREATE TABLE IF NOT EXISTS teachers_students
(
    teachers_id bigint not null,
    students_id bigint not null,
    constraint teachers_fk
        foreign key (teachers_id) references teachers (id),
    constraint students_fk
        foreign key (students_id) references students (id)
);

--rollback DROP TABLE teachers_students;