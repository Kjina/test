drop table if exists USERS;
drop table if exists AUTHORITIES;

CREATE TABLE USERS (
    ID BIGINT AUTO_INCREMENT,
    USERNAME VARCHAR(50) NOT NULL,
    PASSWORD VARCHAR(50) NOT NULL,
    ENABLED SMALLINT NOT NULL,
    PRIMARY KEY (USERNAME)
);

CREATE TABLE AUTHORITIES (
    ID BIGINT NOT NULL,
    USERNAME VARCHAR(50) NOT NULL,
    AUTHORITY VARCHAR(50) NOT NULL,
    FOREIGN KEY (USERNAME) REFERENCES USERS(USERNAME)
);

INSERT INTO USERS (USERNAME, PASSWORD,ENABLED) VALUES
   ('admin', '{scrypt}secret',true),
   ('normaluser', '{scrypt}user',true),
   ('disableduser', '{scrypt}user',false);
-- {noop}은 저장된 암호에 암호화가 적용되지 않았음을 나타낸다.
-- 스프링 보안은 위임을 사용해 사용할 인코딩 방법을 결정한다.
-- 값은 {bcrypt}, {scrypt}, {pdkdf2}, {sha256}이 될수 있다.
-- {sha256}은 주로 호한성을 이유로 존재하며 비보안으로 간주해야 한다.

INSERT INTO AUTHORITIES (ID, USERNAME, AUTHORITY) VALUES
   (1,'admin', 'ADMIN'),
   (1,'admin', 'USER'),
   (2,'normaluser', 'USER'),
   (3,'disableduser', 'USER');