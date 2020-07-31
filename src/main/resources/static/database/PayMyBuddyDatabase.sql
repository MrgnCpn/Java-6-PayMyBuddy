-- CREATE Database
    USE PayMyBuddy_OC_MC;
    SET autocommit=1;

-- CREATE Tables
    DROP TABLE IF EXISTS CREDIT_CARDS;
    DROP TABLE IF EXISTS BUDDY_ACCOUNTS;
    DROP TABLE IF EXISTS TRANSACTIONS;
    DROP TABLE IF EXISTS FRIENDS;
    DROP TABLE IF EXISTS USERS;

    -- Users Table
        CREATE TABLE USERS (
            id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
            firstname VARCHAR(60) NOT NULL,
            lastname VARCHAR(60) NOT NULL,
            birthday DATE NOT NULL,
            email VARCHAR(100) NOT NULL,
            password VARCHAR(250) NOT NULL,
            country_code CHAR(3),
            UNIQUE INDEX index_email (email)
        )
        ENGINE=INNODB
        AUTO_INCREMENT=0;

    -- Transactions Table
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

    -- CreditCard Table
        CREATE TABLE CREDIT_CARDS (
            id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
            user_id INT NOT NULL,
            card_type CHAR(1) NOT NULL,
            card_number LONG,
            card_cvv INTEGER,
            wording VARCHAR(150),
            CONSTRAINT FK_userId_id_creditCard FOREIGN KEY (user_id) REFERENCES users(id)
        )
        ENGINE=INNODB
        AUTO_INCREMENT=0;

    -- BuddyAccount Table
        CREATE TABLE BUDDY_ACCOUNTS (
            id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
            user_id INT NOT NULL,
            type CHAR(1) NOT NULL,
            amount DOUBLE(10, 2) NOT NULL,
            currency CHAR(3),
            balance_date TIMESTAMP,
            CONSTRAINT FK_userId_id_buddyAccount FOREIGN KEY (user_id) REFERENCES users(id)
        ) ENGINE=INNODB
        AUTO_INCREMENT=0;

    -- Friends Table
        CREATE TABLE FRIENDS (
            id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
            user_id INT NOT NULL,
            friend_id INT NOT NULL,
            CONSTRAINT FK_user_id_friend FOREIGN KEY (user_id) REFERENCES users(id),
            CONSTRAINT FK_friend_id_friend FOREIGN KEY (friend_id) REFERENCES users(id)
        )
        ENGINE=INNODB
        AUTO_INCREMENT=0;

-- INSERT data
    LOCK TABLES USERS WRITE;

    INSERT INTO USERS(firstname, lastname, birthday, email, password, country_code)
    VALUES
        ('juanita', 'emard', '1995-01-06', 'juanita.emard@email.com', '', 'FRA'),
        ('alexane', 'collins', '1989-11-22', 'alexane.collins@email.com', '', 'GBR'),
        ('ford', 'bashirian', '1997-09-13', 'ford.bashirian@email.com', '', 'USA'),
        ('katrine', 'lehner', '2000-05-05', 'katrine.lehner@email.com', '', 'FIN'),
        ('jacob', 'boyd', '1989-06-03', 'jacob.boyd@email.com', '', 'GBR'),
        ('tony', 'cooper', '1994-08-03', 'tony.cooper@email.com', '', 'GBR'),
        ('lily', 'peters', '1994-10-03', 'lily.peters@email.com', '', 'GBR'),
        ('sophia', 'zemicks', '1988-02-27', 'sophia.zemicks@email.com', '', 'DEU'),
        ('brain', 'stelzer', '1975-03-15', 'brain.stelzer@email.com', '', 'DEU'),
        ('allison', 'dupond', '1995-04-01', 'allison.dupond@email.com', '', 'FRA'),
        ('morgan', 'johnson', '1997-10-03', 'morgan.johnson@email.com', '', 'GBR'),
        ('peggie', 'goodwin', '1997-10-03', 'peggie.goodwin@email.com', '', 'AUS'),
        ('alvera', 'schinner', '1997-10-03', 'alvera.schinner@email.com', '', 'ESP'),
        ('lila', 'casper', '1997-10-03', 'lila.casper@email.com', '', 'AUS'),
        ('pascale', 'okon', '1997-10-03', 'pascale.okon@email.com', '', 'GHA'),
        ('esmeralda', 'zieme', '1997-10-03', 'esmeralda.zieme@email.com', '', 'ESP'),
        ('austen', 'okuneva', '1997-10-03', 'austen.okuneva@email.com', '', 'JPN'),
        ('ewell', 'medhurst', '1997-10-03', 'ewell.medhurst@email.com', '', 'DEU'),
        ('alf', 'keebler', '1997-10-03','alf.keebler@email.com', '',  'TUR'),
        ('riley', 'bashirian', '1997-10-03', 'riley.bashirian@email.com', '', 'USA'),
        ('sigmund', 'legros', '1997-10-03', 'sigmund.legros@email.com', '', 'FRA'),
        ('john', 'smith', '1991-11-11', 'john.smith@email.com', '', 'USA');

    UNLOCK TABLES;

    LOCK TABLES TRANSACTIONS WRITE;
    -- INSERT
    UNLOCK TABLES;

    LOCK TABLES CREDIT_CARDS WRITE;
    -- INSERT
    UNLOCK TABLES;

    LOCK TABLES BUDDY_ACCOUNTS WRITE;
    -- INSERT
    UNLOCK TABLES;

    LOCK TABLES FRIENDS WRITE;
    -- INSERT
    UNLOCK TABLES;

-- Procedures


-- Triggers

