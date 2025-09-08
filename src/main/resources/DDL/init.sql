CREATE DATABASE finance
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LOCALE_PROVIDER = 'libc'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE SCHEMA fin_app
    AUTHORIZATION postgres;

CREATE TABLE fin_app."user"
(
    uuid character varying(36) NOT NULL NOT NULL PRIMARY KEY,
    dt_create bigint NOT NULL,
    dt_update bigint NOT NULL,
    mail character varying(80) NOT NULL,
    fio character varying(255) NOT NULL,
    role character varying(50) NOT NULL,
    status character varying(50) NOT NULL,
    password character varying(64) NOT NULL
);

ALTER TABLE IF EXISTS fin_app."user"
    OWNER to postgres;

CREATE TABLE fin_app.code
(
    uuid character varying(36) NOT NULL PRIMARY KEY,
    code character varying(4) NOT NULL
);

ALTER TABLE IF EXISTS fin_app.code
    OWNER to postgres;