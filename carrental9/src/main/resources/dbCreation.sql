#wtf

DROP DATABASE IF EXISTS CarRental;

CREATE DATABASE CarRental;

USE CarRental;

CREATE TABLE UserStatus(
statusId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(20) NOT NULL UNIQUE,
description VARCHAR(50));

INSERT INTO UserStatus (name) VALUES
('ANON'), ('USER'), ('ADMIN');

CREATE TABLE Users (
userId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
email VARCHAR(30) UNIQUE NOT NULL,
password VARCHAR(20) NOT NULL,
firstname VARCHAR(20) NOT NULL,
lastname VARCHAR(20) NOT NULL,
phone VARCHAR(15) NOT NULL,
userStatus INT NOT NULL DEFAULT 2,
FOREIGN KEY (userStatus) REFERENCES UserStatus(statusId));

INSERT INTO Users (email, password, firstname, lastname, phone) VALUES
('arantir@gmail.com', 'arantirpass', 'Arantir', 'Vasilenko', '100500'),
('lucifer@gmail.com', 'luciferpass', 'Lucifer', 'Petrov', '666666'),
('petro@gmail.com', 'Petropass@100500', 'Petro', 'Ha', '099100500'),
('bandera@gmail.com', 'Bandera@100500', 'Stepan', 'Bandera', '099100500'),
('jesus@gmail.com', 'jesuspass', 'Jesus', 'Christ', '0007777777');

INSERT INTO Users (email, password, firstname, lastname, phone, userStatus) VALUES
('yurii.andrieiev@gmail.com', 'Qwerty@100500', 'Þð³é', 'Àíäðººâ', '0930888888', 3);

CREATE TABLE Vehicles (
vehicleId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
model VARCHAR(60) NOT NULL,
mileage INT,
year INT);

INSERT INTO Vehicles (model) VALUES
('Alfa Romeo 4C Concept'),
('Alfa Romeo MiTo QV'),
('Ariel Atom 500 V8'),
('Aston Martin DB5 Vantage'),
('Aston Martin DBS'),
('Aston Martin V12 Vantage'),
('Audi A1 Clubsport quattro'),
('Audi R8 GT Spyder'),
('Audi RS3 Sportback'),
('BAC Mono'),
('Bentley Continental Supersports Convertible ISR'),
('BMW 1 Series M Coupe'),
('BMW M3 (E46) GTR'),
('BMW M3 (E92) Coupe'),
('Bugatti Veyron 16.4 Grand Sport Vitesse'),
('Bugatti Veyron 16.4 Super Sport'),
('Caterham Superlight R500'),
('Chevrolet Camaro ZL1'),
('Chevrolet Corvette ZR1'),
('Dodge Challenger SRT-8 392'),
('Dodge Charger R/T'),
('Dodge Charger SRT-8'),
('Ford F-150 SVT Raptor'),
('Ford Fiesta ST'),
('Ford Focus RS500'),
('Ford Focus ST'),
('Ford GT'),
('Ford Mustang Boss 302'),
('Hennessey Venom GT Spyder'),
('Jaguar XKR'),
('Koenigsegg Agera R'),
('Lamborghini Aventador J'),
('Aventador LP 700-4'),
('Lamborghini Countach 5000 quattrovalvole'),
('Lamborghini Diablo SV'),
('Lamborghini Gallardo LP 570-4 Spyder Performante'),
('Lancia Delta HF Integrale Evoluzione II'),
('Lexus LFA'),
('Marussia B2'),
('Maserati GranTurismo MC Stradale'),
('McLaren F1 LM'),
('McLaren MP4-12C'),
('Mercedes-Benz SL65 AMG Black Series'),
('Mercedes-Benz SLS AMG'),
('Mitsubishi Lancer Evolution X'),
('Nissan 350Z (Z33)'),
('Nissan GT-R (R35) Egoist'),
('Nissan Skyline GT-R (R34)'),
('Pagani Huayra'),
('Pagani Zonda R'),
('Pontiac Firebird Trans-Am Special Edition'),
('Porsche 911 (930) Carrera Turbo'),
('Porsche 911 (991) Carrera S'),
('Porsche 911 (997) GT2 RS'),
('Porsche 918 Spyder'),
('Porsche 918 Spyder Concept'),
('Porsche Panamera Turbo S'),
('Range Rover Evoque'),
('Shelby Cobra 427'),
('Shelby Mustang GT500'),
('SRT Viper GTS'),
('Subaru Impreza Cosworth STI CS400'),
('Tesla Roadster Sport');

CREATE TABLE OrderStatus(
orderStatus INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(35));

INSERT INTO OrderStatus (name) VALUES
('WAITING_FOR_PHONE_CONFIRMATION'),
('WAITING_FOR_PAYMENT'),
('PAID'),
('USING_A_CAR'),
('CAR_RETURNED'),
('CAR_BROKEN_WAITING_FOR_PAYMENT'),
('DONE'),
('CANCELLED'),
('CANCELLED_BY_USER');

CREATE TABLE Orders (
orderId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
userId INT NOT NULL,
vehicleId INT NOT NULL,
startTime BIGINT,
endTime BIGINT,
orderStatus INT NOT NULL DEFAULT 1,
FOREIGN KEY (userId) REFERENCES Users(userId),
FOREIGN KEY (vehicleId) REFERENCES Vehicles(vehicleId),
FOREIGN KEY (orderStatus) REFERENCES OrderStatus(orderStatus));

CREATE TABLE OrderHistory (
changeTime DATETIME NOT NULL DEFAULT NOW(),
orderId INT NOT NULL,
orderStatus INT NOT NULL DEFAULT 1,
reason TEXT,
payment INT NOT NULL DEFAULT 0,
FOREIGN KEY (orderStatus) REFERENCES OrderStatus(orderStatus),
FOREIGN KEY (orderId) REFERENCES Orders(orderId),
PRIMARY KEY (changeTime, orderId));

CREATE TABLE PassportInfo (
userId INT NOT NULL,
series CHAR(2),
number INT,
additionalInfo TEXT,
issuedMillis BIGINT,
FOREIGN KEY (userId) REFERENCES Users(userId),
PRIMARY KEY (userId));

