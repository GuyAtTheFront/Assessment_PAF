DROP DATABASE IF EXISTS acme_bank;
CREATE DATABASE acme_bank;
USE acme_bank;

CREATE TABLE accounts(
	account_id char(10) NOT NULL,
	name varchar(128) NOT NULL,
    balance decimal(16, 2) NOT NULL,
    
    primary key(account_id)
);

INSERT INTO accounts (account_id, name, balance) VALUES
	("V9L3Jd1BBI", "fred", 100.00),
	("fhRq46Y6vB", "barney", 300.00),
	("uFSFRqUpJy", "wilma", 1000.00),
	("ckTV56axff", "betty", 1000.00),
	("Qgcnwbshbh", "pebbles", 50.00),
	("if9l185l18", "bambam", 50.00);
    
CREATE USER IF NOT EXISTS "banker";
grant all privileges on acme_bank.* to 'banker'@'%';
flush privileges;
