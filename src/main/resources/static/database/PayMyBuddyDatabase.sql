-- CREATE Database
    DROP DATABASE IF EXISTS PayMyBuddy_OC_MC;
    CREATE DATABASE IF NOT EXISTS PayMyBuddy_OC_MC CHARACTER SET 'utf8';
    USE PayMyBuddy_OC_MC;
    SET autocommit=1;

-- CREATE Tables
    DROP TABLE IF EXISTS users;
    DROP TABLE IF EXISTS transactions;
    DROP TABLE IF EXISTS creditCard;
    DROP TABLE IF EXISTS buddyAccount;

    -- Users Table
        CREATE TABLE users (
            id INT PRIMARY KEY AUTO_INCREMENT,
            firstname VARCHAR(60) NOT NULL,
            lastname VARCHAR(60) NOT NULL,
            birthday DATETIME NOT NULL,
            phone VARCHAR(25),
            email VARCHAR(60) NOT NULL,
            password VARCHAR(250) NOT NULL,
            country_code CHAR(3),
            UNIQUE INDEX index_email (email)
        )
        ENGINE=INNODB
        AUTO_INCREMENT=0;

    -- Transactions Table
        CREATE TABLE transactions (
            id INT PRIMARY KEY AUTO_INCREMENT,
            from_type CHAR(4) NOT NULL,
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

    -- CreditCard Table
        CREATE TABLE creditCard (
            id INT PRIMARY KEY AUTO_INCREMENT,
            user_id INT NOT NULL,
            type CHAR(1) NOT NULL,
            card_number LONG,
            wording VARCHAR(150),
            CONSTRAINT FK_userId_id_creditCard FOREIGN KEY (user_id) REFERENCES users(id)
        )
        ENGINE=INNODB
        AUTO_INCREMENT=0;

    -- BuddyAccount Table
        CREATE TABLE buddyAccount (
            id INT PRIMARY KEY AUTO_INCREMENT,
            user_id INT NOT NULL,
            type CHAR(1) NOT NULL,
            ammount DOUBLE(10, 2) NOT NULL,
            currency CHAR(3),
            date TIMESTAMP,
            CONSTRAINT FK_userId_id_buddyAccount FOREIGN KEY (user_id) REFERENCES users(id)
        ) ENGINE=INNODB
        AUTO_INCREMENT=0;

    -- Friends Table
        CREATE TABLE friends (
            id INT PRIMARY KEY AUTO_INCREMENT,
            user_id INT NOT NULL,
            friend_id INT NOT NULL
            CONSTRAINT FK_user_id_friend FOREIGN KEY (user_id) REFERENCES users(id),
            CONSTRAINT FK_friend_id_friend FOREIGN KEY (friend_id) REFERENCES users(id)
        )
        ENGINE=INNODB
        AUTO_INCREMENT=0;

-- INSERT data
    LOCK TABLES users WRITE;
        -- INSERT
    UNLOCK TABLES;

    LOCK TABLES transactions WRITE;
        -- INSERT
    UNLOCK TABLES;

    LOCK TABLES creditCard WRITE;
        -- INSERT
    UNLOCK TABLES;

    LOCK TABLES buddyAccount WRITE;
        -- INSERT
    UNLOCK TABLES;

-- Procedures


-- Triggers

