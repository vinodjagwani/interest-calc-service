CREATE TABLE "public".accounts (
	bsb varchar NOT NULL,
  	identification varchar NOT NULL,
	opening_date date NOT NULL,
	CONSTRAINT accounts_bsb_identification_pk PRIMARY KEY (bsb,identification)
);
CREATE TABLE "public".balance_interest (
	balance_interest_id varchar NOT NULL,
  	bsb varchar NOT NULL,
	identification varchar NOT NULL,
	balance decimal NOT NULL,
	interest decimal NOT NULL,
	balance_date date NOT null,
	CONSTRAINT balance_interest_id_pk PRIMARY KEY (balance_interest_id)
);