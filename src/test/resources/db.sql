CREATE TABLE IF NOT EXISTS groups
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS audiences
(
    audienceId SERIAL PRIMARY KEY,
    audienceNumber INT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS students
(
    studentId SERIAL PRIMARY KEY,
    firstname VARCHAR,
    lastname VARCHAR,
    group_id INT,
    FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS teachers
(
    teacherId SERIAL PRIMARY KEY,
    firstname VARCHAR,
    lastname VARCHAR
);

CREATE TABLE IF NOT EXISTS lessons
(
    lessonId SERIAL PRIMARY KEY,
    name VARCHAR,
    teacher_id INT,
    group_id INT,
    audience_id int,
    lTime TIMESTAMP NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teachers (teacherId) ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE CASCADE,
    FOREIGN KEY (audience_id) REFERENCES audiences (audienceId) ON DELETE CASCADE
);

INSERT INTO groups(name) VALUES ('DaoTest1');
INSERT INTO groups(name) VALUES ('DaoTest2');
INSERT INTO groups(name) VALUES ('DaoTest3');
INSERT INTO groups(name) VALUES ('DaoTest4');

INSERT INTO audiences(audienceNumber) VALUES (33);
INSERT INTO audiences(audienceNumber) VALUES (31);
INSERT INTO audiences(audienceNumber) VALUES (32);
INSERT INTO audiences(audienceNumber) VALUES (34);

INSERT INTO students(firstname, lastname, group_id) VALUES ('Vlad', 'Valchuk', 1);
INSERT INTO students(firstname, lastname, group_id) VALUES ('Vlad2', 'Valchuk2', 2);
INSERT INTO students(firstname, lastname, group_id) VALUES ('Vlad3', 'Valchuk3', 3);
INSERT INTO students(firstname, lastname) VALUES ('Vlad4', 'Valchuk4');

INSERT INTO teachers(firstname, lastname) VALUES ('Vlad', 'Valchuk');
INSERT INTO teachers(firstname, lastname) VALUES ('Vlad2', 'Valchuk2');
INSERT INTO teachers(firstname, lastname) VALUES ('Vlad3', 'Valchuk3');
INSERT INTO teachers(firstname, lastname) VALUES ('Vlad4', 'Valchuk4');

INSERT INTO lessons(name, teacher_id, group_id, audience_id, lTime) VALUES ('testLesson', 1, 1, 1, '2021-08-09 13:57:40');
INSERT INTO lessons(name, teacher_id, group_id, audience_id, lTime) VALUES ('testLesson', 2, 2, 2, '2021-05-09 13:57:40');
INSERT INTO lessons(name, teacher_id, group_id, audience_id, lTime) VALUES ('testLesson', 3, 3, 3, '2021-06-09 13:57:40');
INSERT INTO lessons(name, teacher_id, group_id, audience_id, lTime) VALUES ('testLesson', 4, 4, 4, '2021-07-09 13:57:40');


