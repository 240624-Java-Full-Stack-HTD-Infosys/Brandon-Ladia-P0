drop table if exists bankingTransactions;
drop table if exists account;
drop table if exists users;
--drop table if exists account

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    firstname VARCHAR(20) NOT NULL,
    lastname VARCHAR(20) NOT NULL,
    username VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(10) NOT NULL,
    email VARCHAR(20),
    phone VARCHAR(10),
    ssn VARCHAR(9)
);

--complete
CREATE TABLE account(
    account_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    checking DECIMAL(15, 2) DEFAULT 0.0,
    savings DECIMAL(15, 2) DEFAULT 0.0,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

--finish
CREATE TABLE bankingTransactions(
    transaction_id SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    amount DECIMAL (15, 2) NOT NULL,
    accountType VARCHAR(8) NOT NULL CHECK (accountType IN ('checking', 'savings')),
    transactionType VARCHAR(11) NOT NULL CHECK (transactionType IN ('deposit', 'withdraw', 'transfer in', 'transfer out')),
    FOREIGN KEY (account_id) REFERENCES account(account_id)
);