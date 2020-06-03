create table bitcoin_price (
id bigint not null,
date_created timestamptz not null,
price_in_usd NUMERIC(10, 2) not null
);
alter table bitcoin_price add CONSTRAINT bitcoin_price_pkey primary key (id);

create sequence bitcoin_price_seq
start with 1
    increment by 1
NO MINVALUE
MAXVALUE 999999999999999999 CACHE 20;
