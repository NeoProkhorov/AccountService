CREATE TABLE IF NOT EXISTS account (
    id                  bigint,
    bank_id             text,
    surname             text,
    name                text,
    patronymic          text,
    birth               date,
    passport            text,
    birth_place         text,
    phone               text,
    mail                text,
    registration_address text,
    fact_address         text,
    PRIMARY KEY(id)
);