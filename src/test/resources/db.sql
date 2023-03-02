CREATE TABLE IF NOT EXISTS groups
(
    id SERIAL PRIMARY KEY,
    name VARCHAR
);

INSERT INTO groups(name) VALUES ('DaoTest1');
INSERT INTO groups(name) VALUES ('DaoTest2');
INSERT INTO groups(name) VALUES ('DaoTest3');
INSERT INTO groups(name) VALUES ('DaoTest4');
