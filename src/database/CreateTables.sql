CREATE TABLE City (
 ID_city INT DEFAULT AUTO_INCREMENT NOT NULL,
 name VARCHAR(100) NOT NULL
);

ALTER TABLE City ADD CONSTRAINT PK_City PRIMARY KEY (ID_city);


CREATE TABLE Type_advertisement (
 ID_type INT DEFAULT AUTO_INCREMENT NOT NULL,
 name VARCHAR(100) NOT NULL
);

ALTER TABLE Type_advertisement ADD CONSTRAINT PK_Type_advertisement PRIMARY KEY (ID_type);


CREATE TABLE User (
 ID_user INT DEFAULT AUTO_INCREMENT NOT NULL,
 email VARCHAR(100) NOT NULL,
 phone_number VARCHAR(100) NOT NULL,
 address VARCHAR(100) NOT NULL,
 password VARCHAR(100) NOT NULL,
 user_name VARCHAR(100) NOT NULL,
 ID_city INT DEFAULT AUTO_INCREMENT NOT NULL
);

ALTER TABLE User ADD CONSTRAINT PK_User PRIMARY KEY (ID_user);


CREATE TABLE Company (
 ID_user INT DEFAULT AUTO_INCREMENT NOT NULL,
 denomination VARCHAR(100) NOT NULL
);

ALTER TABLE Company ADD CONSTRAINT PK_Company PRIMARY KEY (ID_user);


CREATE TABLE Tourist (
 ID_user INT DEFAULT AUTO_INCREMENT NOT NULL,
 surname VARCHAR(100) NOT NULL,
 name VARCHAR(100) NOT NULL,
 birth_date DATE NOT NULL
);

ALTER TABLE Tourist ADD CONSTRAINT PK_Tourist PRIMARY KEY (ID_user);


CREATE TABLE Advertisement (
 ID_advertisement INT DEFAULT AUTO_INCREMENT NOT NULL,
 ID_user INT DEFAULT AUTO_INCREMENT NOT NULL,
 ID_type INT DEFAULT AUTO_INCREMENT NOT NULL,
 description VARCHAR(10000) NOT NULL,
 score INT NOT NULL,
 price INT NOT NULL,
 num_tot_item INT NOT NULL,
 date:start TIMESTAMP(10) NOT NULL,
 date:end TIMESTAMP(10) NOT NULL
);

ALTER TABLE Advertisement ADD CONSTRAINT PK_Advertisement PRIMARY KEY (ID_advertisement);


CREATE TABLE Booking (
 ID_user INT DEFAULT AUTO_INCREMENT NOT NULL,
 ID_advertisement INT DEFAULT AUTO_INCREMENT NOT NULL,
 date_time TIMESTAMP(10) NOT NULL,
 num_booking INT NOT NULL
);

ALTER TABLE Booking ADD CONSTRAINT PK_Booking PRIMARY KEY (ID_user,ID_advertisement);


CREATE TABLE FeedBack (
 ID_user INT DEFAULT AUTO_INCREMENT NOT NULL,
 ID_advertisement INT DEFAULT AUTO_INCREMENT NOT NULL,
 rate INT,
 text VARCHAR(10000)
);

ALTER TABLE FeedBack ADD CONSTRAINT PK_FeedBack PRIMARY KEY (ID_user,ID_advertisement);


CREATE TABLE Image (
 ID_image INT DEFAULT AUTO_INCREMENT NOT NULL,
 path VARCHAR(10000) NOT NULL,
 ID_advertisement INT DEFAULT AUTO_INCREMENT NOT NULL
);

ALTER TABLE Image ADD CONSTRAINT PK_Image PRIMARY KEY (ID_image);


ALTER TABLE User ADD CONSTRAINT FK_User_0 FOREIGN KEY (ID_city) REFERENCES City (ID_city);


ALTER TABLE Company ADD CONSTRAINT FK_Company_0 FOREIGN KEY (ID_user) REFERENCES User (ID_user);


ALTER TABLE Tourist ADD CONSTRAINT FK_Tourist_0 FOREIGN KEY (ID_user) REFERENCES User (ID_user);


ALTER TABLE Advertisement ADD CONSTRAINT FK_Advertisement_0 FOREIGN KEY (ID_user) REFERENCES Company (ID_user);
ALTER TABLE Advertisement ADD CONSTRAINT FK_Advertisement_1 FOREIGN KEY (ID_type) REFERENCES Type_advertisement (ID_type);


ALTER TABLE Booking ADD CONSTRAINT FK_Booking_0 FOREIGN KEY (ID_user) REFERENCES Tourist (ID_user);
ALTER TABLE Booking ADD CONSTRAINT FK_Booking_1 FOREIGN KEY (ID_advertisement) REFERENCES Advertisement (ID_advertisement);


ALTER TABLE FeedBack ADD CONSTRAINT FK_FeedBack_0 FOREIGN KEY (ID_user) REFERENCES Tourist (ID_user);
ALTER TABLE FeedBack ADD CONSTRAINT FK_FeedBack_1 FOREIGN KEY (ID_advertisement) REFERENCES Advertisement (ID_advertisement);


ALTER TABLE Image ADD CONSTRAINT FK_Image_0 FOREIGN KEY (ID_advertisement) REFERENCES Advertisement (ID_advertisement);


