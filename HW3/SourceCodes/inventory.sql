DROP DATABASE IF EXISTS inventory;

CREATE DATABASE inventory;

USE inventory;

CREATE TABLE book
(
   ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
   Title varchar(30) Not NULL,
   AuthorName varchar(30) NOT NULL,
   Price varchar(30) NOT NULL,
   ISBNLast4Digits varchar(30) Not Null
)                   ;
CREATE TABLE cd
(
   ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
   Album varchar(30) Not NULL,
   ArtistName varchar(30) NOT NULL,
   Price varchar(30) NOT NULL,
   Label varchar(30) Not Null
)                   ;
CREATE TABLE dvd
(
   ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
   Title varchar(30) Not NULL,
   Rating varchar(30) NOT NULL,
   Price varchar(30) NOT NULL,
   Production varchar(30) Not Null
)                   ;
CREATE TABLE Titles
(
   ISBN varchar(20) NOT NULL PRIMARY KEY,
   Title varchar(100) NOT NULL,
   EditionNumber int NOT NULL,
   Copyright varchar(4) NOT NULL
)             ;
CREATE TABLE bookID
(
   AuthorISBN varchar(20) NOT NULL PRIMARY KEY,
   FirstName varchar(30) Not NULL,
   LastName varchar(30) Not NULL,
   Price varchar(30) Not Null
)            ;

