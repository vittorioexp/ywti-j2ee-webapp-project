CREATE TABLE City (
 ID_city VARCHAR(20) PRIMARY KEY,
 name VARCHAR(100) NOT NULL
);


CREATE TABLE Type_advertisement (
 ID_type VARCHAR(50) PRIMARY KEY,
 description VARCHAR(100) 
);


CREATE TABLE Company (
 email_c VARCHAR(100) PRIMARY KEY,
 name_c VARCHAR(100) NOT NULL,
 phone_number VARCHAR(100) NOT NULL,
 address VARCHAR(100) NOT NULL,
 password VARCHAR(100) NOT NULL,
 ID_city VARCHAR(20) NOT NULL,
 FOREIGN KEY (ID_city) REFERENCES City (ID_city)
);


CREATE TABLE Tourist (
 email_t VARCHAR(100)PRIMARY KEY,
 surname VARCHAR(100) NOT NULL,
 name VARCHAR(100) NOT NULL,
 birth_date DATE NOT NULL,
 phone_number VARCHAR(100) NOT NULL,
 address VARCHAR(100) NOT NULL,
 password VARCHAR(100) NOT NULL,
 ID_city VARCHAR(20) NOT NULL,
 FOREIGN KEY (ID_city) REFERENCES City (ID_city)
);


CREATE TABLE Advertisement (
 ID_advertisement INT PRIMARY KEY,
 description VARCHAR(10000) NOT NULL,
 score INT NOT NULL,
 price INT NOT NULL,
 num_tot_item INT NOT NULL,
 date_start DATE NOT NULL,
 date_end DATE,
 time_start TIME,
 time_end TIME,
 email_c VARCHAR(100) NOT NULL,
 ID_type VARCHAR(50) NOT NULL,
 FOREIGN KEY (email_c) REFERENCES Company (email_c),
 FOREIGN KEY (ID_type) REFERENCES Type_advertisement (ID_type)
);


CREATE TABLE Booking (
 PRIMARY KEY (email_t,ID_advertisement,date_b),
 date_b DATE NOT NULL,
 time_b TIME NOT NULL,
 num_booking INT NOT NULL,
 state VARCHAR(20) NOT NULL,
 email_t VARCHAR(100) NOT NULL,
 ID_advertisement INT  NOT NULL,
 FOREIGN KEY (email_t) REFERENCES Tourist (email_t),
 FOREIGN KEY (ID_advertisement) REFERENCES Advertisement (ID_advertisement)
);


CREATE TABLE FeedBack (
 PRIMARY KEY (email_t,ID_advertisement,date_f),
 rate INT NOT NULL,
 text_f VARCHAR(10000),
 date_f DATE NOT NULL,
 email_t VARCHAR(100) NOT NULL,
 ID_advertisement INT  NOT NULL,
 FOREIGN KEY (email_t) REFERENCES Tourist (email_t),
 FOREIGN KEY (ID_advertisement) REFERENCES Advertisement (ID_advertisement)
);


CREATE TABLE Image (
 ID_image INT PRIMARY KEY,
 path_i VARCHAR(10000) NOT NULL,
 description_i VARCHAR(100),
 ID_advertisement INT  NOT NULL,
 FOREIGN KEY (ID_advertisement) REFERENCES Advertisement (ID_advertisement)
);

