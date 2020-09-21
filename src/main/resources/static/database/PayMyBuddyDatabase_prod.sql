/** PRODUCTION DB **/

-- CREATE Database
    CREATE DATABASE IF NOT EXISTS paymybuddy_prod_oc_mc;
    USE paymybuddy_prod_oc_mc;
    SET autocommit=1;

-- CREATE Tables
    -- Users Table
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
            card_type CHAR(4) NOT NULL,
            card_number VARCHAR(16),
            card_cvv VARCHAR(3),
            card_date VARCHAR(5),
            wording VARCHAR(150),
            CONSTRAINT FK_userId_id_creditCard FOREIGN KEY (user_id) REFERENCES users(id)
        )
        ENGINE=INNODB
        AUTO_INCREMENT=0;

    -- BuddyAccount Table
        CREATE TABLE ACCOUNTS (
            id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
            user_id INT NOT NULL,
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
        ('juanita', 'emard', '1995-01-06', 'juanita.emard@email.com', '$2a$10$w4YKpuluFDrfomqaBzy1w./GTA57TtnAX6PngUtMhpk6KUGQBbe2e', 'FRA'),
        ('alexane', 'collins', '1989-11-22', 'alexane.collins@email.com', '$2a$10$Qp96gr3vEtUHlFFAnfPGVOnnaTgPOgKDpbo5f1X76UKteqprzmQma', 'GBR'),
        ('ford', 'bashirian', '1997-09-13', 'ford.bashirian@email.com', '$2a$10$KOAKk39lx.QsbuXTeuDPzOdDYZbS4k50HMH/KQJPlttLKlf0w4pO.', 'USA'),
        ('katrine', 'lehner', '2000-05-05', 'katrine.lehner@email.com', '$2a$10$KcSclLuey7.Kw3LseJqjYOzgwUcZyvvi0UarO7xazYN1ZCjD5Vroq', 'FIN'),
        ('jacob', 'boyd', '1989-06-03', 'jacob.boyd@email.com', '$2a$10$FX9R2eRixaquseTKyk2W0uJgPAHQ.iTfw6KtHyMa79yc47F7rGZH6', 'GBR'),
        ('tonia', 'cooper', '1994-08-03', 'tony.cooper@email.com', '$2a$10$.7bo3NrXntHNo01SqPWRIeB0vk6WxBqX8D3E4dyS6R47WXiLkOQSy', 'GBR'),
        ('lily', 'peters', '1994-10-03', 'lily.peters@email.com', '$2a$10$DTpBpPZpruUjd2V0Al8jZ.uwvcCDduV/haJEmjl74TvNUCwFlxYDW', 'GBR'),
        ('sophia', 'zemicks', '1988-02-27', 'sophia.zemicks@email.com', '$2a$10$cuRhsiMub1TLPRoRrWpYLOBgFFjS5j/TY2v2QKF1Myn5kn9CWSJL2', 'DEU'),
        ('brian', 'stelzer', '1975-03-15', 'brain.stelzer@email.com', '$2a$10$xQCF4x2/NQ.SQNwhgoUdneiO2MlDz.5jwXCnhoydDQQsLpD2WkaGq', 'DEU'),
        ('allison', 'dupond', '1995-04-01', 'allison.dupond@email.com', '$2a$10$60Eyfv97.hoNFVUxQ9CMqefXlOgZFfHA5cjL.9Zw4KXvH59PluSOi', 'FRA'),
        ('morgan', 'johnson', '1997-10-03', 'morgan.johnson@email.com', '$2a$10$jUfhu6KqPKdOcjUPdlVsp.zcSDqmRQydBVaSTRGFGaIHR6rnjLE1q', 'GBR'),
        ('peggie', 'goodwin', '1997-10-03', 'peggie.goodwin@email.com', '$2a$10$qlivtwuO8sOJrxdmmizps.b29QAJq1B.f2H/YQCVoahDQKTMC.pIS', 'AUS'),
        ('alvera', 'schinner', '1997-10-03', 'alvera.schinner@email.com', '$2a$10$YKTZTYSLbs8kKsTxI8Wgeeugprex4ReXY/LQHAEouDWK.tgebuKBC', 'ESP'),
        ('lila', 'casper', '1997-10-03', 'lila.casper@email.com', '$2a$10$LBFZzCvITuxeaCScgA6GGeD1bjChSE/nNodBYvoI/R2pDUfQMjWeK', 'AUS'),
        ('pascale', 'okon', '1997-10-03', 'pascale.okon@email.com', '$2a$10$wCsVHYPmbBoPoSBXBqP75eknY6cUabPIKcFIV8gq8H6HLglU0m0ZW', 'GHA'),
        ('esmeralda', 'zieme', '1997-10-03', 'esmeralda.zieme@email.com', '$2a$10$qXg1eCPh4KnwWKzjVw0Cc.zW2brJo0qeWUXD4f5uTM77dHNxXFLAC', 'ESP'),
        ('austen', 'okuneva', '1997-10-03', 'austen.okuneva@email.com', '$2a$10$dGkBuWKh3Kk/H0IDdAY/s.fcJ/kQvfm7ke1Jwp4Gd/vlPCbMxccTe', 'JPN'),
        ('ewell', 'medhurst', '1997-10-03', 'ewell.medhurst@email.com', '$2a$10$rPq77i8.X1UnPTAiL88kWOqHa/WFXSPjblzbrAG4Ko9r2F4/xiviC', 'DEU'),
        ('alf', 'keebler', '1997-10-03', 'alf.keebler@email.com', '$2a$10$x9oMwjFz7xK3CnhPGa8vX.3ZLmMTxsM5.MRB760L8fCf2lzPtKgie', 'TUR'),
        ('riley', 'bashirian', '1997-10-03', 'riley.bashirian@email.com', '$2a$10$cExJLr8vpbuvA/kHfuozdO9pp8bMFQPr4yaRFRZt29r6pcM6W4pz2', 'USA'),
        ('sigmund', 'legros', '1997-10-03', 'sigmund.legros@email.com', '$2a$10$/jLNATaL.rlY6ncmALhQAe0N5PSjj9P69py0KGynd04cgywA9HEOW', 'FRA'),
        ('john', 'smith', '1991-11-11', 'john.smith@email.com', '$2a$10$ThnKOO8S59OALUDSO02mceEisQpU6iVySch35w40HDq.4cEQqHxcq', 'USA');


    LOCK TABLES TRANSACTIONS WRITE;

    INSERT INTO TRANSACTIONS (from_isCard, from_id, to_id, date, description, amount, fee, final_amount, currency)
    VALUES
        (true, 1, 1, '2019-1-25 17:0:38', 'Feed Account', 6000.0, 30.0, 5970.0, 'EUR'),
        (false, 1, 6, '2019-6-7 16:58:25', 'Eu consequat ac felis donec et odio pellentesque.', 181.36, 0.91, 180.45, 'EUR'),
        (false, 1, 6, '2019-1-18 16:32:20', 'Vitae purus faucibus ornare suspendisse sed nisi lacus sed.', 272.16, 1.36, 270.8, 'EUR'),
        (false, 1, 8, '2018-8-10 10:21:31', 'Eu consequat ac felis donec et odio pellentesque.', 371.56, 1.86, 369.7, 'EUR'),
        (false, 6, 1, '2018-3-23 9:28:3', 'Pellentesque adipiscing commodo elit at.', 154.72, 0.77, 153.95, 'EUR'),
        (true, 2, 2, '2018-8-14 12:50:54', 'Feed Account', 9000.0, 45.0, 8955.0, 'USD'),
        (false, 7, 2, '2018-1-3 11:9:44', 'Fermentum iaculis eu non diam phasellus vestibulum lorem.', 325.92, 1.63, 324.29, 'USD'),
        (false, 11, 2, '2018-11-6 17:19:15', 'Viverra ipsum nunc aliquet bibendum enim.', 78.53, 0.39, 78.14, 'USD'),
        (false, 2, 11, '2019-3-26 13:5:58', 'Eu consequat ac felis donec et odio pellentesque.', 164.43, 0.82, 163.61, 'USD'),
        (false, 11, 2, '2020-1-15 12:52:1', 'Tincidunt tortor aliquam nulla facilisi cras fermentum odio eu.', 251.86, 1.26, 250.6, 'USD'),
        (false, 2, 7, '2019-6-11 21:6:28', 'Orci eu lobortis elementum nibh tellus molestie.', 57.12, 0.29, 56.83, 'USD'),
        (false, 2, 16, '2018-10-17 13:56:6', 'Vitae et leo duis ut.', 407.89, 2.04, 405.85, 'USD'),
        (false, 11, 2, '2020-4-2 21:11:30', 'Sem viverra aliquet eget sit amet tellus cras adipiscing enim.', 319.5, 1.6, 317.9, 'USD'),
        (true, 3, 3, '2019-6-17 17:46:5', 'Feed Account', 8000.0, 40.0, 7960.0, 'USD'),
        (false, 3, 15, '2018-3-7 7:58:14', 'Sem viverra aliquet eget sit amet tellus cras adipiscing enim.', 456.08, 2.28, 453.8, 'USD'),
        (false, 13, 3, '2018-5-7 18:53:53', 'Sagittis purus sit amet volutpat consequat mauris nunc congue.', 321.06, 1.61, 319.45, 'USD'),
        (true, 4, 4, '2020-7-1 18:16:5', 'Feed Account', 6000.0, 30.0, 5970.0, 'EUR'),
        (false, 4, 6, '2018-1-20 5:28:25', 'Congue eu consequat ac felis donec et odio pellentesque diam.', 108.24, 0.54, 107.7, 'EUR'),
        (false, 11, 4, '2018-5-31 12:7:46', 'Imperdiet dui accumsan sit amet nulla facilisi morbi.', 208.59, 1.04, 207.55, 'EUR'),
        (false, 18, 4, '2018-3-23 3:2:12', 'Ut tortor pretium viverra suspendisse potenti.', 10.21, 0.05, 10.16, 'EUR'),
        (false, 4, 7, '2020-7-19 7:42:14', 'Fermentum iaculis eu non diam phasellus vestibulum lorem.', 193.8, 0.97, 192.83, 'EUR'),
        (false, 7, 4, '2018-6-19 11:17:18', 'Sagittis purus sit amet volutpat consequat mauris nunc congue.', 35.47, 0.18, 35.29, 'EUR'),
        (false, 4, 11, '2020-4-20 6:25:39', 'Ut tortor pretium viverra suspendisse potenti.', 416.99, 2.08, 414.91, 'EUR'),
        (true, 5, 5, '2018-4-22 15:22:27', 'Feed Account', 10000.0, 50.0, 9950.0, 'GBP'),
        (false, 16, 5, '2018-8-22 18:9:0', 'Vitae purus faucibus ornare suspendisse sed nisi lacus sed.', 417.75, 2.09, 415.66, 'GBP'),
        (false, 5, 12, '2019-9-8 9:32:54', 'Nec nam aliquam sem et tortor consequat id porta.', 415.19, 2.08, 413.11, 'GBP'),
        (false, 16, 5, '2020-1-9 13:16:27', 'Pellentesque adipiscing commodo elit at.', 89.64, 0.45, 89.19, 'GBP'),
        (false, 5, 16, '2018-6-16 7:50:0', 'Vitae purus faucibus ornare suspendisse sed nisi lacus sed.', 399.55, 2.0, 397.55, 'GBP'),
        (false, 5, 12, '2018-6-9 18:37:46', 'Vitae purus faucibus ornare suspendisse sed nisi lacus sed.', 287.31, 1.44, 285.87, 'GBP'),
        (true, 6, 6, '2018-8-30 4:49:8', 'Feed Account', 9000.0, 45.0, 8955.0, 'JPY'),
        (false, 1, 6, '2019-1-20 8:30:6', 'Tortor aliquam nulla facilisi cras fermentum odio eu.', 57.84, 0.29, 57.55, 'JPY'),
        (false, 1, 6, '2020-2-16 0:33:40', 'Tincidunt tortor aliquam nulla facilisi cras fermentum odio eu.', 203.9, 1.02, 202.88, 'JPY'),
        (false, 1, 6, '2018-8-24 8:47:6', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 411.04, 2.06, 408.98, 'JPY'),
        (false, 6, 1, '2019-11-25 19:28:1', 'Sagittis purus sit amet volutpat consequat mauris nunc congue.', 81.03, 0.41, 80.62, 'JPY'),
        (false, 6, 21, '2019-9-6 5:9:33', 'Nec nam aliquam sem et tortor consequat id porta.', 185.45, 0.93, 184.52, 'JPY'),
        (false, 6, 21, '2019-12-14 5:52:56', 'Sapien et ligula ullamcorper malesuada proin libero.', 197.15, 0.99, 196.16, 'JPY'),
        (true, 7, 7, '2019-1-14 22:51:46', 'Feed Account', 4000.0, 20.0, 3980.0, 'JPY'),
        (false, 10, 7, '2018-3-2 10:11:11', 'Ipsum consequat nisl vel pretium.', 306.43, 1.53, 304.9, 'JPY'),
        (true, 8, 8, '2020-2-8 9:33:36', 'Feed Account', 6000.0, 30.0, 5970.0, 'EUR'),
        (false, 8, 17, '2019-12-6 16:20:45', 'Eu consequat ac felis donec et odio pellentesque.', 247.16, 1.24, 245.92, 'EUR'),
        (false, 17, 8, '2019-11-20 23:33:1', 'Venenatis a condimentum vitae sapien pellentesque habitant morbi.', 208.8, 1.04, 207.76, 'EUR'),
        (false, 10, 8, '2019-7-10 13:28:13', 'Viverra ipsum nunc aliquet bibendum enim.', 355.97, 1.78, 354.19, 'EUR'),
        (false, 18, 8, '2019-5-21 1:3:4', 'Sem viverra aliquet eget sit amet tellus cras adipiscing enim.', 327.26, 1.64, 325.62, 'EUR'),
        (false, 8, 3, '2018-6-6 0:3:56', 'Eu consequat ac felis donec et odio pellentesque.', 45.19, 0.23, 44.96, 'EUR'),
        (false, 17, 8, '2020-1-18 18:42:8', 'Viverra ipsum nunc aliquet bibendum enim.', 11.03, 0.06, 10.97, 'EUR'),
        (true, 9, 9, '2020-5-11 5:16:49', 'Feed Account', 1000.0, 5.0, 995.0, 'GBP'),
        (false, 15, 9, '2020-3-2 0:6:4', 'Ipsum consequat nisl vel pretium.', 64.9, 0.32, 64.58, 'GBP'),
        (false, 9, 14, '2020-7-24 11:3:48', 'Tincidunt tortor aliquam nulla facilisi cras fermentum odio eu.', 279.6, 1.4, 278.2, 'GBP'),
        (false, 9, 14, '2020-8-7 17:3:13', 'Orci eu lobortis elementum nibh tellus molestie.', 457.26, 2.29, 454.97, 'GBP'),
        (true, 10, 10, '2020-4-24 10:8:10', 'Feed Account', 3000.0, 15.0, 2985.0, 'GBP'),
        (false, 17, 10, '2020-4-22 13:1:50', 'Vitae et leo duis ut.', 229.42, 1.15, 228.27, 'GBP'),
        (false, 17, 10, '2018-3-23 11:32:36', 'Varius vel pharetra vel turpis nunc eget lorem dolor.', 369.85, 1.85, 368.0, 'GBP'),
        (false, 22, 10, '2019-8-17 2:3:26', 'Fermentum iaculis eu non diam phasellus vestibulum lorem.', 378.96, 1.89, 377.07, 'GBP'),
        (false, 22, 10, '2018-2-18 14:47:30', 'Vitae et leo duis ut.', 389.47, 1.95, 387.52, 'GBP'),
        (false, 10, 22, '2019-2-15 19:50:0', 'Sagittis purus sit amet volutpat consequat mauris nunc congue.', 11.49, 0.06, 11.43, 'GBP'),
        (false, 13, 10, '2019-9-25 11:59:52', 'Sagittis purus sit amet volutpat consequat mauris nunc congue.', 348.31, 1.74, 346.57, 'GBP'),
        (false, 8, 10, '2020-1-3 13:54:39', 'Tortor aliquam nulla facilisi cras fermentum odio eu.', 98.95, 0.49, 98.46, 'GBP'),
        (true, 11, 11, '2018-5-9 22:56:6', 'Feed Account', 9000.0, 45.0, 8955.0, 'USD'),
        (false, 11, 14, '2019-5-9 8:13:7', 'Orci eu lobortis elementum nibh tellus molestie.', 119.24, 0.6, 118.64, 'USD'),
        (false, 11, 4, '2018-3-30 20:18:52', 'Vitae et leo duis ut.', 363.61, 1.82, 361.79, 'USD'),
        (false, 11, 14, '2020-3-26 23:54:58', 'Tortor aliquam nulla facilisi cras fermentum odio eu.', 129.64, 0.65, 128.99, 'USD'),
        (false, 11, 19, '2019-1-10 0:59:8', 'Viverra ipsum nunc aliquet bibendum enim.', 314.41, 1.57, 312.84, 'USD'),
        (false, 11, 14, '2019-3-31 8:48:22', 'Pellentesque adipiscing commodo elit at.', 426.43, 2.13, 424.3, 'USD'),
        (false, 11, 4, '2019-12-24 23:18:48', 'Pellentesque adipiscing commodo elit at.', 309.33, 1.55, 307.78, 'USD'),
        (false, 19, 11, '2020-7-3 13:26:44', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 299.94, 1.5, 298.44, 'USD'),
        (false, 2, 11, '2020-6-15 16:41:1', 'Orci eu lobortis elementum nibh tellus molestie.', 181.02, 0.91, 180.11, 'USD'),
        (true, 12, 12, '2018-10-3 4:28:44', 'Feed Account', 9000.0, 45.0, 8955.0, 'USD'),
        (false, 14, 12, '2018-4-22 2:59:6', 'Varius vel pharetra vel turpis nunc eget lorem dolor.', 55.94, 0.28, 55.66, 'USD'),
        (true, 13, 13, '2020-6-8 9:51:58', 'Feed Account', 7000.0, 35.0, 6965.0, 'JPY'),
        (false, 13, 10, '2020-2-21 3:14:14', 'Vitae et leo duis ut.', 427.59, 2.14, 425.45, 'JPY'),
        (false, 13, 3, '2020-5-27 0:5:34', 'Ut tortor pretium viverra suspendisse potenti.', 363.43, 1.82, 361.61, 'JPY'),
        (false, 10, 13, '2018-4-22 6:0:36', 'Sapien et ligula ullamcorper malesuada proin libero.', 166.17, 0.83, 165.34, 'JPY'),
        (false, 13, 10, '2019-10-21 11:38:13', 'Tortor aliquam nulla facilisi cras fermentum odio eu.', 339.12, 1.7, 337.42, 'JPY'),
        (false, 10, 13, '2018-1-21 0:32:59', 'Varius vel pharetra vel turpis nunc eget lorem dolor.', 116.46, 0.58, 115.88, 'JPY'),
        (false, 13, 3, '2020-4-27 16:51:48', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 480.0, 2.4, 477.6, 'JPY'),
        (false, 10, 13, '2020-6-17 0:47:49', 'Vitae et leo duis ut.', 277.65, 1.39, 276.26, 'JPY'),
        (true, 14, 14, '2020-7-24 11:0:37', 'Feed Account', 5000.0, 25.0, 4975.0, 'EUR'),
        (false, 12, 14, '2019-8-15 9:25:43', 'Eu consequat ac felis donec et odio pellentesque.', 354.18, 1.77, 352.41, 'EUR'),
        (false, 14, 9, '2019-8-11 3:14:17', 'Pellentesque adipiscing commodo elit at.', 58.13, 0.29, 57.84, 'EUR'),
        (false, 19, 14, '2019-4-23 19:7:50', 'Orci eu lobortis elementum nibh tellus molestie.', 387.97, 1.94, 386.03, 'EUR'),
        (false, 16, 14, '2019-1-6 15:14:29', 'Venenatis a condimentum vitae sapien pellentesque habitant morbi.', 23.23, 0.12, 23.11, 'EUR'),
        (false, 14, 9, '2018-11-15 20:23:40', 'Sem viverra aliquet eget sit amet tellus cras adipiscing enim.', 356.18, 1.78, 354.4, 'EUR'),
        (true, 15, 15, '2019-6-19 0:2:19', 'Feed Account', 5000.0, 25.0, 4975.0, 'JPY'),
        (false, 9, 15, '2019-7-25 15:37:38', 'Vitae purus faucibus ornare suspendisse sed nisi lacus sed.', 228.22, 1.14, 227.08, 'JPY'),
        (false, 15, 18, '2018-1-25 7:31:39', 'Imperdiet dui accumsan sit amet nulla facilisi morbi.', 292.1, 1.46, 290.64, 'JPY'),
        (false, 3, 15, '2019-6-29 0:34:22', 'Tortor aliquam nulla facilisi cras fermentum odio eu.', 171.31, 0.86, 170.45, 'JPY'),
        (false, 15, 9, '2018-6-18 12:28:51', 'Fermentum iaculis eu non diam phasellus vestibulum lorem.', 292.38, 1.46, 290.92, 'JPY'),
        (false, 3, 15, '2020-5-30 16:7:1', 'Congue eu consequat ac felis donec et odio pellentesque diam.', 341.85, 1.71, 340.14, 'JPY'),
        (false, 3, 15, '2019-9-11 12:4:46', 'Sagittis purus sit amet volutpat consequat mauris nunc congue.', 50.1, 0.25, 49.85, 'JPY'),
        (true, 16, 16, '2019-7-27 7:46:13', 'Feed Account', 8000.0, 40.0, 7960.0, 'EUR'),
        (false, 16, 22, '2019-9-30 18:1:8', 'Tortor aliquam nulla facilisi cras fermentum odio eu.', 189.6, 0.95, 188.65, 'EUR'),
        (false, 16, 19, '2019-10-28 2:39:26', 'Eu consequat ac felis donec et odio pellentesque.', 364.54, 1.82, 362.72, 'EUR'),
        (false, 16, 5, '2018-4-26 20:33:8', 'Congue eu consequat ac felis donec et odio pellentesque diam.', 83.84, 0.42, 83.42, 'EUR'),
        (false, 16, 22, '2018-3-9 21:2:55', 'Vitae purus faucibus ornare suspendisse sed nisi lacus sed.', 494.92, 2.47, 492.45, 'EUR'),
        (false, 16, 3, '2018-10-9 18:11:10', 'Vitae purus faucibus ornare suspendisse sed nisi lacus sed.', 268.42, 1.34, 267.08, 'EUR'),
        (false, 3, 16, '2020-5-22 1:29:25', 'Tincidunt tortor aliquam nulla facilisi cras fermentum odio eu.', 135.72, 0.68, 135.04, 'EUR'),
        (false, 3, 16, '2020-8-6 8:10:27', 'Eu consequat ac felis donec et odio pellentesque.', 266.33, 1.33, 265.0, 'EUR'),
        (true, 17, 17, '2020-4-29 1:12:3', 'Feed Account', 4000.0, 20.0, 3980.0, 'EUR'),
        (false, 17, 10, '2019-11-30 2:48:28', 'Nec nam aliquam sem et tortor consequat id porta.', 493.18, 2.47, 490.71, 'EUR'),
        (false, 20, 17, '2019-9-12 11:2:13', 'Imperdiet dui accumsan sit amet nulla facilisi morbi.', 219.17, 1.1, 218.07, 'EUR'),
        (false, 17, 16, '2018-4-23 17:48:11', 'Imperdiet dui accumsan sit amet nulla facilisi morbi.', 431.4, 2.16, 429.24, 'EUR'),
        (true, 18, 18, '2019-1-21 16:28:46', 'Feed Account', 5000.0, 25.0, 4975.0, 'GBP'),
        (false, 18, 8, '2019-12-21 13:50:43', 'Vitae et leo duis ut.', 344.28, 1.72, 342.56, 'GBP'),
        (false, 18, 4, '2019-8-17 16:42:59', 'Vitae purus faucibus ornare suspendisse sed nisi lacus sed.', 355.06, 1.78, 353.28, 'GBP'),
        (false, 18, 8, '2018-10-25 14:58:46', 'Sapien et ligula ullamcorper malesuada proin libero.', 487.81, 2.44, 485.37, 'GBP'),
        (false, 18, 4, '2018-10-24 19:40:41', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 12.26, 0.06, 12.2, 'GBP'),
        (true, 19, 19, '2018-1-2 0:53:15', 'Feed Account', 8000.0, 40.0, 7960.0, 'USD'),
        (false, 19, 14, '2019-2-17 13:46:38', 'Feed Account', 459.58, 2.3, 457.28, 'USD'),
        (true, 20, 20, '2019-8-10 6:38:23', 'Feed Account', 9000.0, 45.0, 8955.0, 'EUR'),
        (false, 17, 20, '2019-1-19 18:36:48', 'Eu consequat ac felis donec et odio pellentesque.', 99.68, 0.5, 99.18, 'EUR'),
        (false, 20, 17, '2018-6-2 23:51:40', 'Ipsum consequat nisl vel pretium.', 450.03, 2.25, 447.78, 'EUR'),
        (false, 21, 20, '2020-6-29 0:59:11', 'Imperdiet dui accumsan sit amet nulla facilisi morbi.', 311.04, 1.56, 309.48, 'EUR'),
        (false, 20, 21, '2019-4-7 12:52:19', 'Tortor aliquam nulla facilisi cras fermentum odio eu.', 120.89, 0.6, 120.29, 'EUR'),
        (false, 20, 21, '2018-4-7 1:5:37', 'Congue eu consequat ac felis donec et odio pellentesque diam.', 73.93, 0.37, 73.56, 'EUR'),
        (true, 21, 21, '2020-4-23 18:11:53', 'Feed Account', 5000.0, 25.0, 4975.0, 'GBP'),
        (false, 21, 3, '2019-9-9 1:18:21', 'Ut tortor pretium viverra suspendisse potenti.', 67.85, 0.34, 67.51, 'GBP'),
        (false, 21, 19, '2018-2-8 1:16:1', 'Ipsum consequat nisl vel pretium.', 459.01, 2.3, 456.71, 'GBP'),
        (true, 22, 22, '2020-6-5 1:1:29', 'Feed Account', 8000.0, 40.0, 7960.0, 'GBP'),
        (false, 22, 16, '2020-1-30 22:45:57', 'Tincidunt tortor aliquam nulla facilisi cras fermentum odio eu.', 303.96, 1.52, 302.44, 'GBP'),
        (false, 16, 22, '2019-1-11 22:48:14', 'Pellentesque adipiscing commodo elit at.', 61.25, 0.31, 60.94, 'GBP'),
        (false, 22, 16, '2018-12-9 0:26:21', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 233.24, 1.17, 232.07, 'GBP'),
        (false, 22, 10, '2018-5-28 0:36:10', 'Pellentesque adipiscing commodo elit at.', 486.69, 2.43, 484.26, 'GBP'),
        (false, 10, 22, '2019-5-20 3:48:13', 'Congue eu consequat ac felis donec et odio pellentesque diam.', 156.53, 0.78, 155.75, 'GBP'),
        (false, 22, 16, '2018-11-4 1:36:24', 'Sem viverra aliquet eget sit amet tellus cras adipiscing enim.', 264.67, 1.32, 263.35, 'GBP'),
        (false, 22, 10, '2019-12-19 9:22:49', 'Fermentum iaculis eu non diam phasellus vestibulum lorem.', 84.16, 0.42, 83.74, 'GBP');

    LOCK TABLES CREDIT_CARDS WRITE;

    INSERT INTO CREDIT_CARDS (user_id, card_type, card_number, card_cvv, card_date, wording)
    VALUES
        (1, 'MAST', '1447560945069489', '565', '01/20', 'My Card - Société Générale'),
        (2, 'VISA', '2049648612159233', '079', '01/20', 'My Card - BforBank'),
        (3, 'MAST', '9591221741525002', '757', '01/20', 'My Card - Ally Financial'),
        (4, 'VISA', '6191677172738759', '660', '01/20', 'My Card - Société Générale'),
        (5, 'MAST', '4251056551139390', '071', '01/20', 'My Card - Bank of America'),
        (6, 'VISA', '3845820614575538', '929', '01/20', 'My Card - Silicon Valley Bank'),
        (7, 'MAST', '9554094265951975', '654', '01/20', 'My Card - HSBC'),
        (8, 'MAST', '2976170107952553', '385', '01/20', 'My Card - BforBank'),
        (9, 'MAST', '3835111804646716', '735', '01/20', 'My Card - Bank of America'),
        (10, 'VISA', '7857666864510644', '225', '01/20', 'My Card - Société Générale'),
        (11, 'VISA', '5766517158593824', '305', '01/20', 'My Card - BPCE'),
        (12, 'VISA', '3501050162095724', '520', '01/20', 'My Card - HSBC'),
        (13, 'VISA', '6957934880271965', '683', '01/20', 'My Card - JPMorgan Chase'),
        (14, 'MAST', '9312572979604607', '460', '01/20', 'My Card - ING'),
        (15, 'MAST', '6066806940027793', '923', '01/20', 'My Card - Bank of America'),
        (16, 'VISA', '7036205304409556', '479', '01/20', 'My Card - ING'),
        (17, 'VISA', '8876532706524173', '038', '01/20', 'My Card - UBS'),
        (18, 'VISA', '8137581256910896', '567', '01/20', 'My Card - Volksbank'),
        (19, 'MAST', '7123550570744211', '774', '01/20', 'My Card - BPCE'),
        (20, 'MAST', '0151440941109811', '081', '01/20', 'My Card - N26'),
        (21, 'VISA', '2446539175591162', '220', '01/20', 'My Card - BPCE'),
        (22, 'MAST', '1708954204889586', '870', '01/20', 'My Card - Santander UK');

    LOCK TABLES ACCOUNTS WRITE;

    INSERT INTO ACCOUNTS (user_id, amount, currency, balance_date)
    VALUES
        (1, 4707.89, 'EUR', NOW()),
        (2, 9120.35, 'USD', NOW()),
        (3, 8084.56, 'USD', NOW()),
        (4, 6545.5, 'EUR', NOW()),
        (5, 9439.18, 'GBP', NOW()),
        (6, 9571.19, 'JPY', NOW()),
        (7, 4175.96, 'JPY', NOW()),
        (8, 7685.41, 'EUR', NOW()),
        (9, 801.51, 'GBP', NOW()),
        (10, 5240.0, 'GBP', NOW()),
        (11, 7496.24, 'USD', NOW()),
        (12, 9359.26, 'USD', NOW()),
        (13, 5245.77, 'JPY', NOW()),
        (14, 7141.88, 'EUR', NOW()),
        (15, 5573.18, 'JPY', NOW()),
        (16, 8409.57, 'EUR', NOW()),
        (17, 3053.0, 'EUR', NOW()),
        (18, 3730.22, 'GBP', NOW()),
        (19, 7950.47, 'USD', NOW()),
        (20, 8501.7, 'EUR', NOW()),
        (21, 4714.52, 'GBP', NOW()),
        (22, 6732.64, 'GBP', NOW());

    LOCK TABLES FRIENDS WRITE;

    INSERT INTO  FRIENDS (user_id, friend_id)
    VALUES
        (1, 8), (1, 19), (1, 6),
        (2, 7), (2, 8), (2, 11), (2, 16),
        (3, 16), (3, 8), (3, 13), (3, 15), (3, 21),
        (4, 11), (4, 18), (4, 6), (4, 7),
        (5, 16), (5, 12),
        (6, 4), (6, 1), (6, 21),
        (7, 2), (7, 4), (7, 10),
        (8, 1), (8, 3), (8, 2), (8, 17), (8, 10), (8, 18),
        (9, 14), (9, 15), (9, 18),
        (10, 8), (10, 22), (10, 7), (10, 13), (10, 17),
        (11, 4), (11, 2), (11, 14), (11, 19),
        (12, 5), (12, 14), (12, 22),
        (13, 3), (13, 10),
        (14, 9), (14, 11), (14, 12), (14, 19), (14, 16), (14, 21),
        (15, 9), (15, 3), (15, 18),
        (16, 3), (16, 5), (16, 14), (16, 22), (16, 2), (16, 17), (16, 19),
        (17, 8), (17, 16), (17, 10), (17, 20),
        (18, 4), (18, 9), (18, 8), (18, 15),
        (19, 1), (19, 14), (19, 21), (19, 11), (19, 16),
        (20, 17), (20, 21),
        (21, 6), (21, 14), (21, 19), (21, 20), (21, 3),
        (22, 10), (22, 16), (22, 12);

    UNLOCK TABLES;