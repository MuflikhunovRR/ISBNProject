-- Create TABLE
CREATE TABLE ISBN_CATALOG (
  id INT(11) NOT NULL AUTO_INCREMENT,
  StatusCode VARCHAR(30) NOT NULL,
  StatusMessage VARCHAR(150) NOT NULL,
  ISBN VARCHAR(18) NOT NULL,
  BookName VARCHAR(150) NOT NULL,
  BookPrice VARCHAR(30) NOT NULL,
  BookAuthor VARCHAR(150) NOT NULL,
  Availability VARCHAR(150) NOT NULL,
  Published VARCHAR(150) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE ISBN_CATALOG;

SELECT * FROM ISBN_CATALOG;

-- Insert data
INSERT INTO WorldAirports VALUES
  (1, 'LAX', 'LOS ANGELES INTL', 'United States', 'US', 91, 8, 12091, 126, 33, 56, 0, 'N', 118, 24, 0, 'W'),
  (2, 'FCO', 'ROME DA VINCI', 'Italy', 'IT', 450, -1, 12795, 14, 41, 47,59, 'N', 12, 14, 3, 'E'),
  (3, 'SFO', 'SAN FRANCISCO INTL', 'United States', 'US', 91, 8, 11870, 12, 37, 37, 0, 'N', 122, 23, 0, 'W');




DROP TABLE autocode_test2;
//for Oracle
CREATE TABLE autocode_test2;
( id NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
  Title varchar2(150) NOT NULL,
  Author varchar2(150) NOT NULL,
  Price varchar2(150) NOT NULL,
  ISBN varchar2(150) NOT NULL,
  AvailabilityData varchar2(150) NOT NULL,
  Published varchar2(150) NOT NULL
);

//for MsSql
CREATE TABLE ISBN_CATALOG (
  id INT(11) NOT NULL AUTO_INCREMENT,
  Title VARCHAR(150) NOT NULL,
  Author VARCHAR(150) NOT NULL,
  Price VARCHAR(150) NOT NULL,
  ISBN VARCHAR(150) NOT NULL,
  AvailabilityData VARCHAR(150) NOT NULL,
  Published VARCHAR(150) NOT NULL,
  ISBN_Check BOOLEAN NOT NULL,
  PRIMARY KEY (id)
);

SELECT * FROM autocode_test2 order by id;

DELETE FROM autocode_test2;

INSERT INTO autocode_test2 (Title, Author, Price, ISBN, AvailabilityData, Published) VALUES ();
commit;



