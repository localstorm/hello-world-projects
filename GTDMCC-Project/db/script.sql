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

create table FLIGHT_PLANS (
     id INT NOT NULL AUTO_INCREMENT,
     creation DATETIME not null,
     is_archive SMALLINT  not null,
     user_id INT not null,
     PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


create table TASKS (
     id INT NOT NULL AUTO_INCREMENT,
     summary TEXT NOT NULL,
     details TEXT,
     creation DATETIME not null,  
     deadline DATETIME,  
     redline DATETIME,  
     is_cancelled SMALLINT not null,     
     is_finished SMALLINT not null,
     is_awaited SMALLINT not null,
     is_paused SMALLINT not null,
     is_delegated SMALLINT not null,
     runtime_note TEXT,
     sort_order INT not null,
     list_id INT not null,
     PRIMARY KEY (id),
     FOREIGN KEY (list_id) REFERENCES LISTS(id) ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

create table TASKS_TO_FLIGHT_PLANS 
(
    id      INT NOT NULL AUTO_INCREMENT,
    task_id INT NOT NULL,
    plan_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (task_id) REFERENCES TASKS(id) ON DELETE CASCADE,
    FOREIGN KEY (plan_id) REFERENCES FLIGHT_PLANS(id) ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;


create table REFERENCED_OBJECTS
(
    id INT NOT NULL AUTO_INCREMENT,
    name        TEXT NOT NULL,
    creation DATETIME NOT NULL,
    sort_order INT not null,
    is_archived SMALLINT NOT NULL,
    context_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (context_id) REFERENCES CONTEXTS(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;


create table NOTES 
(
    id INT NOT NULL AUTO_INCREMENT,
    note    TEXT NOT NULL,
    creation DATETIME NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

create table NOTES_TO_TASKS
(
    note_id INT NOT NULL,
    task_id INT NOT NULL,
    FOREIGN KEY (task_id) REFERENCES TASKS(id)  ON DELETE CASCADE,
    FOREIGN KEY (note_id) REFERENCES NOTES(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

create table NOTES_TO_OBJECTS
(
    note_id INT NOT NULL,
    object_id INT NOT NULL,
    FOREIGN KEY (object_id) REFERENCES REFERENCED_OBJECTS(id)  ON DELETE CASCADE,
    FOREIGN KEY (note_id) REFERENCES NOTES(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;


create table NOTES_TO_LISTS
(
    note_id INT NOT NULL,
    list_id INT NOT NULL,
    FOREIGN KEY (list_id) REFERENCES LISTS(id)  ON DELETE CASCADE,
    FOREIGN KEY (note_id) REFERENCES NOTES(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

create table FILES
(
    id INT NOT NULL AUTO_INCREMENT,
    name        TEXT NOT NULL,
    mime_type   TEXT NOT NULL,
    data        BLOB NOT NULL,
    creation DATETIME NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB DEFAULT CHARSET=utf8;


create table FILES_TO_OBJECTS
(
    file_id INT NOT NULL,
    object_id INT NOT NULL,
    FOREIGN KEY (object_id) REFERENCES REFERENCED_OBJECTS(id)  ON DELETE CASCADE,
    FOREIGN KEY (file_id) REFERENCES FILES(id)  ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

