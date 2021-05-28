INSERT INTO City (ID_city, name) VALUES
(1, 'Torino'),
(2, 'Vercelli'),
(3, 'Novara'),
(4, 'Cuneo'),
(5, 'Asti'),
(6, 'Alessandria'),
(7, 'Valle d''Aosta'),
(8, 'Imperia'),
(9, 'Savona'),
(10, 'Genova'),
(11, 'La Spezia'),
(12, 'Varese'),
(13, 'Como'),
(14, 'Sondrio'),
(15, 'Milano'),
(16, 'Bergamo'),
(17, 'Brescia'),
(18, 'Pavia'),
(19, 'Cremona'),
(20, 'Mantova'),
(21, 'Bolzano / Bozen'),
(22, 'Trento'),
(23, 'Verona'),
(24, 'Vicenza'),
(25, 'Belluno'),
(26, 'Treviso'),
(27, 'Venezia'),
(28, 'Padova'),
(29, 'Rovigo'),
(30, 'Udine'),
(31, 'Gorizia'),
(32, 'Trieste'),
(33, 'Piacenza'),
(34, 'Parma'),
(35, 'Reggio nell''Emilia'),
(36, 'Modena'),
(37, 'Bologna'),
(38, 'Ferrara'),
(39, 'Ravenna'),
(40, 'Forlì-Cesena'),
(41, 'Pesaro e Urbino'),
(42, 'Ancona'),
(43, 'Macerata'),
(44, 'Ascoli Piceno'),
(45, 'Massa-Carrara'),
(46, 'Lucca'),
(47, 'Pistoia'),
(48, 'Firenze'),
(49, 'Livorno'),
(50, 'Pisa'),
(51, 'Arezzo'),
(52, 'Siena'),
(53, 'Grosseto'),
(54, 'Perugia'),
(55, 'Terni'),
(56, 'Viterbo'),
(57, 'Rieti'),
(58, 'Roma'),
(59, 'Latina'),
(60, 'Frosinone'),
(61, 'Caserta'),
(62, 'Benevento'),
(63, 'Napoli'),
(64, 'Avellino'),
(65, 'Salerno'),
(66, 'L''Aquila'),
(67, 'Teramo'),
(68, 'Pescara'),
(69, 'Chieti'),
(70, 'Campobasso'),
(71, 'Foggia'),
(72, 'Bari'),
(73, 'Taranto'),
(74, 'Brindisi'),
(75, 'Lecce'),
(76, 'Potenza'),
(77, 'Matera'),
(78, 'Cosenza'),
(79, 'Catanzaro'),
(80, 'Reggio di Calabria'),
(81, 'Trapani'),
(82, 'Palermo'),
(83, 'Messina'),
(84, 'Agrigento'),
(85, 'Caltanissetta'),
(86, 'Enna'),
(87, 'Catania'),
(88, 'Ragusa'),
(89, 'Siracusa'),
(90, 'Sassari'),
(91, 'Nuoro'),
(92, 'Cagliari'),
(93, 'Pordenone'),
(94, 'Isernia'),
(95, 'Oristano'),
(96, 'Biella'),
(97, 'Lecco'),
(98, 'Lodi'),
(99, 'Rimini'),
(100, 'Prato'),
(101, 'Crotone'),
(102, 'Vibo Valentia'),
(103, 'Verbano-Cusio-Ossola'),
(104, 'Olbia-Tempio'),
(105, 'Ogliastra'),
(106, 'Medio Campidano'),
(107, 'Carbonia-Iglesias'),
(108, 'Monza e della Brianza'),
(109, 'Fermo'),
(110, 'Barletta-Andria-Trani');



INSERT INTO Type_advertisement (ID_type, type) VALUES
(1, 'Dance club'),
(2, 'Cinema'),
(3, 'Theater'),
(4, 'Restaurant'),
(5, 'Bar'),
(6, 'Hotel'),
(7, 'Pool'),
(8, 'Museum'),
(9, 'Stadium'),
(10, 'Gym'),
(11, 'Guided tour'),
(12, 'Bike tour'),
(13, 'Cooking lessons'),
(14, 'Wine tour'),
(15, 'Bed and breakfast');


INSERT INTO TOURIST (email_t, SURNAME, NAME, birth_date, phone_number, address, password, ID_city) 
VALUES ('mariorossi@gmail.com', 'Rossi', 'Mario', '1998-03-10', '3287888901', 'Via Roma 10', MD5 (MD5('Abcde54321')), (SELECT ID_city FROM CITY WHERE CITY.name = 'Padova'));

INSERT INTO TOURIST (email_t, SURNAME, NAME, birth_date, phone_number, address, password, ID_city)
VALUES ('pluto@mail.com', 'pluto', 'pippo', '1998-03-10', '3287888901', 'Via Roma 10', MD5 (MD5('Abcde54321')), (SELECT ID_city FROM CITY WHERE CITY.name = 'Padova'));

INSERT INTO TOURIST (email_t, SURNAME, NAME, birth_date, phone_number, address, password, ID_city)
VALUES ('giovanna@mail.com', 'giovanna', 'mail', '1998-03-10', '3287888901', 'Via Roma 10', MD5 (MD5('Abcde54321')), (SELECT ID_city FROM CITY WHERE CITY.name = 'Padova'));

INSERT INTO TOURIST (email_t, SURNAME, NAME, birth_date, phone_number, address, password, ID_city)
VALUES ('pippopasticcio@gmail.com', 'pippopasticcio', 'pippo', '1998-03-10', '3287888901', 'Via Roma 10', MD5 (MD5('Abcde54321')), (SELECT ID_city FROM CITY WHERE CITY.name = 'Padova'));

INSERT INTO TOURIST (email_t, SURNAME, NAME, birth_date, phone_number, address, password, ID_city)
VALUES ('filippo@mail.com', 'filippo', 'mail', '1998-03-10', '3287888901', 'Via Roma 10', MD5 (MD5('Abcde54321')), (SELECT ID_city FROM CITY WHERE CITY.name = 'Padova'));

INSERT INTO COMPANY (email_c, name_c, phone_number, address, password, ID_city) 
VALUES ('hotelcentrale@gmail.com', 'Hotel centrale', '0495341234', 'Via Venezia 48', MD5 (MD5('Abcde54321')), (SELECT ID_city FROM CITY WHERE CITY.name = 'Padova'));


INSERT INTO Advertisement (title, DESCRIPTION, SCORE, PRICE, NUM_TOT_ITEM, DATE_START, DATE_END, TIME_START, TIME_END, email_c, ID_TYPE)
VALUES ('Delicious dinner in the Dolomites, da Pino', 'This is a mock advertisement. Requests to the REST web server will be made in order to show the desired advertisement.', 6,  19, 28, '2021-04-24','2021-04-24','18:30:00','23:30:00', 'hotelcentrale@gmail.com', (SELECT ID_TYPE FROM Type_advertisement WHERE Type_advertisement.type = 'Restaurant'));

INSERT INTO Advertisement (title, DESCRIPTION, SCORE, PRICE, NUM_TOT_ITEM, DATE_START, DATE_END, TIME_START, TIME_END, email_c, ID_TYPE)
VALUES ('Tour of Cortina', 'This is a mock advertisement. Requests to the REST web server will be made in order to show the desired advertisement.', 11,  35, 25, '2021-04-19','2021-04-25','18:30:00','23:30:00', 'hotelcentrale@gmail.com', (SELECT ID_TYPE FROM Type_advertisement WHERE Type_advertisement.type = 'Guided tour'));


INSERT INTO Booking (date_b,time_b,num_booking,state,email_t,ID_Advertisement)
VALUES ('2020-03-23','14:30:00', 1, 'SUCCESSFUL','mariorossi@gmail.com',1);

INSERT INTO Booking (date_b,time_b,num_booking,state,email_t,ID_Advertisement)
VALUES ('2020-03-23','14:30:00', 2, 'SUCCESSFUL','pluto@mail.com',1);

INSERT INTO Booking (date_b,time_b,num_booking,state,email_t,ID_Advertisement)
VALUES ('2020-03-24','14:30:00', 4, 'SUCCESSFUL','pluto@mail.com',2);

INSERT INTO Booking (date_b,time_b,num_booking,state,email_t,ID_Advertisement)
VALUES ('2020-03-25','14:30:00', 4, 'SUCCESSFUL','mariorossi@gmail.com',2);



INSERT INTO Feedback (rate,text_f,date_f,email_t,ID_Advertisement)
VALUES (5,'This was super cool!', '2021-04-29','pluto@mail.com',2);

INSERT INTO Feedback (rate,text_f,date_f,email_t,ID_Advertisement)
VALUES (4,'Good! I will surely come back', '2021-04-30','mariorossi@gmail.com',2);

