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
-- {noop}�� ����� ��ȣ�� ��ȣȭ�� ������� �ʾ����� ��Ÿ����.
-- ������ ������ ������ ����� ����� ���ڵ� ����� �����Ѵ�.
-- ���� {bcrypt}, {scrypt}, {pdkdf2}, {sha256}�� �ɼ� �ִ�.
-- {sha256}�� �ַ� ȣ�Ѽ��� ������ �����ϸ� �񺸾����� �����ؾ� �Ѵ�.

INSERT INTO AUTHORITIES (ID, USERNAME, AUTHORITY) VALUES
   (1,'admin', 'ADMIN'),
   (1,'admin', 'USER'),
   (2,'normaluser', 'USER'),
   (3,'disableduser', 'USER');