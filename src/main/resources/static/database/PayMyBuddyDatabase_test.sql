DROP DATABASE IF EXISTS paymybuddy_test_oc_mc;

CREATE DATABASE IF NOT EXISTS paymybuddy_test_oc_mc;
USE paymybuddy_test_oc_mc;
SET autocommit=1;

CREATE TABLE USERS (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(60) NOT NULL,
    lastname VARCHAR(60) NOT NULL,
    birthday DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    country_code CHAR(3),
    UNIQUE INDEX index_email (email)
)
ENGINE=INNODB
AUTO_INCREMENT=0;

CREATE TABLE TRANSACTIONS (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    from_isCard BOOLEAN NOT NULL,
    from_id INT NOT NULL,
    to_id INT NOT NULL,
    date DATETIME NOT NULL,
    description TEXT,
    amount DOUBLE(10, 2) NOT NULL,
    fee DOUBLE(10, 2) NOT NULL,
    final_amount DOUBLE(10, 2) NOT NULL,
    currency CHAR(3) NOT NULL,
    CONSTRAINT FK_from_id FOREIGN KEY (from_id) REFERENCES users(id),
    CONSTRAINT FK_to_id FOREIGN KEY (to_id) REFERENCES users(id)
)
ENGINE=INNODB
AUTO_INCREMENT=0;

CREATE TABLE CREDIT_CARDS (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    card_type CHAR(4) NOT NULL,
    card_number VARCHAR(16),
    card_cvv VARCHAR(3),
    card_date VARCHAR(5),
    wording VARCHAR(150),
CONSTRAINT FK_userId_id_creditCard FOREIGN KEY (user_id) REFERENCES users(id)
)
ENGINE=INNODB
AUTO_INCREMENT=0;

CREATE TABLE ACCOUNTS (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    amount DOUBLE(10, 2) NOT NULL,
    currency CHAR(3),
    balance_date TIMESTAMP,
    CONSTRAINT FK_userId_id_buddyAccount FOREIGN KEY (user_id) REFERENCES users(id)
)
ENGINE=INNODB
AUTO_INCREMENT=0;

CREATE TABLE FRIENDS (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    friend_id INT NOT NULL,
    CONSTRAINT FK_user_id_friend FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT FK_friend_id_friend FOREIGN KEY (friend_id) REFERENCES users(id)
)
ENGINE=INNODB
AUTO_INCREMENT=0;

LOCK TABLES USERS WRITE;

INSERT INTO USERS(firstname, lastname, birthday, email, password, country_code)
VALUES
    ('juanita', 'emard', '1995-01-06', 'juanita.emard@email.com', '$2a$10$w4YKpuluFDrfomqaBzy1w./GTA57TtnAX6PngUtMhpk6KUGQBbe2e', 'FRA'),
    ('alexane', 'collins', '1989-11-22', 'alexane.collins@email.com', '$2a$10$Qp96gr3vEtUHlFFAnfPGVOnnaTgPOgKDpbo5f1X76UKteqprzmQma', 'GBR');

LOCK TABLES TRANSACTIONS WRITE;

INSERT INTO TRANSACTIONS (from_isCard, from_id, to_id, date, description, amount, fee, final_amount, currency)
VALUES
    (true, 1, 1, '2019-1-25 17:0:38', 'Feed Account', 1000.00, 5.00, 995.00, 'USD'),
    (false, 1, 2, '2019-6-7 16:58:25', 'Eu consequat ac felis donec et odio pellentesque.', 100.00, 0.50, 99.50, 'USD'),
    (true, 2, 2, '2019-1-25 17:0:38', 'Feed Account', 100.00, 0.50, 99.50, 'USD');

LOCK TABLES CREDIT_CARDS WRITE;

INSERT INTO CREDIT_CARDS (user_id, card_type, card_number, card_cvv, card_date, wording)
VALUES
    (1, 'MAST', '1447560945069489', '565', '01/20', 'My Card - Boursorama'),
    (2, 'VISA', '2049648612159233', '079', '01/20', 'My Card - BforBank');

LOCK TABLES ACCOUNTS WRITE;

INSERT INTO ACCOUNTS (user_id, amount, currency, balance_date)
VALUES
    (1, 895.00, 'USD', NOW()),
    (2, 199.50, 'USD', NOW());        ;

LOCK TABLES FRIENDS WRITE;

INSERT INTO  FRIENDS (user_id, friend_id)
VALUES (1, 2), (2, 1);

UNLOCK TABLES;