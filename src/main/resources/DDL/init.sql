CREATE SCHEMA fin_app
    AUTHORIZATION postgres;

CREATE TABLE fin_app.users
(
    uuid character varying(36) NOT NULL PRIMARY KEY,
    dt_create bigint NOT NULL,
    dt_update bigint NOT NULL,
    mail character varying(80) NOT NULL,
    fio character varying(255) NOT NULL,
    role character varying(50) NOT NULL,
    status character varying(50) NOT NULL,
    password character varying(64) NOT NULL
);

ALTER TABLE IF EXISTS fin_app.users
    OWNER to postgres;

CREATE TABLE fin_app.codes
(
    uuid character varying(36) NOT NULL PRIMARY KEY REFERENCES fin_app.users(uuid) ON DELETE CASCADE,
    code character varying(4) NOT NULL
);

ALTER TABLE IF EXISTS fin_app.codes
    OWNER to postgres;

CREATE TABLE fin_app.op_categories
(
    uuid character varying(36) NOT NULL PRIMARY KEY,
    dt_create bigint NOT NULL,
    dt_update bigint NOT NULL,
    title character varying(128) NOT NULL
);

ALTER TABLE IF EXISTS fin_app.op_categories
    OWNER to postgres;

CREATE TABLE fin_app.currency
(
    uuid character varying(36) NOT NULL PRIMARY KEY,
    dt_create bigint NOT NULL,
    dt_update bigint NOT NULL,
    title character varying(16) NOT NULL,
    description character varying(128) NOT NULL
);

ALTER TABLE IF EXISTS fin_app.currency
    OWNER to postgres;

CREATE TABLE fin_app.accounts
(
    uuid character varying(36) NOT NULL PRIMARY KEY,
    dt_create bigint NOT NULL,
    dt_update bigint NOT NULL,
    title character varying(16) NOT NULL,
    description character varying(128) NOT NULL,
    balance numeric(20,2) NOT NULL DEFAULT 0,
    type character varying(24) NOT NULL,
    currency_id character varying(36) NOT NULL,
    user_id character varying(36) NOT NULL REFERENCES fin_app.users(uuid) ON DELETE CASCADE
);

ALTER TABLE IF EXISTS fin_app.accounts
    OWNER to postgres;

CREATE TABLE fin_app.operations
(
    uuid character varying(36) NOT NULL PRIMARY KEY,
    dt_create bigint NOT NULL,
    dt_update bigint NOT NULL,
    date bigint NOT NULL,
    description character varying(128) NOT NULL,
    category character varying(128) NOT NULL,
    value numeric(20,2) NOT NULL DEFAULT 0,
    currency_id character varying(36) NOT NULL,
    account_id character varying(36) NOT NULL REFERENCES fin_app.accounts(uuid) ON DELETE CASCADE
);

ALTER TABLE IF EXISTS fin_app.operations
    OWNER to postgres;

CREATE TABLE fin_app.audit
(
    uuid character varying(36) NOT NULL PRIMARY KEY,
    dt_create bigint NOT NULL,
    user_id character varying(36),
    text character varying(256) NOT NULL,
    type character varying(128) NOT NULL,
    essence_id character varying(36)
);

ALTER TABLE IF EXISTS fin_app.audit
    OWNER to postgres;