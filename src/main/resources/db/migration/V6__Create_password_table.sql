CREATE TABLE USER_PASSWORD
(
    ID BIGINT AUTO_INCREMENT,
    USER_ID BIGINT NOT NULL,
    PASSWORD VARCHAR(256) NOT NULL,
    CONSTRAINT USER_PASSWORD_PK
        PRIMARY KEY (ID)
);