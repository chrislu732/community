CREATE TABLE COMMUNITY_USER
(
	ID BIGINT AUTO_INCREMENT,
	ACCOUNT_ID VARCHAR(100),
	NAME VARCHAR(50) NOT NULL,
	TOKEN VARCHAR(36) NOT NULL,
	GMT_CREATE BIGINT NOT NULL,
	GMT_MODIFIED BIGINT NOT NULL,
	BIO VARCHAR(256),
	AVATAR_URL VARCHAR(100) NOT NULL,
	CONSTRAINT COMMUNITY_USER_PK
		PRIMARY KEY (ID)
);