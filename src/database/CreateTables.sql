CREATE TABLE City (
 ID_city SERIAL PRIMARY KEY,
 name VARCHAR(100) NOT NULL
);


CREATE TABLE Type_advertisement (
 ID_type SERIAL PRIMARY KEY,
 type VARCHAR(100) NOT NULL
);


CREATE TABLE Company (
 email_c VARCHAR(100) PRIMARY KEY,
 name_c VARCHAR(100) NOT NULL,
 phone_number VARCHAR(100) NOT NULL,
 address VARCHAR(200) NOT NULL,
 password VARCHAR(100) NOT NULL,
 ID_city INT NOT NULL,
 FOREIGN KEY (ID_city) REFERENCES City (ID_city)
);


CREATE TABLE Tourist (
 email_t VARCHAR(100) PRIMARY KEY,
 surname VARCHAR(100) NOT NULL,
 name VARCHAR(100) NOT NULL,
 birth_date DATE NOT NULL,
 phone_number VARCHAR(100) NOT NULL,
 address VARCHAR(200) NOT NULL,
 password VARCHAR(100) NOT NULL,
 ID_city INT NOT NULL,
 FOREIGN KEY (ID_city) REFERENCES City (ID_city)
);


CREATE TABLE Advertisement (
 ID_advertisement SERIAL PRIMARY KEY,
 title VARCHAR(200) NOT NULL,
 description VARCHAR(10000) NOT NULL,
 score INT NOT NULL,
 price INT NOT NULL,
 num_tot_item INT NOT NULL,
 date_start DATE NOT NULL,
 date_end DATE NOT NULL,
 time_start TIME NOT NULL,
 time_end TIME NOT NULL,
 email_c VARCHAR(100) NOT NULL,
 ID_type INT NOT NULL,
 FOREIGN KEY (email_c) REFERENCES Company (email_c),
 FOREIGN KEY (ID_type) REFERENCES Type_advertisement (ID_type)
);


CREATE TABLE Booking (
 PRIMARY KEY (email_t,ID_advertisement),
 date_b DATE NOT NULL,
 time_b TIME NOT NULL,
 num_booking INT NOT NULL,
 state VARCHAR(30) NOT NULL,
 email_t VARCHAR(100) NOT NULL,
 ID_advertisement INT  NOT NULL,
 FOREIGN KEY (email_t) REFERENCES Tourist (email_t),
 FOREIGN KEY (ID_advertisement) REFERENCES Advertisement (ID_advertisement)
);


CREATE TABLE FeedBack (
 PRIMARY KEY (email_t,ID_advertisement),
 rate INT NOT NULL,
 text_f VARCHAR(10000) NOT NULL,
 date_f DATE NOT NULL,
 email_t VARCHAR(100) NOT NULL,
 ID_advertisement INT  NOT NULL,
 FOREIGN KEY (email_t) REFERENCES Tourist (email_t),
 FOREIGN KEY (ID_advertisement) REFERENCES Advertisement (ID_advertisement)
);


CREATE TABLE Image (
 ID_image SERIAL PRIMARY KEY,
 path_i VARCHAR(10000) NOT NULL,
 description_i VARCHAR(100) NOT NULL,
 ID_advertisement INT  NOT NULL,
 FOREIGN KEY (ID_advertisement) REFERENCES Advertisement (ID_advertisement)
);

