CREATE TABLE NOTIFICATION
(
    ID            BIGINT AUTO_INCREMENT,
    NOTIFIER      BIGINT NOT NULL,
    RECEIVER      BIGINT NOT NULL,
    OUTER_ID      BIGINT NOT NULL,
    TYPE          INT NOT NULL,
    GMT_CREATE    BIGINT NOT NULL,
    STATUS        INT DEFAULT 0,
    NOTIFIER_NAME VARCHAR(100) NOT NULL,
    OUTER_TITLE   VARCHAR(256) NOT NULL,
    CONSTRAINT NOTIFICATION_PK
        PRIMARY KEY (ID)
);