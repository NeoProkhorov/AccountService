CREATE TABLE IF NOT EXISTS account (
    id                      bigint  not null,
    bank_id                 text,
    surname                 text,
    name                    text,
    patronymic              text,
    birth                   date,
    passport                text,
    birth_place             text,
    phone                   text,
    mail                    text,
    registration_address    text,
    fact_address            text,
    PRIMARY KEY(id)
);

CREATE SEQUENCE account_seq
	INCREMENT BY 50
	START 1
	CACHE 1
	NO CYCLE;