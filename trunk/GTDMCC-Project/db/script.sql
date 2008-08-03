--drop table USERS
create table USERS (
    id INT NOT NULL,
    fname TEXT NOT NULL,
    lname TEXT NOT NULL,
    login CHAR(20) NOT NULL,
    pass_hash TEXT NOT NULL,
    is_blocked SMALLINT NOT NULL,
    PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

insert into USERS (id, fname, lname, login, pass_hash, is_blocked) values (174947681, 'root', 'root', 'root', 'ho-ho', 0);


CREATE UNIQUE INDEX idx_users_login
    ON USERS (login)


create table CONTEXTS (
     id INT NOT NULL AUTO_INCREMENT,
     name TEXT NOT NULL,
     sort_order INT not null,
     user_id INT not null,
     PRIMARY KEY (id),
     FOREIGN KEY (user_id) REFERENCES USERS(id)  ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

alter table CONTEXTS add column
(
    is_archived SMALLINT not NULL
);
--drop table LISTS

create table LISTS (
     id INT NOT NULL AUTO_INCREMENT,
     name TEXT NOT NULL,
     creation DATETIME not null,  
     context_id INT NOT NULL,
     sort_order INT not null,
     is_archived SMALLINT NOT NULL,
     PRIMARY KEY (id),
     FOREIGN KEY (context_id) REFERENCES CONTEXTS(id)  ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

--
--drop table FLIGHT_PLANS
create table FLIGHT_PLANS (
     id INT NOT NULL AUTO_INCREMENT,
     name TEXT NOT NULL,
     creation DATETIME not null, -- sorted by creation
     is_active SMALLINT  not null,
     PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

--drop table TASKS
create table TASKS (
     id INT NOT NULL AUTO_INCREMENT,
     summary TEXT NOT NULL,
     details TEXT,
     creation DATETIME not null,  
     deadline DATETIME,  
     redline DATETIME,  
     is_finished SMALLINT not null,
     is_started SMALLINT not null,
     is_paused SMALLINT not null,
     is_delegated SMALLINT not null,
     runtime_note TEXT,
     sort_order INT not null,
     list_id INT not null,
     PRIMARY KEY (id),
     FOREIGN KEY (list_id) REFERENCES LISTS(id) ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

--drop table TASKS_TO_FLIGHT_PLANS 
create table TASKS_TO_FLIGHT_PLANS 
(
    task_id INT NOT NULL,
    plan_id INT NOT NULL,
    FOREIGN KEY (task_id) REFERENCES TASKS(id) ON DELETE CASCADE,
    FOREIGN KEY (plan_id) REFERENCES FLIGHT_PLANS(id) ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;


--drop table REFERENCED_OBJECTS
create table REFERENCED_OBJECTS
(
    id INT NOT NULL AUTO_INCREMENT,
    name        TEXT NOT NULL,
    creation DATETIME NOT NULL,
    sort_order INT not null,
    is_archived SMALLINT NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

--drop table NOTES 
create table NOTES 
(
    id INT NOT NULL AUTO_INCREMENT,
    note    TEXT NOT NULL,
    creation DATETIME NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

--drop table NOTES_TO_TASKS
create table NOTES_TO_TASKS
(
    note_id INT NOT NULL,
    task_id INT NOT NULL,
    FOREIGN KEY (task_id) REFERENCES TASKS(id)  ON DELETE CASCADE,
    FOREIGN KEY (note_id) REFERENCES NOTES(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

--drop table NOTES_TO_OBJECTS
create table NOTES_TO_OBJECTS
(
    note_id INT NOT NULL,
    object_id INT NOT NULL,
    FOREIGN KEY (object_id) REFERENCES REFERENCED_OBJECTS(id)  ON DELETE CASCADE,
    FOREIGN KEY (note_id) REFERENCES NOTES(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

--drop table NOTES_TO_LISTS
create table NOTES_TO_LISTS
(
    note_id INT NOT NULL,
    list_id INT NOT NULL,
    FOREIGN KEY (list_id) REFERENCES LISTS(id)  ON DELETE CASCADE,
    FOREIGN KEY (note_id) REFERENCES NOTES(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;



--drop table FILES
create table FILES
(
    id INT NOT NULL AUTO_INCREMENT,
    name        TEXT NOT NULL,
    mime_type   TEXT NOT NULL,
    data        BLOB NOT NULL,
    creation DATETIME NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

--drop table FILES_TO_TASKS
create table FILES_TO_TASKS
(
    task_id INT NOT NULL,
    file_id INT NOT NULL,
    FOREIGN KEY (task_id) REFERENCES TASKS(id)  ON DELETE CASCADE,
    FOREIGN KEY (file_id) REFERENCES FILES(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

--drop table FILES_TO_LISTS
create table FILES_TO_LISTS
(
    list_id INT NOT NULL,
    file_id INT NOT NULL,
    FOREIGN KEY (list_id) REFERENCES LISTS(id)  ON DELETE CASCADE,
    FOREIGN KEY (file_id) REFERENCES FILES(id) ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

--drop table FILES_TO_OBJECTS
create table FILES_TO_OBJECTS
(
    file_id INT NOT NULL,
    object_id INT NOT NULL,
    FOREIGN KEY (object_id) REFERENCES REFERENCED_OBJECTS(id)  ON DELETE CASCADE,
    FOREIGN KEY (file_id) REFERENCES FILES(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

--drop table LISTS_TO_OBJECTS
create table LISTS_TO_OBJECTS
(
    list_id INT NOT NULL,
    object_id INT NOT NULL,
    FOREIGN KEY (object_id) REFERENCES REFERENCED_OBJECTS(id)  ON DELETE CASCADE,
    FOREIGN KEY (list_id) REFERENCES LISTS(id) ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

--drop table TASKS_TO_OBJECTS
create table TASKS_TO_OBJECTS
(
    task_id INT NOT NULL,
    object_id INT NOT NULL,
    FOREIGN KEY (object_id) REFERENCES REFERENCED_OBJECTS(id)  ON DELETE CASCADE,
    FOREIGN KEY (task_id) REFERENCES TASKS(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

--drop table CONTEXTS_TO_OBJECTS
create table CONTEXTS_TO_OBJECTS
(
    context_id INT NOT NULL,
    object_id INT NOT NULL,
    FOREIGN KEY (object_id) REFERENCES REFERENCED_OBJECTS(id)  ON DELETE CASCADE,
    FOREIGN KEY (context_id) REFERENCES CONTEXTS(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

