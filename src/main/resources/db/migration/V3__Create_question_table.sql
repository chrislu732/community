create table QUESTION
(
    id INT auto_increment,
    title VARCHAR(50),
    description TEXT,
    gmt_create BIGINT,
    gmt_modified BIGINT,
    author INT,
    comment_count INT default 0,
    view_count INT default 0,
    like_count INT default 0,
    tag VARCHAR(256),
    constraint QUESTION_PK
        primary key (id)
);